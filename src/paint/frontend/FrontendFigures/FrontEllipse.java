package paint.frontend.FrontendFigures;

import javafx.scene.canvas.GraphicsContext;
import paint.backend.model.Ellipse;
import paint.backend.model.Figure;
import paint.backend.model.Point;

public class FrontEllipse extends FrontFigure{
    private Ellipse ellipse;
    public FrontEllipse(Point centerPoint,double sMayorAxis, double sMinorAxis){
    ellipse = new Ellipse(centerPoint,sMayorAxis,sMinorAxis);
}
    @Override
    public boolean figureBelongs(Point eventPoint){
        return ((Math.pow(eventPoint.getX() - ellipse.getCenterPoint().getX(), 2) / Math.pow(ellipse.getsMayorAxis(), 2)) +
                (Math.pow(eventPoint.getY() - ellipse.getCenterPoint().getY(), 2) / Math.pow(ellipse.getsMinorAxis(), 2))) <= 0.30;
    }
    @Override
    public void fill(GraphicsContext gc){
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
    @Override
    public void stroke(GraphicsContext gc){
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

    @Override
    public void resize(double percent) {
        double percentChange = 1 + percent/100;
        ellipse.setMayorAxis(percentChange* ellipse.getsMayorAxis());
        ellipse.setMinorAxis(percentChange* ellipse.getsMinorAxis());
    }

    @Override
    public Figure getBackFigure() {
        return ellipse;
    }

}
