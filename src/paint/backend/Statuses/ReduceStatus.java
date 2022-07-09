package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class ReduceStatus extends ResizeStatus{
    public ReduceStatus(FrontFigure frontFigure, CanvasState canvasState) {
        super(frontFigure, canvasState);
    }

    @Override
    public void executeOperation() {
        getOriginalCopy().enlarge();
    }

    @Override
    public void executeInverseOperation() {
        getOriginalCopy().reduce();
    }

    @Override
    public String toString(){
        return String.format("Achicar %s", getOriginalCopy().getFigure().getFigureShape());
    }


}
