package Hospital.Presentacion.Administrador.Medicos;

import Hospital.Application;
import Hospital.Logic.Medico;
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

    public void create(Medico e) throws  Exception{
            switch (model.getMode()) {
                case Application.MODE_CREATE:
                    Service.instance().create(e);
                    break;
                case Application.MODE_EDIT:
                    Service.instance().update(e);
                    break;
            }
        model.setCurrent(new Medico());
        model.setList(Service.instance().findAllMedicos());
    }

    public void edit(int row){
        Medico e = model.getList().get(row);
        model.setCurrent(e);
        model.setMode(Application.MODE_EDIT);
    }

    public void switchMode(){
        model.setMode(Application.MODE_CREATE);
    }

    public void read(String id) throws Exception {
        Medico e = new Medico();
        e.setId(id);
        try {
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            Medico b = new Medico();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Medico());
    }

    public void deleteMedico(Medico e)throws Exception{
        Medico b = (Medico) Service.instance().searchId(e);
        Service.instance().Delete(b);
        model.setCurrent(new Medico());
        model.setList(Service.instance().findAllMedicos());
    }

    public void searchMedico(String nombre) {
        Medico m = new Medico();
        m.setNombre(nombre);
        model.setList(Service.instance().search(m));
    }

    public void searchMedicoId(String id) {
        Medico m = new Medico();
        m.setId(id);
        model.setList(Service.instance().searchIdFilt(m));
    }

    public List<Medico> searchOneMedico(String nombre) {
        Medico m = new Medico();
        m.setNombre(nombre);
        return Service.instance().search(m);
    }

    public List<Medico> getMedicos() {
        Medico m = new Medico();
        m.setNombre("");
        return Service.instance().search(m);
    }

    public void cargarMedicos() {
        Medico m = new Medico();
        m.setNombre("");
        model.setList(Service.instance().search(m));
    }
}
