package Hospital.Presentacion.Administrador.Medicamento;


import Hospital.Application;
import Hospital.Logic.Medicamentos;
import Hospital.Presentacion.AbstractModel;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    Medicamentos current;
    List<Medicamentos> list;
    int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Medicamentos("","","");
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public void init() {
        current = new Medicamentos("","","");
        mode = Application.MODE_CREATE;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Medicamentos getCurrent() {
        return current;
    }

    public void setCurrent(Medicamentos current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Medicamentos> getList() {
        return list;
    }

    public void setList(List<Medicamentos> list) {
        List<Medicamentos> old = this.list;
        this.list = list;
        firePropertyChange(LIST, old, list);
    }
    public void cargarDatos() {
        firePropertyChange(LIST);
    }
}
