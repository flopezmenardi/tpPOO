package paint.backend.Exceptions;
// esta exception se usa en los casos que se intenta realizar
// alguna modificacion(enlarge, reduce,fill, border size, etc) cuando no hay ninguna figura seleccionada.
public class NoSelectedFigureException extends Exception{
    private final static String MESSAGE = "No figure selected";
    public NoSelectedFigureException(){
        super(MESSAGE);
    }
}
