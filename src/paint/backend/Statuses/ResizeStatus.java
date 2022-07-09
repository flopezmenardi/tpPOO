package paint.backend.Statuses;

import paint.backend.CanvasState;
import paint.frontend.FrontendFigures.FrontFigure;
// clase abstracta para un buen modelado de clases.
public abstract class ResizeStatus extends ChangeStatus {

    public ResizeStatus(FrontFigure frontFigure, CanvasState canvasState) {
        super(frontFigure, canvasState);
    }



}
