package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class EnlargeStatus extends ResizeStatus{

    public EnlargeStatus(FrontFigure frontFigure, CanvasState canvasState) {
        super(frontFigure, canvasState);
    }

    @Override
    public void executeOperation() {
        getOriginalCopy().reduce();
    }

    @Override
    public void executeInverseOperation() {
        getOriginalCopy().enlarge();
    }

    @Override
    public String toString(){
        return String.format("Agrandar %s", getOriginalCopy().getFigure().getFigureShape());
    }

}
