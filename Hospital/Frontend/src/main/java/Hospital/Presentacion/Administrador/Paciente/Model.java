package Hospital.Presentacion.Administrador.Paciente;

import Hospital.Application;
import Hospital.Logic.Medico;
import Hospital.Presentacion.AbstractModel;
import Hospital.Logic.Pacientes;

import java.beans.PropertyChangeListener;
import java.util.List;

public class Model extends AbstractModel {
    Pacientes current;
    List<Pacientes> list;
    int mode;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model(){
        current = new Pacientes("","","", null);
    }
    public Pacientes getCurrent(){
        return this.current;
    }

    public void setCurrent(Pacientes current){
        this.current = current;
        firePropertyChange(CURRENT);
    }
    public List<Pacientes> getList(){
        return list;
    }

    public void setList(List<Pacientes> list){
        List<Pacientes> old = this.list;
        this.list = list;
        firePropertyChange(LIST, old, list);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public void init(){
        current = new Pacientes("","","", null);
        mode =  Application.MODE_CREATE;
    }

    public int getMode(){
        return mode;
    }

    public void setMode(int mode){
        this.mode = mode;
    }

    public void cargarDatos(){
        firePropertyChange(LIST);
    }

}
