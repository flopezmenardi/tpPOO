package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class ReduceStatus extends ResizeStatus{
    public ReduceStatus(FrontFigure frontFigure, CanvasState canvasState) {
        super(frontFigure, canvasState);
    }

    // el undo de haber achicado una Figure es volver a agrandarla.
    @Override
    public void executeOperation() {
        getFigure().enlarge();
    }

    // el redo seria volver a achicar la figura que se habia agrandado por el undo.
    @Override
    public void executeInverseOperation() {
        getFigure().reduce();
    }

    @Override
    public String toString(){
        return String.format("Achicar %s", getFigure().getFigure().getFigureShape());
    }


}
