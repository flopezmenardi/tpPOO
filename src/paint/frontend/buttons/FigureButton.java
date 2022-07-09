package paint.frontend.buttons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import paint.backend.model.Point;
import paint.frontend.FrontendFigures.FrontFigure;

// hicimos que extienda a ToggleButton pues la idea es que los botones de figures se mantengan apetados,
// para asi poder crear muchas figuras sin tener que tocar todo el tiempo el boton de la figura.

public abstract class FigureButton extends ToggleButton {
    public FigureButton(String name){
        super(name);
    }

    // metodo drawFigure se encarga de dibujar su Frontfigure correspondiente. Se la invoca desde el paintPane.

    public  abstract FrontFigure drawFigure(Point startPoint, Point endPoint, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc);
}
