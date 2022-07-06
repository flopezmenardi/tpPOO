package paint.frontend.buttons;

import paint.backend.model.Circle;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.frontend.FrontendFigures.FrontCircle;
import paint.frontend.FrontendFigures.FrontFigure;

public class CircleButton extends FigureButton{
    public CircleButton(String name) {
        super(name);
    }

    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new FrontCircle(startPoint, circleRadius);
    }
}
