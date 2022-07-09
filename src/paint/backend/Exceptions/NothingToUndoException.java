package paint.backend.Exceptions;

public class NothingToUndoException extends Exception{
    private static final String MESSAGE = "Nothing to Undo";
    public NothingToUndoException(){
        super(MESSAGE);
    }
}
