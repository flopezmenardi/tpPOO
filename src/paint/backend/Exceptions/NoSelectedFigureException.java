package paint.backend.Exceptions;

public class NoSelectedFigureException extends Exception{
    private final static String MESSAGE = "No figure selected";
    public NoSelectedFigureException(){
        super(MESSAGE);
    }
}
