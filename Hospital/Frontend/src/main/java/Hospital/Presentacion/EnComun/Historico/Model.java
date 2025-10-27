package Hospital.Presentacion.EnComun.Historico;

import Hospital.Logic.Prescripcion;
import Hospital.Logic.Recetas;
import Hospital.Presentacion.AbstractModel;

import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Recetas current;
    List<Recetas> list;
    List<Prescripcion> listPrescripcion;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String LISTPRESCRIPCION = "listPrescripcion";

    public Model() {
        current = new Recetas(null, null);
        list = new ArrayList<>();
        listPrescripcion = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(LISTPRESCRIPCION);
    }

    public Recetas getCurrent() {
        return current;
    }

    public void setCurrent(Recetas current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Recetas> getList() {
        return list;
    }

    public void setList(List<Recetas> list) {
        List<Recetas> old = this.list;
        this.list = list;
        firePropertyChange(LIST, old, list);
    }

    public List<Prescripcion> getListPrescripcion() {
        return listPrescripcion;
    }

    public void setListPrescripcion(List<Prescripcion> listPrescripcion) {
        List<Prescripcion> old = this.listPrescripcion;
        this.listPrescripcion = listPrescripcion;
        firePropertyChange(LISTPRESCRIPCION, old, listPrescripcion);
    }
}
