package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class BorderColorStatus extends ChangeStatus{
    private Color prevColor;
    private Color actualColor;
    public BorderColorStatus(FrontFigure frontFigure, CanvasState canvasState, Color prevColor,Color actualColor ) {
        super(frontFigure, canvasState);
        this.prevColor = prevColor;
        this.actualColor = actualColor;
    }

    @Override
    public void executeOperation() {

        getOriginalCopy().setBorderColor(this.prevColor);

    }

    @Override
    public void executeInverseOperation() {
        getOriginalCopy().setBorderColor(this.actualColor);
    }

    @Override
    public String toString(){
        return String.format("Cambiar color de borde de %s", getOriginalCopy().getFigure().getFigureShape());
    }

}
