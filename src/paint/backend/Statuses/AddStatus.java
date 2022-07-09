package paint.backend.Statuses;

import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public class AddStatus extends ChangeStatus{

    public AddStatus(FrontFigure frontFigure, ChangesStrings type, CanvasState canvasState){
        super(frontFigure, type, canvasState);
    }

    @Override
    public void executeOperation() {
        getCanvasState().deleteFigure(getOriginalCopy());
    }

    @Override
    public void executeInverseOperation() {

    }

    @Override
    public String toString(){
        return String.format("Agregar el %s", getOriginalCopy().getFigure().getFigureShape());
    }

    @Override
    public ChangeStatus inverseStatus(Color color) {
        return null;
    }
}
