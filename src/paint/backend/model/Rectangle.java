package paint.backend.model;

public class Rectangle extends Figure {

    protected final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void resize(double percentage){
        //centerPoint no se modifica
        double percentChange = 1 + percentage/100; //si es -10 multiplico por 0,9 si es +10 multiplico por 1,1
        topLeft.movePoint(topLeft.getX() - topLeft.getX()*percentChange, topLeft.getY() - topLeft.getY()*percentChange);
        bottomRight.movePoint(bottomRight.getX() - bottomRight.getX()*percentChange, bottomRight.getY() - bottomRight.getY()*percentChange);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

}
