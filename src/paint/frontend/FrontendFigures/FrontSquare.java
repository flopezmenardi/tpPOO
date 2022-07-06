package paint.frontend.FrontendFigures;

import paint.backend.model.Point;
import paint.backend.model.Square;

public class FrontSquare extends FrontRectangle{
    private Square square;

    public FrontSquare(Point startPoint, double size) {
        super(startPoint, new Point(startPoint.getX() + size, startPoint.getY() + size));
    }
    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", square.getTopLeft(), square.getBottomRight());
    }

}
