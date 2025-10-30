package Hospital.Presentacion.Medicos.Prescripcion;

import Hospital.Logic.Medicamentos;
import Hospital.Logic.Pacientes;
import Hospital.Logic.Prescripcion;
import Hospital.Presentacion.AbstractModel;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;
import Hospital.Logic.Recetas;

public class Model extends AbstractModel{
    Recetas current;
    Prescripcion currentPrescripcion;
    List<Prescripcion> listPrescripciones;
    List<Pacientes> listPacientes;
    List<Medicamentos> listMedicamentos;

    public static final String CURRENT = "current";
    public static final String LISTPRESCRIPCIONES = "listPrescripciones";
    public static final String LISTPACIENTES = "listPacientes";
    public static final String LISTMEDICAMENTOS = "listMedicamentos";


    public Model() {
        current = new Recetas(new ArrayList<>(), null);
        listPrescripciones = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LISTPRESCRIPCIONES);
        firePropertyChange(LISTPACIENTES);
        firePropertyChange(LISTMEDICAMENTOS);
    }

    public Recetas getCurrent() {
        return current;
    }

    public void setCurrent(Recetas current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Prescripcion> getListPrescripciones() {
        return listPrescripciones;
    }

    public void setListPrescripciones(List<Prescripcion> listPrescripciones) {
        List<Prescripcion> old = this.listPrescripciones;
        this.listPrescripciones = listPrescripciones;
        firePropertyChange(LISTPRESCRIPCIONES, old, listPrescripciones);
    }

    public List<Pacientes> getListPacientes() {
        return listPacientes;
    }

    public void setListPacientes(List<Pacientes> listPacientes) {
        this.listPacientes = listPacientes;
        firePropertyChange(LISTPACIENTES);
    }

    public List<Medicamentos> getListMedicamentos() {
        return listMedicamentos;
    }

    public void setListMedicamentos(List<Medicamentos> lista) {
        List<Medicamentos> old = this.listMedicamentos;
        this.listMedicamentos = lista;
        firePropertyChange(LISTMEDICAMENTOS, old, lista);
    }




    public  Prescripcion getCurrentPrescripcion() {
        return currentPrescripcion;
    }
    public void setCurrentPrescripcion(Prescripcion currentPrescripcion) {
        this.currentPrescripcion = currentPrescripcion;
    }


    public void addPrescripcion(Prescripcion p) {
        List<Prescripcion> old = new ArrayList<>(this.listPrescripciones);
        this.listPrescripciones.add(p);
        firePropertyChange(LISTPRESCRIPCIONES, old, this.listPrescripciones);
    }

}
