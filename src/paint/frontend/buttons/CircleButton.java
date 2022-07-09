package paint.frontend.buttons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Circle;
import paint.backend.model.Point;
import paint.frontend.frontendFigures.FrontEllipse;
import paint.frontend.frontendFigures.FrontFigure;

public class CircleButton extends FigureButton{
    public CircleButton(String name) {
        super(name);
    }

    // retornamos un Ellipse pues un circulo es una ellipse , y despues el toString
    // del circulo se opcupa de que aparezca el nombre de la figura correspondiente
    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new FrontEllipse(new Circle(startPoint, circleRadius), fillColor, borderColor, borderSize, gc);
    }
}
