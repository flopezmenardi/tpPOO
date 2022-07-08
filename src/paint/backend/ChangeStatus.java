package paint.backend;

import paint.backend.model.Figure;

public abstract class ChangeStatus {
private Figure figure;
public abstract String toString();
public  void undo(){
    // setear el to string a la caja de datitta

}
public abstract void redo();
}
