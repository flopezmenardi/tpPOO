package paint.frontend.buttons;

import paint.backend.model.Circle;
import paint.backend.model.Figure;
import paint.backend.model.Point;

public class CircleButton extends FigureButton{
    @Override
    public Figure drawFigure(Point startPoint, Point endPoint) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Circle(startPoint, circleRadius);
    }
}
