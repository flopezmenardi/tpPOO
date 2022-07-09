package paint.backend;

import paint.backend.exceptions.NothingToRedoException;
import paint.backend.exceptions.NothingToUndoException;
import paint.backend.statuses.ChangeStatus;
import paint.frontend.frontendFigures.FrontFigure;
import java.util.*;

public class CanvasState {
    private final Stack<ChangeStatus> undoStack;
    private final Stack<ChangeStatus> redoStack;
    private final List<FrontFigure> list;

    public CanvasState(){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        list = new ArrayList<>();
    }

    /* Lista donde guardamos todas las figuras que estan en el canvas.
     modificamos la implementacion de la catedra haciendo que guarden FrontFigures en vez de Figures. */



    // agrega una Figure a la lista de figures del canvas.

    public void addFigure(FrontFigure figure) {
        if (figure != null)
            list.add(figure);
    }

    // elimina una Figure de la lista. No es necesario que las figures implementen el equals porque con la comparacion
    // de direccion de memoria nos alcanza.

    public void deleteFigure(FrontFigure figure) {
        list.remove(figure);
    }

    // retorna una lista con todas las figuras presentes en el canvas

    public Iterable<FrontFigure> figures() {
        return new ArrayList<>(list);
    }


    // metodo que agrega un change al stack del undo
    // (se llama cuando se crea un nuevo cambio en el canvas)

    public void undoPush(ChangeStatus status){
        undoStack.push(status);
    }

    // cada vez que se presiona el boton undo, primero se pregunta que haya algo para deshacer. En el caso de que haya,
    // se devuelve ese "cambio", y se agrega el mismo cambio al stack de redo.

    public ChangeStatus getUndo() throws NothingToUndoException{
        if(undoStack.isEmpty())
            throw new NothingToUndoException();
        ChangeStatus aux =undoStack.pop();
        redoStack.push(aux);
        return aux;
    }


    // cada vez que se presiona el boton redo, primero se pregunta que haya algo para rehacer. En el caso de que haya,
    // se devuelve ese "cambio", y se agrega el mismo cambio al stack de undo.

    public ChangeStatus getRedo() throws NothingToRedoException{
        if(redoStack.isEmpty())
           throw new NothingToRedoException();
      ChangeStatus aux = redoStack.pop();
      undoStack.push(aux);
      return aux;
    }

    // usamos este metodo pues la consigna dice que si se hace un undo y despues se agrega una figura,
    // se debe vaciar el stack de redo

    public void makeRedoNull() {
        redoStack.clear();
    }

    /* se usan para las labels de los botomes de redo y undo
    quiero retornar la operacion que se hara si hago undo
    para esto debo hacer un peek del ultimo changeStatus en el stack de undo y acceder al toString de la misma. */

    public String getUndoOperationString(){
        if(undoStack.empty())
            return "";
        return undoStack.peek().toString();
    }
    // analogo al getUndoOperationString pero con redo.

    public String getRedoOperationString(){
        if(redoStack.isEmpty())
            return "";
        return redoStack.peek().toString();
    }

}
