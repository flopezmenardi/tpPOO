package paint.backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

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
    public void fill(GraphicsContext gc){
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }
    @Override
    public void stroke(GraphicsContext gc){
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }
    @Override
    public boolean figureBelongs(Point eventPoint){
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }

    @Override
    public void resize(double percentage){
        //centerPoint no se modifica
        double percentChange = 1 + percentage/100; //si es -10 multiplico por 0,9 si es +10 multiplico por 1,1
        //calculamos el 10% de height/width
        double widthDifference = getWidth() * percentChange - getWidth() ;
        double heightDifference = getHeight() * percentChange - getHeight();
        topLeft.move(-widthDifference/2, heightDifference/2);
        bottomRight.move(widthDifference/2, -heightDifference/2);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public double getWidth(){
        return bottomRight.getX() - topLeft.getX();
    }
    public double getHeight(){
        return topLeft.getY() - bottomRight.getY();
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

}
