package paint.frontend.interfaces;

public interface Resize {
    public default void enlarge(){
        resize(10);
    }
    public default void reduce(){
        resize(-10);
    }
    public void resize(double percent);
}
