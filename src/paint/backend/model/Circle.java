package paint.backend.model;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2*radius, 2*radius);
    }

    @Override
    public boolean containsPoint(Point eventPoint){
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(super.getCenterPoint().getY() - eventPoint.getY(), 2)) < getRadius();
    }
    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }



    public double getRadius() {
        return getsMayorAxis()/2;
    }

    @Override
    public Figure copyBackFigure(){
        return new Circle(getCenterPoint(),getRadius());
    }

    @Override
    public String getFigureShape(){
        return "Circulo";
    }
}
