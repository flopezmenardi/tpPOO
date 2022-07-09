package paint.backend.statuses;

import paint.backend.CanvasState;
import paint.frontend.frontendFigures.FrontFigure;

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

    //el string que aparecerá al lado del undo/redo button como proxima operacion para hacerle undo/redo
    @Override
    public String toString(){
        return String.format("Borrar el %s", getFigure().getFigure().getFigureShape());
    }

}
