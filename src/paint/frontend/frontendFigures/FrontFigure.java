package paint.frontend.frontendFigures;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paint.backend.model.Figure;
import paint.backend.model.Point;
import paint.frontend.interfaces.Resize;

/*Una frontfigure recibe una Figure del back para guardarla, y ademas todos los datos de una figura que
* pertenecen al front como el fillcolor, bordercolor y bordersize.
* Para los metodos que se implementaran en estas clases, no es necesario tener clases particulares para
* un circulo y un cuadrado, ya que el rectangulo puede guardar una instancia de cuadrado y los metodos
* son iguales para ambos al igual que la elipse con una instancia de circulo*/

public abstract class FrontFigure implements Resize {
    private Color fillColor;
    private Color borderColor;
    private double borderSize;
    //tambien necesitamos el graphicscontext para los metodos fill y stroke
    private GraphicsContext gc;
    private Figure figure;

    public FrontFigure(Figure figure, Color fillColor, Color borderColor, double borderSize, GraphicsContext gc){
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.borderSize = borderSize;
        this.figure = figure;
    }

    //retorna si el punto dado pertenece a una figura
    public boolean figureBelongs(Point eventPoint){
        //llamamos al metodo containsPoint del back
        return figure.containsPoint(eventPoint);
    }
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void resize(double percent);

    //cambiar el color de fill de una figura
    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
    //cambiar el color del borde de una figura
    public void setBorderColor(Color borderColor){
        this.borderColor = borderColor;
    }
    //cambiar el tamaño del borde de una figura
    public void setBorderSize(double borderSize){
        this.borderSize = borderSize;
    }
    //retorna la instancia de figura del back que tenemos guardada
    public Figure getFigure() {
        return figure;
    }
    /*queremos que la frontFigure tenga retorne el mismo string que la figura en el back, ya que para el status
    * pane no necesitamos mostrar propiedades del fill/borde, simplemente las caracteristicas basicas de la figura */
    public String toString(){return figure.toString();}
    //retorna el color de relleno de la figura
    public Color getFillColor() {
        return fillColor;
    }
    //retorna el color del borde de la figura
    public Color getBorderColor() {
        return borderColor;
    }
    //retorna el graphicsContext
    public GraphicsContext getGc(){
        return gc;
    }
    //retorna el tamaño del borde de la figura
    public double getBorderSize() {
        return borderSize;
    }

}
