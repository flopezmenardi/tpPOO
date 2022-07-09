package paint.frontend.buttons;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Point;
import paint.backend.model.Square;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.FrontendFigures.FrontRectangle;

public class SquareButton extends FigureButton {

    public SquareButton(String name) {
        super(name);
    }

    // retornamos un rectangle pues un cuadrado es un rectangulo, y despues el toString
    // del cuadrado se opcupa de que aparezca el nombre de la figura correspondiente
    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc) {
        return new FrontRectangle(new Square(startPoint, endPoint.getX() - startPoint.getX()), fillColor, borderColor, borderSize, gc);
    }
}
