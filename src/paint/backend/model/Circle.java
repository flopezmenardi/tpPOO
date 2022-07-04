package paint.backend.model;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2*radius, 2*radius);
    }
    @Override
    public boolean figureBelongs(Point eventPoint){
        return Math.sqrt(Math.pow(centerPoint.getX() - eventPoint.getX(), 2) +
                Math.pow(centerPoint.getY() - eventPoint.getY(), 2)) < getRadius();
    }
    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, getRadius());
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return sMayorAxis/2;
    }

}
