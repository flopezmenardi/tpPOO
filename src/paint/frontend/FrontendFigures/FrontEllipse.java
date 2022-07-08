package paint.frontend.FrontendFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Ellipse;
import paint.backend.model.Figure;
import paint.backend.model.Point;

public class FrontEllipse extends FrontFigure{
    //private final Ellipse ellipse;
    public FrontEllipse(Figure figure, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc){
        super(figure, fillColor, borderColor, borderSize, gc);
}

    @Override
    public void fill(GraphicsContext gc){
        gc.fillOval(getFigure().getX() - (getFigure().getWidth() / 2), getFigure().getY() - (getFigure().getHeight() / 2), getFigure().getWidth(), getFigure().getHeight());
    }
    @Override
    public void stroke(GraphicsContext gc){
        gc.strokeOval(getFigure().getX() - (getFigure().getWidth() / 2), getFigure().getY() - (getFigure().getHeight() / 2), getFigure().getWidth(), getFigure().getHeight());
    }

    @Override
    public void resize(double percent) {
        double percentChange = 1 + percent/100;
        getFigure().setWidth(percentChange* getFigure().getWidth());
        getFigure().setHeight(percentChange* getFigure().getHeight());
    }

    @Override
    public FrontFigure copyFigure(){
        return new FrontEllipse(getFigure(), getFillColor(), getBorderColor(), getBorderSize(), getGc());
    }

//    @Override
//    public Figure getBackFigure() {
//        return ellipse;
//    }



}
