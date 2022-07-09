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
	//cuantas operaciones hay en las listas de undo y redo
	private int undoCounter = 0;
	private int redoCounter = 0;
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK; // color del borde por default
	private Color fillColor = Color.YELLOW; // color del relleno por default
	private final int DEFAULTBORDERSIZE = 5; // width del borde por default
	private final int MINBORDERSIZE = 1; // MIN del slider
	private final int MAXBORDERSIZE = 60; // MAX del slider
	private final int INCBORDERSIZE = 1; //por cuanto incrementará el borderSize cuando el usuario usa el slider

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
	//panel de color
	final ColorPicker fillColorPicker = new ColorPicker(fillColor); // creamos los paneles de color seteados por default como corresponden
	final ColorPicker lineColorPicker = new ColorPicker(lineColor);
	// Dibujar una figura
	private Point startPoint;
	// Seleccionar una figura
	private FrontFigure selectedFigure;
	// StatusBar
	private final StatusPane statusPane;
	// labels de los botones de undo y redo, que muestran la cantidad de cambios disponibles, y el nombre de cada cambio
	private Label undoCounterText;
	private Label redoCounterText;
	private Label undoOperationText;
	private Label redoOperationText;






	public PaintPane(CanvasState canvasState, StatusPane statusPane) {

		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// ESTILO DE LOS BOTONES
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) { // itera por los botones para setear su tamanio
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}

		// le ponemos estilo a los botones enlarge, reduce, undo, redo. Son de tipo Button y no ToggleButton pues
		//no es la idea que se mantengan apretados.
		Button[] buttonsArr = {enlargeButton, reduceButton,undoButton,redoButton};
		for(Button b : buttonsArr){
			b.setMinWidth(90);
			b.setCursor(Cursor.HAND);
		}


		// creamos un nuevo layout con todos los botones uno abajo del otro (de manera vertical) a la izquierda
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().addAll(buttonsArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		//inicializamos el slider
		final Slider borderSize = new Slider(MINBORDERSIZE, MAXBORDERSIZE, INCBORDERSIZE);
		borderSize.setShowTickMarks(true);
		borderSize.setShowTickLabels(true);

		//textbox para los counters y el string que indica la proxima operacion del undo
		undoCounterText = new Label(""); //cantidad de operaciones en stack de undo
		redoCounterText = new Label(""); //cantidad de operaciones en stack de redo
		undoOperationText = new Label(""); //muestra operacion que hará si toco undo
		redoOperationText = new Label(""); //muestra operacion que hara si toco redo

		//posicionamiento de estas labels
		undoOperationText.setMinWidth(300);
		undoOperationText.setAlignment(Pos.CENTER_RIGHT);
		undoCounterText.setMinWidth(10);
		undoCounterText.setAlignment(Pos.CENTER);

		redoOperationText.setMinWidth(300);
		redoOperationText.setAlignment(Pos.CENTER_LEFT);
		redoCounterText.setMinWidth(10);
		redoCounterText.setAlignment(Pos.CENTER);

		//valores iniciales
		undoOperationText.setText(canvasState.getUndoOperationString());
		redoOperationText.setText(canvasState.getRedoOperationString());
		undoCounterText.setText("0");
		redoCounterText.setText("0");

		//creamos un nuevo layout de tipo horizontal, con los botones/labels apareciendo uno al lado del otro
		HBox undoBox = new HBox(10);
		//los adds lo hacemos en el order que queremos que aparezcan las labels/botones
		undoBox.getChildren().addAll(undoOperationText, undoCounterText);
		undoBox.getChildren().addAll(undoButton,redoButton);
		undoBox.getChildren().addAll(redoCounterText, redoOperationText);
		undoBox.setPadding(new Insets(5));
		undoBox.setStyle("-fx-background-color: #999999");
		undoBox.setAlignment(Pos.CENTER);
		undoBox.setPrefWidth(100);
		gc.setLineWidth(1);

		// le damos estilo a la label BORDE y RELLENO que aparecen en los botones de la izquierda
		Text borderText = new Text("Borde");
		Font font = Font.font("botones", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20);
		borderText.setFont(font);
		Text fillText = new Text("Relleno");
		fillText.setFont(font);
		buttonsBox.getChildren().add(borderText);
		buttonsBox.getChildren().addAll(borderSize, lineColorPicker);
		buttonsBox.getChildren().add(fillText);
		buttonsBox.getChildren().add(fillColorPicker);


		// color pickers

			// cuando se presiona al boton de cambiar color de relleno
		fillColorPicker.setOnAction(event -> {
			//try-catch por si no hay figura seleccionada
			try {
				selectedFigureExists();
				//debemos crear un changeStatus de la figura para que quede registrado en el stack de undo
				ChangeStatus changeStatus = new FillColorStatus(selectedFigure, canvasState, selectedFigure.getFillColor(),fillColorPicker.getValue());
				canvasState.undoPush(changeStatus);
				//actualizamos los labels de las proximas operaciones de undo/redo
				undoOperationText.setText(canvasState.getUndoOperationString());
				redoOperationText.setText(canvasState.getRedoOperationString());
				//actualizamos el valor del contador de Undos y su label
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				fillColor = fillColorPicker.getValue();
				selectedFigure.setFillColor(fillColor);
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
			
		});

		// cuando se presiona al boton de cambiar color de borde
		lineColorPicker.setOnAction(event -> {
			//try-catch por si no hay figura seleccionada
			try {
				selectedFigureExists();
				// creo un nuevo cambio, el cual sera cambiar el color del borde
				ChangeStatus changeStatus = new BorderColorStatus(selectedFigure, canvasState, selectedFigure.getBorderColor(), lineColorPicker.getValue());
				canvasState.undoPush(changeStatus); // lo agrego al stack de changes del undo, por si lo quiero eliminar.
				//actualizamos los labels de las proximas operaciones de undo/redo
				undoOperationText.setText(canvasState.getUndoOperationString());
				redoOperationText.setText(canvasState.getRedoOperationString());
				// aumentamos la cantidad de cambios a deshacer y actualizamos su label
				undoCounter++;
				undoCounterText.setText(String.format("%d", undoCounter));
				lineColor = lineColorPicker.getValue();
				selectedFigure.setBorderColor(lineColor);
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
			
		});

		//slider para el cambiar el grosor del borde

		borderSize.setOnMouseReleased(event -> {
			//try catch por si no hay figura seleccionada
			try{
				selectedFigureExists();
				selectedFigure.setBorderSize(borderSize.getValue());
				redrawCanvas();
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
		});


		// agregado de una figura

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


		// cuando se presiona el boton de agrandar
		enlargeButton.setOnAction(event -> {
			try {
				selectedFigureExists();
				// creo un nuevo cambio con la figura seleccionada y su tamanio antes de agrandarse.
				ChangeStatus changeStatus = new EnlargeStatus(selectedFigure,canvasState);
				canvasState.undoPush(changeStatus); // lo agrego al stack del undo.
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter+=1; //incremento la cantidad de cambios a deshacer.
				undoCounterText.setText(String.format("%d", undoCounter));
				selectedFigure.enlarge(); //llamo al metodo para agrandar la figura.
				redrawCanvas(); // actualizo el canvas.
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
		});
		// se presiona el boton de reducir
		reduceButton.setOnAction(event -> {

			try {
				selectedFigureExists();
				// creo un nuevo cambio antes de reducir la figura.
				ChangeStatus changeStatus = new ReduceStatus(selectedFigure, canvasState);
				canvasState.undoPush(changeStatus); // lo agrego al stack del undo.
				undoOperationText.setText(canvasState.getUndoOperationString());
				undoCounter++; // incremento la cantidad de cambios para deshacer.
				undoCounterText.setText(String.format("%d", undoCounter));
				selectedFigure.reduce(); // llamo al metodo de la figura para que se reduzca
				redrawCanvas(); //actualizo el canvas.
			}catch(NoSelectedFigureException ex){
				System.out.println(ex.getMessage());
			}
		});


		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if(selectedFigure!=null) selectedFigure.getFigure().move(diffX, diffY);
				redrawCanvas();
			}
		});

		// cuando se presiona al boton de BORRAR FIGURA
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

		// SE PRESIONA EL BOTON DE DESHACER EL CAMBIO
		undoButton.setOnAction(event->{

			try {
				ChangeStatus status = canvasState.getUndo(); // status es el ultimo cambio realizado

			undoOperationText.setText(canvasState.getUndoOperationString());
			redoOperationText.setText(canvasState.getRedoOperationString()); // !! aca
			status.executeOperation(); // como es un undo, llamo al metodo executeOperation del ultimo cambio.
			undoCounter -=1; // decremento las operaciones para deshacer.
			redoCounter +=1; // incremento las de rehacer.
			undoCounterText.setText(String.format("%d", undoCounter));
			redoCounterText.setText(String.format("%d", redoCounter));
			redrawCanvas(); // actualizacion del canvas.
			}catch(NothingToUndoException ex){ // creamos un popup con alerta en el caso de que se quiera deshacer un cambio inexistente.
				System.out.println(ex.getMessage());
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Undo Error");
				alert.setHeaderText("");
				alert.setContentText("There is nothing to undo");
				alert.showAndWait(); //codigo no sigue hasta q user cierra este popup
			}
		});

		// SE PRESIONA EL BOTON DE REHACER EL CAMBIO
		redoButton.setOnAction(event ->{
			try {
				ChangeStatus status = canvasState.getRedo(); // agarramos el ultimo cambio deshecho.
				status.executeInverseOperation(); // llamamos a la operacion invertida pues queremos rehacer ese cambio.
				undoOperationText.setText(canvasState.getUndoOperationString()); // !! aca
				redoOperationText.setText(canvasState.getRedoOperationString());
				undoCounter += 1; // aumentamos los cambios a deshacer
				redoCounter -= 1; // decrementamos los cambios para rehacer.
				undoCounterText.setText(String.format("%d", undoCounter));
				redoCounterText.setText(String.format("%d", redoCounter));
				redrawCanvas(); // actualizamos el canvas.
			}catch(NothingToRedoException ex){ //creamos un nuevo popup si no hay cambios por rehacer.
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

	// metodo que sirve para actualizar el canvas con los cambios realizados hasta el momento
	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(FrontFigure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				//si la figura esta seleccionada queremos que su borde sea rojo
				gc.setStroke(Color.RED);
			} else {

				gc.setStroke(figure.getBorderColor()); //si la figura no esta seleccionada, el borde es del color defualt o el seteado por el user
			}
			gc.setFill(figure.getFillColor()); //cambiamos el color de relleno de la figura:
			gc.setLineWidth(figure.getBorderSize()); 	//cambiamos el grosor del border de la figura:
			//en vez de los ifs con instanceof, creamos metodos abstractos en Figure para fill y stroke:
			figure.fill(gc);
			figure.stroke(gc);

		}
	}

	// metodo que retorna true si la figura que recibe por paramtero esta presente en el "pixel" eventPoint

	boolean figureBelongs(FrontFigure figure, Point eventPoint) {
		return figure.figureBelongs(eventPoint);
	}

	// tira la exception en el caso de que no se haya seleccionado ninguna Figure

	private void selectedFigureExists() throws NoSelectedFigureException{

		if(selectedFigure == null)
			throw new NoSelectedFigureException();
	}


}
