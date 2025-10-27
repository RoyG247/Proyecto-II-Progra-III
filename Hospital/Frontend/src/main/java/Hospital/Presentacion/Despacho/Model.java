package Hospital.Presentacion.Despacho;

import Hospital.Logic.Recetas;
import Hospital.Presentacion.AbstractModel;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    List<Recetas> recetas;
    Recetas seleccionada;


    public static final String RECETAS = "recetas";
    public static final String SELECCIONADA = "seleccionada";

    public Model() {
        recetas = new ArrayList<>();
        seleccionada = new Recetas(null, null);
    }

    public List<Recetas> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Recetas> recetas) {
        List<Recetas> old= this.recetas;
        this.recetas = recetas;
        firePropertyChange(RECETAS, old, recetas);
    }
    public Recetas getSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(Recetas seleccionada) {
        this.seleccionada = seleccionada;
        firePropertyChange(SELECCIONADA);
    }
}
