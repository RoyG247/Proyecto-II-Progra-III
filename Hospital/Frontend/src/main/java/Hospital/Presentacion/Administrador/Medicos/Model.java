package Hospital.Presentacion.Administrador.Medicos;


import Hospital.Application;
import Hospital.Logic.Medico;
import Hospital.Presentacion.AbstractModel;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    Medico current;
    List<Medico> list;
    int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Medico();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public void init() {
        current = new Medico();
        mode = Application.MODE_CREATE;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Medico getCurrent() {
        return current;
    }

    public void setCurrent(Medico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Medico> getList() {
        return list;
    }

    public void setList(List<Medico> list) {
        List<Medico> old = this.list;
        this.list = list;
        firePropertyChange(LIST, old, list);
    }
    public void cargarDatos() {
        firePropertyChange(LIST);
    }
}
