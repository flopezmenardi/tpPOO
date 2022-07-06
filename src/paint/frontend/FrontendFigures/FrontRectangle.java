package paint.frontend.FrontendFigures;

import javafx.scene.canvas.GraphicsContext;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Rectangle;

public class FrontRectangle extends FrontFigure{
    private Rectangle rectangle;
    public FrontRectangle(Point startPoint, Point bottomRight){
        rectangle = new Rectangle(startPoint,bottomRight);

    }
    @Override
    public boolean figureBelongs(Point eventPoint){
        return eventPoint.getX() > rectangle.getTopLeft().getX() && eventPoint.getX() < rectangle.getBottomRight().getX() &&
                eventPoint.getY() > rectangle.getTopLeft().getY() && eventPoint.getY() < rectangle.getBottomRight().getY();
    }
    @Override
    public void fill(GraphicsContext gc){
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }
    @Override
    public void stroke(GraphicsContext gc){
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }
    @Override
    public void resize(double percentage){
        //centerPoint no se modifica
        double percentChange = 1 + percentage/100; //si es -10 multiplico por 0,9 si es +10 multiplico por 1,1
        //calculamos el 10% de height/width
        double widthDifference = rectangle.getWidth() * percentChange - rectangle.getWidth() ;
        double heightDifference = rectangle.getHeight() * percentChange - rectangle.getHeight();
        rectangle.getTopLeft().move(-widthDifference/2, heightDifference/2);
        rectangle.getBottomRight().move(widthDifference/2, -heightDifference/2);
    }


    @Override
    public Figure getBackFigure() {
        return rectangle;
    }
    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", rectangle.getTopLeft(), rectangle.getBottomRight());
    }

}
