package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.FrontendFigures.FrontRectangle;

public class DeleteStatus extends ChangeStatus{
    public DeleteStatus(FrontFigure frontFigure, CanvasState canvasState) {
        super(frontFigure,canvasState);
    }

    @Override
    public void executeOperation() {
        getCanvasState().addFigure(getOriginalCopy());
    }

    @Override
    public void executeInverseOperation() {
        getCanvasState().deleteFigure(getOriginalCopy());
    }

    @Override
    public String toString(){
        return String.format("Borrar el %s", getOriginalCopy().getFigure().getFigureShape());
    }

}
