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

    public void movePoint(double diffX, double diffY){
        x += diffX;
        y += diffY;
    }
//    public double distanceTo(Point o){
//        return
//    }
    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

}
