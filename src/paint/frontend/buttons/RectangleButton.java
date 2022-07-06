package paint.frontend.buttons;

import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Rectangle;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.FrontendFigures.FrontRectangle;

public class RectangleButton extends FigureButton{
    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint) {
        return new FrontRectangle(startPoint, endPoint);
    }
}
