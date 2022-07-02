package paint.backend.model;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public void setX(double diff){
        x += diff;
    }
    public void setY(double diff){
        y += diff;
    }

    public void movePoint(double diffX, double diffY){
        setX(diffX);
        setY(diffY);
    }
    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

}
