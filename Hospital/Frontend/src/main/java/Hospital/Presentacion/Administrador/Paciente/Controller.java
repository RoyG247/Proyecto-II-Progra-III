package Hospital.Presentacion.Administrador.Paciente;

import Hospital.Application;
import Hospital.Logic.Pacientes;
import Hospital.Logic.Service;

import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init();
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void read(String id) throws Exception {
        Pacientes e = new Pacientes();
        e.setId(id);
        try {
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            Pacientes b = new Pacientes();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void create(Pacientes p) throws Exception {
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(p);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(p);
                break;
        }
        model.setCurrent(new Pacientes());
        model.setList(Service.instance().findAllPacientes());
    }

    public void edit(int row){
        Pacientes e = model.getList().get(row);
        model.setCurrent(e);
        model.setMode(Application.MODE_EDIT);
    }

    public void switchMode(){
      model.setMode(Application.MODE_CREATE);
    }


    public void clear(){ model.setCurrent(new Pacientes());}

    public void deletePaciente(Pacientes e) throws Exception {
        Pacientes b = (Pacientes) Service.instance().searchOne(e);
        Service.instance().Delete(b);
        model.setCurrent(new Pacientes());
        model.setList(Service.instance().findAllPacientes());
    }

    public List<Pacientes> searchOnePaciente(String nombre){
        Pacientes p = new Pacientes();
        p.setNombre(nombre);
        return Service.instance().search(p);
    }

    public void searchPaciente(String nombre){
        Pacientes p = new Pacientes();
        p.setNombre(nombre);
        model.setList(Service.instance().search(p));
    }

    public void searchPacienteId(String id){
        Pacientes p = new Pacientes();
        p.setId(id);
        model.setList(Service.instance().searchId(p));
    }

    public List<Pacientes> getPacientes() {
        Pacientes p = new Pacientes();
        p.setNombre("");
        return Service.instance().search(p);
    }

    public void cargarPacientes(){
        Pacientes p = new Pacientes();
        p.setNombre("");
        model.setList(Service.instance().search(p));
    }


}
