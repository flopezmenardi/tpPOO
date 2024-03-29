package paint.frontend.buttons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Point;
import paint.backend.model.Rectangle;
import paint.frontend.frontendFigures.FrontFigure;
import paint.frontend.frontendFigures.FrontRectangle;

public class RectangleButton extends FigureButton{
    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc) {
        return new FrontRectangle(new Rectangle(startPoint, endPoint), fillColor, borderColor, borderSize, gc);

    }
}
