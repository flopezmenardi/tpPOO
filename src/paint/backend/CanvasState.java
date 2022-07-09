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
//        return stackOperation(undoStack, redoStack);
        ChangeStatus aux =undoStack.pop();
        redoStack.push(aux);
        return aux;
    }

    public ChangeStatus getRedo(){
      ChangeStatus aux = redoStack.pop();
      undoStack.push(aux);
      return aux;
    }

    public void makeRedoNull() {
        redoStack.clear();
    }

    public String getUndoOperationString(){
        //quiero retornar la operacion que se hara si hago undo
        //para esto debo hacer un peek del ultimo changeStatus en el stack de undo y acceder al string de su enum
        if(undoStack.empty())
            return "";
        return undoStack.peek().getOperationString();
    }

}
