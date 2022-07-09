package paint.backend.Statuses;


import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public abstract class ChangeStatus {
    private  FrontFigure copy;
    private  FrontFigure originalCopy;
    private CanvasState canvasState;
    private final ChangesStrings type;
    public ChangeStatus(FrontFigure frontFigure, ChangesStrings type, CanvasState canvasState) {
        originalCopy = frontFigure;
        copy = frontFigure.copyFigure();
        copy.setID(originalCopy.getID());
        this.type = type;
        this.canvasState = canvasState;
    }

    public abstract void executeOperation();
    public abstract void executeInverseOperation();


    @Override
    public String toString() {
        return getOperationString();
    }

    public abstract ChangeStatus inverseStatus(Color color);

    public FrontFigure figureToAdd() {
        return copy;
    }
    public FrontFigure figureToDelete(){return originalCopy;}

    public String getOperationString(){
        return type.getMESSAGE();
    }
    public CanvasState getCanvasState(){return canvasState;}

    public FrontFigure getOriginalCopy() {
        return originalCopy;
    }
}
