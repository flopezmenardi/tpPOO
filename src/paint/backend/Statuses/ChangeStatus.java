package paint.backend.Statuses;


import paint.frontend.FrontendFigures.FrontFigure;

public class ChangeStatus {
    private  FrontFigure copy;
    private  FrontFigure originalCopy;
    private final ChangesStrings type;
    public ChangeStatus(FrontFigure frontFigure, ChangesStrings type) {
        originalCopy = frontFigure;
        copy = frontFigure.copyFigure();
        copy.setID(originalCopy.getID());
        this.type = type;
    }

    @Override
    public String toString() {
        return getOperationString();
    }

    public FrontFigure figureToAdd() {
        return copy;
    }
    public FrontFigure figureToDelete(){return originalCopy;}

    public String getOperationString(){
        return type.getMESSAGE();
    }


}
