package paint.frontend.buttons;

import javafx.scene.control.ToggleButton;
import paint.backend.model.Figure;
import paint.backend.model.Point;

public abstract class FigureButton extends ToggleButton {
    public FigureButton(String name){
        super(name);
    }
    public  abstract Figure drawFigure(Point startPoint, Point endPoint);
}
