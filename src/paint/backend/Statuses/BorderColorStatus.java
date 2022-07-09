package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class BorderColorStatus extends ChangeStatus{
    private Color prevColor;
    public BorderColorStatus(FrontFigure frontFigure, ChangesStrings type, CanvasState canvasState, Color color) {
        super(frontFigure, type, canvasState);
        prevColor = color;
    }

    @Override
    public void executeOperation() {
        Color aux = getOriginalCopy().getBorderColor();
        getOriginalCopy().setBorderColor(this.prevColor);
        prevColor = aux;
    }

    @Override
    public void executeInverseOperation() {
        getOriginalCopy().setBorderColor(this.prevColor);
    }

    @Override
    public String toString(){
        return String.format("Cambiar color de borde de %s", getOriginalCopy().getFigure().getFigureShape());
    }

    @Override
    public ChangeStatus inverseStatus(Color color) {
        return null;
    }
}
