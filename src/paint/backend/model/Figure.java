package paint.backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Movable,Resize {
    @Override
    public abstract void move(double diffX, double diffY);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract boolean figureBelongs(Point eventPoint);

    @Override
    public abstract void resize(double percent);
}
