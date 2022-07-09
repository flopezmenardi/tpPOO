package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;
import paint.frontend.FrontendFigures.FrontRectangle;

public class DeleteStatus extends ChangeStatus{
    public DeleteStatus(FrontFigure frontFigure, CanvasState canvasState) {
        super(frontFigure,canvasState);
    }

    // el undo de delete seria volver a agregar la figura al canvas.
    @Override
    public void executeOperation() {
        getCanvasState().addFigure(getFigure());
    }

    // el redo seria volver a eliminar la figura del canvas.
    @Override
    public void executeInverseOperation() {
        getCanvasState().deleteFigure(getFigure());
    }

    @Override
    public String toString(){
        return String.format("Borrar el %s", getFigure().getFigure().getFigureShape());
    }

}
