package paint.frontend.FrontendFigures;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.frontend.Interfaces.Resize;

import java.util.Objects;

public abstract class FrontFigure implements Resize {
    public static int IDBASE = 1000;
    private int myId;
    private Color fillColor;
    private Color borderColor;
    private double borderSize;
    private GraphicsContext gc;
    private Figure figure;

    public FrontFigure(Figure figure, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc){
        myId = IDBASE;
        IDBASE +=1;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.borderSize = borderSize;
        this.figure = figure;
    }


    public boolean figureBelongs(Point eventPoint){
        //aca deberiamos hacer return figure.containsPoint pero no tenemos una figura generica
        return figure.containsPoint(eventPoint);
    }
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void resize(double percent);

    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
    public void setBorderColor(Color borderColor){
        this.borderColor = borderColor;
    }
    public void setBorderSize(double borderSize){
        this.borderSize = borderSize;
    }

    public Figure getFigure() {
        return figure;
    }
    public String toString(){return figure.toString();}

    public Color getFillColor() {
        return fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public GraphicsContext getGc(){
        return gc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontFigure that = (FrontFigure) o;
        return myId == that.myId;
    }


    public void setID(int myId){this.myId =  myId;}
    public int getID(){return myId;}
    public abstract FrontFigure copyFigure();

    public double getBorderSize() {
        return borderSize;
    }

}
