package paint.backend.statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.frontendFigures.FrontFigure;



public class FillColorStatus extends ChangeStatus{
    private Color prevColor;
    private Color actualColor;
    public FillColorStatus(FrontFigure frontFigure, CanvasState canvasState, Color prevColor, Color actualColor) {
        super(frontFigure, canvasState);
        this.prevColor = prevColor;
        this.actualColor = actualColor;
    }

    // el undo seria volver a setear la figura con el color anterior a ser modificado.
    @Override
    public void executeOperation() {
        getFigure().setFillColor(this.prevColor);
    }
    // el redo seria volver a poner la figuta con el color "actual",
    // que no necesariamente es el que se muestra en pantalla.
    @Override
    public void executeInverseOperation() {
        getFigure().setFillColor(this.actualColor);
    }

    //el string que aparecerá al lado del undo/redo button como proxima operacion para hacerle undo/redo
    @Override
    public String toString(){
        return String.format("Cambiar color de relleno de %s", getFigure().getFigure().getFigureShape());
    }

}
