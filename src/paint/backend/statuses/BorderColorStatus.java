package paint.backend.statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.frontendFigures.FrontFigure;

public class BorderColorStatus extends ChangeStatus{
    private Color prevColor;
    private Color actualColor;
    public BorderColorStatus(FrontFigure frontFigure, CanvasState canvasState, Color prevColor,Color actualColor ) {
        super(frontFigure, canvasState);
        this.prevColor = prevColor;
        this.actualColor = actualColor;
    }

    // el undo de borderColor seria volver su colir al previous color.
    @Override
    public void executeOperation() {
        getFigure().setBorderColor(this.prevColor);
    }

    // el redo seria volver al color "actual" que no necesariamente es el actual que estamos viendo en pantalla.
    @Override
    public void executeInverseOperation() {
        getFigure().setBorderColor(this.actualColor);
    }

    //el string que aparecerá al lado del undo/redo button como proxima operacion para hacerle undo/redo
    @Override
    public String toString(){
        return String.format("Cambiar color de borde de %s", getFigure().getFigure().getFigureShape());
    }

}
