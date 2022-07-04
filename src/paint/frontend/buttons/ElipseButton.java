package paint.frontend.buttons;

import paint.backend.model.Ellipse;
import paint.backend.model.Figure;
import paint.backend.model.Point;

public class ElipseButton extends FigureButton{
    @Override
    public Figure drawFigure(Point startPoint, Point endPoint) {
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }
}
