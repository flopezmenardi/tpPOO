package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class AddStatus extends ChangeStatus{

    public AddStatus(FrontFigure frontFigure, CanvasState canvasState){
        super(frontFigure, canvasState);
    }

    // el undo de add seria deleteFigure
    @Override
    public void executeOperation() {
        getCanvasState().deleteFigure(getFigure());
    }

    // el redo de add seria volver a agregar la figura
    @Override
    public void executeInverseOperation() {
    getCanvasState().addFigure(getFigure());
    }

    //el string que aparecerá al lado del undo/redo button como proxima operacion para hacerle undo/redo
    @Override
    public String toString(){
        return String.format("Agregar el %s", getFigure().getFigure().getFigureShape());
    }

}
