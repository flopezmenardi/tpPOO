package paint.backend.exceptions;

// esta exception es utilizada en los casos en los que el usuario intenta deshacer cambios cuando no hay ningun cambio.
public class NothingToRedoException extends Exception{
    private static final String MESSAGE = "Nothing to Redo";
    public NothingToRedoException(){
        super(MESSAGE);
    }
}
