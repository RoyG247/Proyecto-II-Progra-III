package Hospital.Presentacion.Medicos.Prescripcion;

import Hospital.Logic.*;
import Hospital.Presentacion.Sesion;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void readPaciente(String id) throws Exception {
        Pacientes e = new Pacientes();
        e.setId(id);
        try {
            model.getCurrent().setPaciente(Service.instance().read(e));
        } catch (Exception ex) {
            Pacientes b = new Pacientes();
            b.setId(id);
            model.getCurrent().setPaciente(b);
            throw ex;
        }
    }

    public void create(Prescripcion p) throws Exception {
        model.getCurrent().getPrescripciones().add(p);
        model.setListPrescripciones(model.getCurrent().getPrescripciones());
        model.setCurrentPrescripcion(new Prescripcion());
    }

    public void create(Recetas r) throws Exception {
        Service.instance().create(r);
        model.setCurrent(new Recetas(new ArrayList<Prescripcion>(),new Pacientes(),new Medico("","","","")));
        model.setListPrescripciones(List.of());
    }

    public void update(Prescripcion p) throws Exception {
        model.getCurrentPrescripcion().setCantidad(p.getCantidad());
        model.getCurrentPrescripcion().setDuracion(p.getDuracion());
        model.getCurrentPrescripcion().setIndicaciones(p.getIndicaciones());
    }

    public void searchPaciente(String nombre){
        Pacientes p = new Pacientes();
        p.setNombre(nombre);
        model.setListPacientes(Service.instance().search(p));
    }

    public void searchPacienteId(String id){
        Pacientes p = new Pacientes();
        p.setId(id);
        model.setListPacientes(Service.instance().searchId(p));
    }

    public void cargarPacientes() {
        model.setListPacientes(Service.instance().findAllPacientes());
    }

    public void readMedicamentos (String codigo) throws Exception  {
        Medicamentos e = new Medicamentos("","","");
        e.setCodigo(codigo);
        try {
            model.getCurrentPrescripcion().setMedicamento(Service.instance().read(e));
        } catch (Exception ex) {
            Medicamentos b = new Medicamentos("","","");
            b.setCodigo(codigo);
            model.getCurrentPrescripcion().setMedicamento(b);
            throw ex;
        }
    }

    public void searchMedicamento(String id){
        Medicamentos m = new Medicamentos("","","");
        m.setCodigo(id);
        model.setListMedicamentos(Service.instance().searchId(m));
    }

    public void searchMedicamentoNombre(String nombre){
        Medicamentos m = new Medicamentos("","","");
        m.setNombre(nombre);
        model.setListMedicamentos(Service.instance().search(m));
    }

    public void cargarMedicamentos() {
        model.setListMedicamentos(Service.instance().findAllMedicamentos());
    }

    public void addPrescripcion(Prescripcion p) {
        model.addPrescripcion(p);
        model.setCurrentPrescripcion(new Prescripcion());
    }

    public void removePrescripcion(String nombreMed) throws Exception {
        List<Prescripcion> prescripciones = model.getListPrescripciones();
        Prescripcion toRemove = null;
        for (Prescripcion p : prescripciones) {
            if (p.getMedicamento() != null && nombreMed.equals(p.getMedicamento().getNombre())) {
                toRemove = p;
                break;
            }
        }
        if (toRemove != null) {
            prescripciones.remove(toRemove);
            model.setListPrescripciones(prescripciones);
        } else {
            throw new Exception("No se encontró una prescripción con ese medicamento.");
        }
    }

    public  Prescripcion getPrescripcionByNombre(String nombreMed) {
        List<Prescripcion> prescripciones = model.getListPrescripciones();
        for (Prescripcion p : prescripciones) {
            if (p.getMedicamento() != null && nombreMed.equals(p.getMedicamento().getNombre())) {
                return p;
            }
        }
        return null;
    }

    public void updatePrescripcion(Prescripcion p) throws Exception {
        Prescripcion current = model.getCurrentPrescripcion();
        if (current != null) {
            current.setCantidad(p.getCantidad());
            current.setDuracion(p.getDuracion());
            current.setIndicaciones(p.getIndicaciones());
            current.setMedicamento(p.getMedicamento());
        } else {
            throw new Exception("No hay una prescripción seleccionada para actualizar.");
        }
    }

    public void clear() {
        model.setCurrent(new Recetas(null,null ,Sesion.getMedico()));
        model.setCurrentPrescripcion(null);
        model.setListPrescripciones(new ArrayList<>());
    }

    public void setMedico() {
        Medico m = Sesion.getMedico();
        model.getCurrent().setMedico(m);
    }

}

