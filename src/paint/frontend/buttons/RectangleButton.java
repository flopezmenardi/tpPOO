package paint.frontend.buttons;

import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Rectangle;

public class RectangleButton extends FigureButton{
    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public  Figure drawFigure(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }
}
