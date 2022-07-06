package paint.frontend.FrontendFigures;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Resize;

public abstract class FrontFigure implements Resize {
    private Color fillColor;
    private Color borderColor;
    private double borderSize;
    private GraphicsContext gc;

    public abstract boolean figureBelongs(Point eventPoint);
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

    public abstract Figure getBackFigure();
}
