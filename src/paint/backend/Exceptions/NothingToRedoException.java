package paint.backend.Exceptions;

public class NothingToRedoException extends Exception{
    private static final String MESSAGE = "Nothing to Redo";
    public NothingToRedoException(){
        super(MESSAGE);
    }
}
