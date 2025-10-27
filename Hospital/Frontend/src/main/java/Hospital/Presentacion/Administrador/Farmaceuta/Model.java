package Hospital.Presentacion.Administrador.Farmaceuta;
import Hospital.Application;
import Hospital.Logic.Farmaceuta;
import Hospital.Presentacion.AbstractModel;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    Farmaceuta current;
    List<Farmaceuta> list;
    int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Farmaceuta();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public void init() {
        current = new Farmaceuta();
        mode = Application.MODE_CREATE;
    }

    public int getMode() {
        return mode;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }

    public Farmaceuta getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceuta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Farmaceuta> getList() {
        return list;
    }

    public void setList(List<Farmaceuta> list) {
        List<Farmaceuta> old = this.list;
        this.list = list;
        firePropertyChange(LIST, old, list);
    }

    public void cargarDatos() {
        firePropertyChange(LIST);
    }
}