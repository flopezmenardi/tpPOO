package paint.backend.model;

public class Square extends Rectangle {
    private double size;
    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public Figure copyBackFigure(){
        return new Square(getTopLeft(),size);
    }

    @Override
    public String getFigureShape(){
        return "Cuadrado";
    }

}
