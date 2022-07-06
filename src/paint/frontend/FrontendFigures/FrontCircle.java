package paint.frontend.FrontendFigures;

import paint.backend.model.Circle;
import paint.backend.model.Ellipse;
import paint.backend.model.Point;

public class FrontCircle extends FrontEllipse {
    private Circle circle;

    public FrontCircle(Point centerPoint, double radius) {
        super(centerPoint,2*radius,2*radius);
    }

    @Override
    public boolean figureBelongs(Point eventPoint){
        return Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
    }

    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", circle.getCenterPoint(), circle.getRadius());
    }
}
