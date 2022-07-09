package paint.frontend.buttons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Ellipse;
import paint.backend.model.Point;
import paint.frontend.FrontendFigures.FrontEllipse;
import paint.frontend.FrontendFigures.FrontFigure;

public class ElipseButton extends FigureButton{

    public ElipseButton(String name){super(name);}

    @Override
    public FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc) {
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
       return new FrontEllipse(new Ellipse(centerPoint,sMayorAxis,sMinorAxis), fillColor, borderColor, borderSize, gc);
    }
}
