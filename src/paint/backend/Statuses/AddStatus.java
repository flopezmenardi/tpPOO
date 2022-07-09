package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class AddStatus extends ChangeStatus{

    public AddStatus(FrontFigure frontFigure, CanvasState canvasState){
        super(frontFigure, canvasState);
    }

    @Override
    public void executeOperation() {
        getCanvasState().deleteFigure(getOriginalCopy());
    }

    @Override
    public void executeInverseOperation() {
    getCanvasState().addFigure(getOriginalCopy());
    }

    @Override
    public String toString(){
        return String.format("Agregar el %s", getOriginalCopy().getFigure().getFigureShape());
    }

}
