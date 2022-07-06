package paint.frontend.buttons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Circle;
import paint.backend.model.Ellipse;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.frontend.FrontendFigures.FrontEllipse;
import paint.frontend.FrontendFigures.FrontFigure;

public class CircleButton extends FigureButton{
    public CircleButton(String name) {
        super(name);
    }

    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new FrontEllipse(new Circle(startPoint, circleRadius), fillColor, borderColor, borderSize, gc);
    }
}
