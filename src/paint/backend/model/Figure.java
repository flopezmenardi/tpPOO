package paint.backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Movable {
    @Override
    public abstract void move(double diffX, double diffY);

}
