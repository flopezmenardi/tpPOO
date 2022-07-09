package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class EnlargeStatus extends ResizeStatus{

    public EnlargeStatus(FrontFigure frontFigure, ChangesStrings type, CanvasState canvasState) {
        super(frontFigure, type, canvasState);
    }

    @Override
    public void executeOperation() {
        getOriginalCopy().reduce();
    }

    @Override
    public void executeInverseOperation() {

    }

    @Override
    public String toString(){
        return String.format("Agrandar %s", getOriginalCopy().getFigure().getFigureShape());
    }

    @Override
    public ChangeStatus inverseStatus(Color color) {
        return null;
    }
}
