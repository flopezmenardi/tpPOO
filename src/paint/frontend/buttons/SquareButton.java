package paint.frontend.buttons;


import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Square;

public class SquareButton extends FigureButton {

    public SquareButton(String name) {
        super(name);
    }

    @Override
    public Figure drawFigure(Point startPoint, Point endPoint) {
        return new Square(startPoint, endPoint.getX() - startPoint.getX());
    }
}
