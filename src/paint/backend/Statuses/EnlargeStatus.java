package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class EnlargeStatus extends ResizeStatus{

    public EnlargeStatus(FrontFigure frontFigure, CanvasState canvasState) {
        super(frontFigure, canvasState);
    }

    // el undo de haber agrandado una Figure es volver a reducirla.
    @Override
    public void executeOperation() {
        getFigure().reduce();
    }

    // el redo seria volver a agrandar la figura que se habia achicado por el undo.
    @Override
    public void executeInverseOperation() {
        getFigure().enlarge();
    }

    @Override
    public String toString(){
        return String.format("Agrandar %s", getFigure().getFigure().getFigureShape());
    }

}
