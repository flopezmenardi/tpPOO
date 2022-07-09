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
import paint.backend.Exceptions.NoSelectedFigureException;
import paint.backend.Exceptions.NothingToRedoException;
import paint.backend.Exceptions.NothingToUndoException;
import paint.backend.Statuses.*;
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
	private final StatusPane statusPane;

	private Label undoCounterText;
	private Label redoCounterText;
	private Label undoOperationText;
	private Label redoOperationText;

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
			try {
				selectedFigureExists();
				ChangeStatus changeStatus = new FillColorStatus(selectedFigure, canvasState, selectedFigure.getFillColor(),fillColorPicker.getValue());
				canvasState.undoPush(changeStatus);

				undoOperationText.setText(canvasState.getUndoOperationString());
				// !! aca
				redoOperationText.setText(canvasState.getRedoOperationString());
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				fillColor = fillColorPicker.getValue();
				selectedFigure.setFillColor(fillColor);
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
			
		});
		final ColorPicker lineColorPicker = new ColorPicker(lineColor);
		lineColorPicker.setOnAction(event -> {
			try {
				selectedFigureExists();
				ChangeStatus changeStatus = new BorderColorStatus(selectedFigure, canvasState, selectedFigure.getBorderColor(), lineColorPicker.getValue());
				canvasState.undoPush(changeStatus);
				undoOperationText.setText(canvasState.getUndoOperationString());
				redoOperationText.setText(canvasState.getRedoOperationString());
				// !! aca
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				lineColor = lineColorPicker.getValue();
				selectedFigure.setBorderColor(lineColor);
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
			
		});

		//slider para el grosor del borde
		final Slider borderSize = new Slider(MINBORDERSIZE, MAXBORDERSIZE, INCBORDERSIZE);
		borderSize.setShowTickMarks(true);
		borderSize.setShowTickLabels(true);
		borderSize.setOnMouseReleased(event -> {
			try{
				selectedFigureExists();
				selectedFigure.setBorderSize(borderSize.getValue());
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
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
		redoOperationText = new Label("");
		//posicionamiento de estas labels
		undoOperationText.setMinWidth(300);
		undoOperationText.setAlignment(Pos.CENTER_RIGHT);
		undoCounterText.setMinWidth(30);
		undoCounterText.setAlignment(Pos.CENTER);

		redoOperationText.setMinWidth(300);
		redoOperationText.setAlignment(Pos.CENTER_LEFT);
		redoCounterText.setMinWidth(30);
		redoCounterText.setAlignment(Pos.CENTER);
		
		//valores iniciales
		undoOperationText.setText(canvasState.getUndoOperationString());
		redoOperationText.setText(canvasState.getRedoOperationString());
		undoCounterText.setText("0");
		redoCounterText.setText("0");

		HBox undoBox = new HBox(10);
		undoBox.getChildren().addAll(undoOperationText, undoCounterText);
		undoBox.getChildren().addAll(HbuttonsArr);
		undoBox.getChildren().addAll(redoCounterText, redoOperationText);
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
					redoOperationText.setText("");
					}
			}
			if (newFigure != null) {
				canvasState.addFigure(newFigure);
				undoCounter +=1;
				AddStatus aux = new AddStatus(newFigure,canvasState);
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
			
			try {
				selectedFigureExists();
				ChangeStatus changeStatus = new EnlargeStatus(selectedFigure,canvasState);
				canvasState.undoPush(changeStatus);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter+=1;
				undoCounterText.setText(String.format("%d", undoCounter));
				selectedFigure.enlarge();
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
		});

		reduceButton.setOnAction(event -> {

			//A la selected figure (si es distinta de null) llamamos al metodo reduce
			try {
				selectedFigureExists();
				ChangeStatus changeStatus = new ReduceStatus(selectedFigure, canvasState);
				canvasState.undoPush(changeStatus);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				selectedFigure.reduce();
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
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
			try {
				selectedFigureExists();
				ChangeStatus aux = new DeleteStatus(selectedFigure,canvasState);
				canvasState.undoPush(aux);
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}catch (NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
		});

		undoButton.setOnAction(event->{
			//if(undoCounter == 0) return;
			try {
				ChangeStatus status = canvasState.getUndo();

			undoOperationText.setText(canvasState.getUndoOperationString());
			redoOperationText.setText(canvasState.getRedoOperationString()); // !! aca
			status.executeOperation();
			undoCounter -=1;
			redoCounter +=1;
			undoCounterText.setText(String.format("%d", undoCounter));
			redoCounterText.setText(String.format("%d", redoCounter));
			redrawCanvas();
			}catch(NothingToUndoException ex){
				System.out.println(ex.getMessage());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Undo Error");
				alert.setHeaderText("");
				alert.setContentText("There is nothing to undo");
				alert.showAndWait(); //codigo no sigue hasta q user cierra este popup
			}
		});

		redoButton.setOnAction(event ->{
			try {
				ChangeStatus status = canvasState.getRedo();
				status.executeInverseOperation();
				undoOperationText.setText(canvasState.getUndoOperationString()); // !! aca
				redoOperationText.setText(canvasState.getRedoOperationString());
				undoCounter += 1;
				redoCounter -= 1;
				undoCounterText.setText(String.format("%d", undoCounter));
				redoCounterText.setText(String.format("%d", redoCounter));
				redrawCanvas();
			}catch(NothingToRedoException ex){
				System.out.println(ex.getMessage());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Redo Error");
				alert.setHeaderText("");
				alert.setContentText("There is nothing to redo");
				alert.showAndWait(); //codigo no sigue hasta q user cierra este popup
			}
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
	private void selectedFigureExists() throws NoSelectedFigureException{
		//tira una exception si no hay una figura seleccionada
		if(selectedFigure == null)
			throw new NoSelectedFigureException();
	}

}
