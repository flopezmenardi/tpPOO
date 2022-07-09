package paint.frontend.frontendFigures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Figure;


public class FrontEllipse extends FrontFigure{

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


}
