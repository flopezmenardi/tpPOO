package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.FrontendFigures.FrontRectangle;

public class DeleteStatus extends ChangeStatus{
    public DeleteStatus(FrontFigure frontFigure, ChangesStrings type, CanvasState canvasState) {
        super(frontFigure, type,canvasState);
    }

    @Override
    public void executeOperation() {
        getCanvasState().addFigure(getOriginalCopy());
    }

    @Override
    public void executeInverseOperation() {

    }

    @Override
    public String toString(){
        return String.format("Borrar el %s", getOriginalCopy().getFigure().getFigureShape());
    }

    @Override
    public ChangeStatus inverseStatus(Color color) {
        return null;
    }

    @Override
    public FrontFigure figureToAdd() {
        return null;
    }

}
