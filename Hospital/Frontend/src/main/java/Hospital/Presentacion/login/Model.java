package Hospital.Presentacion.login;

import Hospital.Presentacion.AbstractModel;
import Hospital.Logic.Empleado;
import Hospital.Presentacion.Sesion;

import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    Empleado current;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Empleado("", "", "", "");
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }
    public Empleado getCurrent() {
        return current;
    }

    public void setCurrent(Empleado current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public boolean isFirstLogin() {
        return Sesion.firstLogin();
    }



}