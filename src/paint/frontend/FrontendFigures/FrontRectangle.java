package paint.frontend.FrontendFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.backend.model.Rectangle;

public class FrontRectangle extends FrontFigure{
    public FrontRectangle(Figure fig, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc){
        super(fig,fillColor,borderColor,borderSize,gc);
    }

    @Override
    public void fill(GraphicsContext gc){
        gc.fillRect(getFigure().getX(), getFigure().getY(), getFigure().getWidth(), getFigure().getHeight());
    }
    @Override
    public void stroke(GraphicsContext gc){
        gc.strokeRect(getFigure().getX(), getFigure().getY(), getFigure().getWidth(), getFigure().getHeight());
    }
    @Override
    public void resize(double percentage) {
        double percentChange = 1 + percentage / 100; //si es -10 multiplico por 0,9 si es +10 multiplico por 1,1
        //Calculamos la nueva height y width de la figura y usamos el seter.
        getFigure().setHeight(getFigure().getHeight() * percentChange);
        getFigure().setWidth(getFigure().getWidth() * percentChange);
    }

    @Override
    public FrontFigure copyFigure() {
        return new FrontRectangle(getFigure(), getFillColor(), getBorderColor(), getBorderSize(), getGc());
    }
}
