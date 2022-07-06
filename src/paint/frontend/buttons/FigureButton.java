package paint.frontend.buttons;

import javafx.scene.control.ToggleButton;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.frontend.FrontendFigures.FrontFigure;

public abstract class FigureButton extends ToggleButton {
    public FigureButton(String name){
        super(name);
    }
    public  abstract FrontFigure drawFigure(Point startPoint, Point endPoint);
}
