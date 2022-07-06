package paint.frontend.FrontendFigures;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.frontend.Interfaces.Resize;

public abstract class FrontFigure implements Resize {
    private Color fillColor;
    private Color borderColor;
    private double borderSize;
    private GraphicsContext gc;
    private Figure figure;

    public FrontFigure(Figure figure, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc){
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.borderSize = borderSize;
        this.figure = figure;
    }


    public boolean figureBelongs(Point eventPoint){
        //aca deberiamos hacer return figure.containsPoint pero no tenemos una figura generica
        return true;
    }
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void resize(double percent);

    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
    public void setBorderColor(Color borderColor){
        this.borderColor = borderColor;
    }
    public void setBorderSize(double borderSize){
        this.borderSize = borderSize;
    }

    public Figure getFigure() {
        return figure;
    }
}
