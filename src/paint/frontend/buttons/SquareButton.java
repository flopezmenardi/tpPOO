package paint.frontend.buttons;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Rectangle;
import paint.backend.model.Square;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.FrontendFigures.FrontRectangle;

public class SquareButton extends FigureButton {

    public SquareButton(String name) {
        super(name);
    }

    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc) {
        return new FrontRectangle(new Square(startPoint, endPoint.getX() - startPoint.getX()), fillColor, borderColor, borderSize, gc);
        //return new FrontSquare(new Square(startPoint, endPoint.getX() - startPoint.getX()));
    }
}
