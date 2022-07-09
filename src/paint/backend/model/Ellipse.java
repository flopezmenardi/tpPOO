package paint.backend.model;


public class Ellipse extends Figure {

    private final Point centerPoint;
    private double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    protected double getsMayorAxis() {
        return sMayorAxis;
    }

    protected double getsMinorAxis() {
        return sMinorAxis;
    }
    protected Point getCenterPoint(){return centerPoint;}

    @Override
    public double getHeight() {
        return this.sMinorAxis;
    }

    @Override
    public double getWidth() {
        return this.sMayorAxis;
    }

    @Override
    public double getX() {
        return this.centerPoint.getX();
    }

    @Override
    public double getY() {
        return this.centerPoint.getY();
    }

    @Override
    public void move(double diffX, double diffY){
        centerPoint.move(diffX, diffY);
    }

    @Override
    public boolean containsPoint(Point eventPoint){
        return ((Math.pow(eventPoint.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) +
                (Math.pow(eventPoint.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }

    @Override
    public void setHeight(double height){
        this.sMinorAxis = height;
    }
    @Override
    public void setWidth(double width){
        this.sMayorAxis = width;
    }

    @Override
    public String toString() {
        return String.format("Ellipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", this.centerPoint, this.sMayorAxis, this.sMinorAxis);
    }




    @Override
    public String getFigureShape(){
        return "Elipse";
    }
}
