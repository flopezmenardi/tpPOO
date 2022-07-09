package paint.frontend;

import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import paint.backend.CanvasState;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import paint.backend.Statuses.ChangeStatus;
import paint.backend.Statuses.ChangesStrings;
import paint.backend.model.Point;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.buttons.*;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private int undoCounter = 0;
	//cuantas operaciones hay en las listas de undo y redo
	private int redoCounter = 0;
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color fillColor = Color.YELLOW;
	private final int DEFAULTBORDERSIZE = 5;
	private final int MINBORDERSIZE = 1;
	private final int MAXBORDERSIZE = 60;
	//por cuanto incrementará el borderSize cuando el usuario usa el slider
	private final int INCBORDERSIZE = 1;

	//private final int

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final FigureButton rectangleButton = new RectangleButton("Rectángulo");
	private final FigureButton circleButton = new CircleButton("Círculo");
	private final FigureButton squareButton = new SquareButton("Cuadrado");
	private final FigureButton ellipseButton = new ElipseButton("Elipse");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");
	//botones para agrandar/achicar en barra izquierda
	private final Button enlargeButton = new Button("Agrandar");
	private final Button reduceButton = new Button("Achicar");
	//botones para undo y redo:
	private final Button undoButton = new Button("Deshacer");
	private final Button redoButton = new Button("Rehacer");

	//sliders/pickers para los colores y grosor del border y relleno
	//private final Slider borderSize = new Slider(MINBORDERSIZE, MAXBORDERSIZE, INCBORDERSIZE);

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private FrontFigure selectedFigure;
	// StatusBar
	private StatusPane statusPane;

	private Label undoCounterText;
	private Label redoCounterText;
	private Label undoOperationText;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) { // itera por los botones para setear su tamanio
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		//hicimos los siguientes botones del tipo Button y no ToggleButton asi no quedan apretados
		Button[] buttonsArr = {enlargeButton, reduceButton};
		for(Button b : buttonsArr){
			b.setMinWidth(90);
			b.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(buttonsArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		// color pickers
		final ColorPicker fillColorPicker = new ColorPicker(fillColor);
		fillColorPicker.setOnAction(event -> {
			if (selectedFigure != null) {
				ChangeStatus changeStatus = new ChangeStatus(selectedFigure, ChangesStrings.FILLCOLOR);
				canvasState.undoPush(changeStatus);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
			}
			fillColor = fillColorPicker.getValue();
			if(selectedFigure!= null) selectedFigure.setFillColor(fillColor);
			redrawCanvas();
		});
		final ColorPicker lineColorPicker = new ColorPicker(lineColor);
		lineColorPicker.setOnAction(event -> {
			if (selectedFigure != null) {
				ChangeStatus changeStatus = new ChangeStatus(selectedFigure, ChangesStrings.BORDERCOLOR);
				canvasState.undoPush(changeStatus);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
			}
			lineColor = lineColorPicker.getValue();
			if (selectedFigure != null) selectedFigure.setBorderColor(lineColor);
			redrawCanvas();
		});

		//slider para el grosor del borde
		final Slider borderSize = new Slider(MINBORDERSIZE, MAXBORDERSIZE, INCBORDERSIZE);
		borderSize.setShowTickMarks(true);
		borderSize.setShowTickLabels(true);
		borderSize.setOnMouseReleased(event -> {
			if(selectedFigure != null){
				selectedFigure.setBorderSize(borderSize.getValue());
				redrawCanvas();
			}
		});

		Text borderText = new Text("Borde");
		Font font = Font.font("botones", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20);
		borderText.setFont(font);
		Text fillText = new Text("Relleno");
		fillText.setFont(font);
		buttonsBox.getChildren().add(borderText);
		buttonsBox.getChildren().addAll(borderSize, lineColorPicker);
		buttonsBox.getChildren().add(fillText);
		buttonsBox.getChildren().add(fillColorPicker);

		//Undo y Redo:
		Button[] HbuttonsArr = {undoButton, redoButton};
		for(Button b : HbuttonsArr){
			b.setMinWidth(90);
			b.setCursor(Cursor.HAND);
		}
		//textbox para los counters y el string que indica la proxima operacion del undo
		undoCounterText = new Label("");
		redoCounterText = new Label("");
		undoOperationText = new Label("");
		undoOperationText.setText(canvasState.getUndoOperationString());
		undoCounterText.setText("0");
		redoCounterText.setText("0");

		HBox undoBox = new HBox(10);
		undoBox.getChildren().addAll(undoOperationText, undoCounterText);
		undoBox.getChildren().addAll(HbuttonsArr);
		undoBox.getChildren().add(redoCounterText);
		undoBox.setPadding(new Insets(5));
		undoBox.setStyle("-fx-background-color: #999999");
		undoBox.setAlignment(Pos.CENTER);
		undoBox.setPrefWidth(100);
		gc.setLineWidth(1);



		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null) {
				return ;
			}
			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}
			FrontFigure newFigure = null;
			FigureButton[] myButtons = new FigureButton[]{ellipseButton,circleButton,squareButton,rectangleButton};
			for(FigureButton b: myButtons){
				if(b.isSelected()){
					newFigure = b.drawFigure(startPoint,endPoint, fillColor, lineColor, DEFAULTBORDERSIZE, gc);
					//cada vez que se dibuja una figura queremos borrar lo que tiene el stack de redo
					canvasState.makeRedoNull();
					redoCounter = 0;
					redoCounterText.setText(String.format("%d", redoCounter));
					}
			}
			if (newFigure != null) {
				canvasState.addFigure(newFigure);
				undoCounter +=1;
				ChangeStatus aux = new ChangeStatus(newFigure,ChangesStrings.ADD);
				canvasState.undoPush(aux);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounterText.setText(String.format("%d", undoCounter));
			}
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(FrontFigure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure); //Eliminamos el to String porque no era necesario
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (FrontFigure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure); //Eliminamos el toString que no era necesario
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});


		enlargeButton.setOnAction(event -> {
			//A la selected figure (si es distinta de null) llamamos al metodo enlarge

			if (selectedFigure != null) {
				ChangeStatus changeStatus = new ChangeStatus(selectedFigure,ChangesStrings.ENLARGE);
				canvasState.undoPush(changeStatus);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter+=1;
				undoCounterText.setText(String.format("%d", undoCounter));

				selectedFigure.enlarge();
				redrawCanvas();
			}
		});

		reduceButton.setOnAction(event -> {

			//A la selected figure (si es distinta de null) llamamos al metodo reduce
			if (selectedFigure != null) {
				ChangeStatus changeStatus = new ChangeStatus(selectedFigure, ChangesStrings.REDUCE);
				canvasState.undoPush(changeStatus);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				selectedFigure.reduce();
				redrawCanvas();
			}
		});


		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				//creamos clase abstracta en Figure llamada move entonces solo hacemos selectedFigure.move()
				if(selectedFigure!=null) selectedFigure.getFigure().move(diffX, diffY);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				ChangeStatus aux = new ChangeStatus(selectedFigure,ChangesStrings.DELETE);
				canvasState.undoPush(aux);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		undoButton.setOnAction(event->{
			if(undoCounter == 0) return;
			ChangeStatus status = canvasState.getUndo();
			undoOperationText.setText(canvasState.getUndoOperationString());
			canvasState.deleteFigure(status.figureToDelete()); //Eliminamos comparando por codigo por lo que la copia elimina OK
			/*if(redoCounter != 0) es alto parche que sirve, pero no tanto. me parece que vamos a tener que hacer clases. */ canvasState.addFigure(status.figureToAdd()); //Agregamos la figura que seria la version anterior a la lista de friguras.
			undoCounter -=1;
			redoCounter +=1;
			undoCounterText.setText(String.format("%d", undoCounter));
			redoCounterText.setText(String.format("%d", redoCounter));
			redrawCanvas();
		});

		redoButton.setOnAction(event ->{
			if (redoCounter == 0) return;
			ChangeStatus status = canvasState.getRedo();
			canvasState.deleteFigure(status.figureToDelete()); //Eliminamos comparando por codigo por lo que la copia elimina OK
			 canvasState.addFigure(status.figureToAdd()); //Agregamos la figura que seria la version anterior a la lista de friguras.
			undoCounter +=1;
			redoCounter -=1;
			undoCounterText.setText(String.format("%d", undoCounter));
			redoCounterText.setText(String.format("%d", redoCounter));
			redrawCanvas();
		});

//dibujar borrar cambio color de borde, cambio color de relleno

		setLeft(buttonsBox);
		setRight(canvas);
		setTop(undoBox);

	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(FrontFigure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				//si la figura esta seleccionada queremos que su borde sea rojo
				gc.setStroke(Color.RED);
			} else {
				//si la figura no esta seleccionada, el borde es del color defualt o el seteado por el user
				gc.setStroke(figure.getBorderColor());
			}
			//cambiamos el color de relleno de la figura:
			gc.setFill(figure.getFillColor());
			//cambiamos el grosor del border de la figura:
			gc.setLineWidth(figure.getBorderSize());
			//en vez de los ifs con instanceof, creamos metodos abstractos en Figure para fill y stroke:
			figure.fill(gc);
			figure.stroke(gc);

		}
	}

	boolean figureBelongs(FrontFigure figure, Point eventPoint) {
		//en vez de los ifs con instanceof, creamos metodo abstracto figureBelongs:
		return figure.figureBelongs(eventPoint);
	}

}
