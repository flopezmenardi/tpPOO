package paint.backend;

import paint.backend.Statuses.ChangeStatus;
import paint.frontend.FrontendFigures.FrontFigure;

import java.util.*;

public class CanvasState {

    private final List<FrontFigure> list = new ArrayList<>();

    public void addFigure(FrontFigure figure) {
        list.add(figure);
    }

    public void deleteFigure(FrontFigure figure) {
        list.remove(figure);
    }

    public Iterable<FrontFigure> figures() {
        return new ArrayList<>(list);
    }

    private Stack<ChangeStatus> undoStack = new Stack<>();
    private Stack<ChangeStatus> redoStack = new Stack<>();
    
    private ChangeStatus stackOperation(Stack<ChangeStatus> undo, Stack<ChangeStatus> redo){
        ChangeStatus aux = undo.pop();
        redo.push(aux);
        return aux;
    }

    public void redoPush(ChangeStatus status){
        redoStack.push(status);
    }

    public void undoPush(ChangeStatus status){
        undoStack.push(status);
    }
    
    public ChangeStatus getUndo(){
        return stackOperation(undoStack, redoStack);
    
    }
    public ChangeStatus getRedo(){
       return stackOperation(redoStack, undoStack);
    }

    public void makeRedoNull() {
        redoStack.clear();
    }

}
