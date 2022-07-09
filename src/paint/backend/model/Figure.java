package paint.backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Movable {



    /*creamos los siguientes metodos abstractos que en rectangulo representan su altura y ancho y en
    * circulo/elipse representa su eje mayor y menor así podemos guardar las figuras en una variable Figure
    * en FrontFigure. */

    public  abstract double getHeight();
    public abstract  double getWidth();
    //para elipse y circulo, getX y getY retornan las coordenadas del centerpoint
    //para el rectangulo y cuadrado, getX y getY retornan las coordenadas del topLeft
    public abstract double getX();
    public abstract double getY();
    public abstract void setHeight(double height);
    public abstract void setWidth(double width);
    @Override
    public abstract void move(double diffX, double diffY);
    public abstract boolean containsPoint(Point eventPoint);
    //este metodo nos retorna una copia de la figura:
    public abstract Figure copyBackFigure();
}
