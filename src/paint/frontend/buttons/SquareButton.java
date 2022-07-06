package paint.frontend.buttons;


import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Square;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.FrontendFigures.FrontSquare;

public class SquareButton extends FigureButton {

    public SquareButton(String name) {
        super(name);
    }

    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint) {
        return new FrontSquare(startPoint, endPoint.getX() - startPoint.getX());
    }
}
