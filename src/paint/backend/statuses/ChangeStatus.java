package paint.backend.statuses;


import paint.backend.CanvasState;
import paint.frontend.frontendFigures.FrontFigure;

public abstract class ChangeStatus {
    // vamos a cuardar una
    private  FrontFigure figure;
    private CanvasState canvasState;

    public ChangeStatus(FrontFigure frontFigure, CanvasState canvasState) {
        figure = frontFigure;
       this.canvasState = canvasState;
    }
    // execute operation  refiere a la operacion del undo.
    public abstract void executeOperation();
    // el metodo excecuteInverseOperation hace lo inverso de executeOperation.
    // el inverso de borrar es agregar, el de agrandar achicar, el de cambiar color, es invertir los colores, etc
    public abstract void executeInverseOperation();
    // getters
    public CanvasState getCanvasState(){return canvasState;}
    public FrontFigure getFigure() {
        return figure;
    }
}
