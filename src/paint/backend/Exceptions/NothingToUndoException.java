package paint.backend.Exceptions;


// esta exception es utilizada en los casos en los que el usuario intenta rehacer cambios cuando no hay ningun cambio.
public class NothingToUndoException extends Exception{
    private static final String MESSAGE = "Nothing to Undo";
    public NothingToUndoException(){
        super(MESSAGE);
    }
}
