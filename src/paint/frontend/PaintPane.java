package paint.frontend;

import javafx.scene.control.*;

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
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.buttons.*;

import java.util.ArrayList;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private  Color lineColor = Color.BLACK;
	private  Color fillColor = Color.YELLOW;
	private  double borderSizeDEFAULT = 13434;
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

	//sliders/pickers para los colores y grosor del border y relleno
	//private final Slider borderSize = new Slider();
	//private final ColorPicker fillColorPicker
	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private FrontFigure selectedFigure;
	// StatusBar
	private StatusPane statusPane;

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
		final ColorPicker fillColorPicker = new ColorPicker();
		fillColorPicker.setOnAction(event -> {
			fillColor = fillColorPicker.getValue();
			if(selectedFigure!= null) selectedFigure.setFillColor(fillColor);
			redrawCanvas();
		});
		final ColorPicker lineColorPicker = new ColorPicker();
		lineColorPicker.setOnAction(event -> {
			lineColor = lineColorPicker.getValue();
			if (selectedFigure != null) selectedFigure.setBorderColor(lineColor);
			redrawCanvas();
		});
		Text borderText = new Text("Borde");
		Font font = Font.font("botones", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20);
		borderText.setFont(font);
		Text fillText = new Text("Relleno");
		fillText.setFont(font);
		buttonsBox.getChildren().add(borderText);
		buttonsBox.getChildren().add(lineColorPicker);
		buttonsBox.getChildren().add(fillText);
		buttonsBox.getChildren().add(fillColorPicker);

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
					newFigure = b.drawFigure(startPoint,endPoint, fillColor, lineColor, borderSizeDEFAULT, gc);
					}
			}
			if (newFigure != null) canvasState.addFigure(newFigure);
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
			if (selectedFigure != null){
				selectedFigure.enlarge();
				redrawCanvas();
			}
		});

		reduceButton.setOnAction(event -> {
			//A la selected figure (si es distinta de null) llamamos al metodo enlarge
			if (selectedFigure != null){
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
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);


	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(FrontFigure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(figure.getBorderColor());
			}
			gc.setFill(figure.getFillColor());
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
