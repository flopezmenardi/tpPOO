package paint.backend.model;

@FunctionalInterface
public interface Movable {
    public abstract void move(double diffX, double diffY);
}
