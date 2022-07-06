package paint.backend.model;


public class Ellipse extends Figure {

    protected final Point centerPoint;
    protected double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public void move(double diffX, double diffY){
        centerPoint.move(diffX, diffY);
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }
    public double getsMayorAxis() {
        return sMayorAxis;
    }
    public double getsMinorAxis() {
        return sMinorAxis;
    }

    public void setMayorAxis(double newAxisValue){this.sMayorAxis = newAxisValue; }
    public void setMinorAxis(double newAxisValue){this.sMinorAxis = newAxisValue; }

}
