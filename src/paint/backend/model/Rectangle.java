package paint.backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Figure {
    private Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void move(double diffX, double diffY){
        topLeft.move(diffX, diffY);
        bottomRight.move(diffX, diffY);
    }

    @Override
    public double getHeight(){
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    @Override
    public double getWidth() {
        return bottomRight.getX() - topLeft.getX();
    }

    @Override
    public boolean containsPoint(Point eventPoint){
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }

    @Override
    public double getX() {
        return topLeft.getX();
    }

    @Override
    public double getY() {
        return topLeft.getY();
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    @Override
    public void setHeight(double height) {
        double prevHeight = this.getHeight();
        double differenceY = height - prevHeight;
        this.topLeft.move(0, -differenceY/2);
        this.bottomRight.move(0, differenceY/2);
    }

    @Override
    public void setWidth(double width) {
        double prevWidth = this.getWidth();
        double differenceX = width - prevWidth;
        this.topLeft.move(-differenceX/2, 0);
        this.bottomRight.move(differenceX/2, 0);
    }

    @Override
    public Figure copyBackFigure(){
        return new Rectangle(topLeft, bottomRight);
    }
}
