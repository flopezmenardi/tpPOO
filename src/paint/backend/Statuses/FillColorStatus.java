package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;



public class FillColorStatus extends ChangeStatus{
    private Color prevColor;
    private Color actualColor;
    public FillColorStatus(FrontFigure frontFigure, CanvasState canvasState, Color prevColor, Color actualColor) {
        super(frontFigure, canvasState);
        this.prevColor = prevColor;
        this.actualColor = actualColor;
    }

    @Override
    public void executeOperation() {
        getOriginalCopy().setFillColor(this.prevColor);

        // agrego al stack del redo y que no se encargue de eso el getUNDO

    }


    @Override
    public void executeInverseOperation() {
        getOriginalCopy().setFillColor(this.actualColor);

    }

    @Override
    public String toString(){
        return String.format("Cambiar color de relleno de %s", getOriginalCopy().getFigure().getFigureShape());
    }

}
