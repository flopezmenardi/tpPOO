package paint.backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure {
    private Color fillColor, borderColor;
    private double borderSize;
    public double getBorderSize(){
        return borderSize;
    }
    public Color getFillColor() {
        return fillColor;
    }
    public Color getBorderColor() {
        return borderColor;
    }
    public void setBorderSize(double size){
        borderSize = size;
    }
    public void setFillColor(Color color){
        fillColor = color;
    }
    public void setBorderColor(Color color){
        borderColor = color;
    }

    public abstract void move(double diffX, double diffY);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract boolean figureBelongs(Point eventPoint);

    public void enlarge(){
        resize(10);
    }
    public void reduce(){
        resize(-10);
    }
    public abstract void resize(double percent);
}
