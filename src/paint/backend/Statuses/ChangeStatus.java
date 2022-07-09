package paint.backend.Statuses;


import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;

public abstract class ChangeStatus {
    private  FrontFigure copy;
    private  FrontFigure originalCopy;
    private CanvasState canvasState;

    public ChangeStatus(FrontFigure frontFigure, CanvasState canvasState) {
        originalCopy = frontFigure;
        copy = frontFigure.copyFigure();
        copy.setID(originalCopy.getID());
        this.canvasState = canvasState;
    }

    public abstract void executeOperation();
    public abstract void executeInverseOperation();
    public CanvasState getCanvasState(){return canvasState;}
    public FrontFigure getOriginalCopy() {
        return originalCopy;
    }
}
