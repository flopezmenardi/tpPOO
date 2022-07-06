package paint.frontend.buttons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Circle;
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
    public FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc) {
        return new FrontRectangle(new Rectangle(startPoint, endPoint), fillColor, borderColor, borderSize, gc);

    }
}
