package paint.backend.Statuses;


import paint.frontend.FrontendFigures.FrontFigure;

public class ChangeStatus {
    private final FrontFigure copy;
    private final ChangesStrings type;
    public ChangeStatus(FrontFigure frontFigure, ChangesStrings type) {
        copy = frontFigure.copyFigure();
        this.type = type;
    }

    @Override
    public String toString() {
        return null;
    }

    public FrontFigure figureToReturn() {
        return copy;
    }

}
