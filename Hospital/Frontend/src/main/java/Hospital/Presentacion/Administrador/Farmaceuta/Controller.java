package Hospital.Presentacion.Administrador.Farmaceuta;

import Hospital.Application;
import Hospital.Logic.Farmaceuta;
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

    public void create(Farmaceuta e) throws  Exception{
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(e);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(e);
                break;
        }
        model.setCurrent(new Farmaceuta());
        model.setList(Service.instance().findAllFarmaceutas());
    }

    public void edit(int row) {
        Farmaceuta e = model.getList().get(row);
        model.setMode(Application.MODE_EDIT);
    }

    public void switchMode(){
        model.setMode(Application.MODE_CREATE);
    }

    public void read(String id) throws Exception {
        Farmaceuta e = new Farmaceuta();
        e.setId(id);
        try {
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            Farmaceuta b = new Farmaceuta();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Farmaceuta());
    }

    public void deleteFarmaceuta(Farmaceuta e)throws Exception{
        Farmaceuta b = (Farmaceuta) Service.instance().searchId(e);
        Service.instance().Delete(b);
        model.setCurrent(new Farmaceuta());
        model.setList(Service.instance().findAllFarmaceutas());
    }

    public void searchFarmaceuta(String nombre) {
        Farmaceuta m = new Farmaceuta();
        m.setNombre(nombre);
        model.setList(Service.instance().search(m));
    }

    public void searchFarmaceutaById(String id) {
        Farmaceuta m = new Farmaceuta();
        m.setId(id);
        model.setList(Service.instance().searchIdFilt(m));
    }

    public List<Farmaceuta> searchOneFarmaceuta(String nombre) {
        Farmaceuta m = new Farmaceuta();
        m.setNombre(nombre);
        return Service.instance().search(m);
    }

    public List<Farmaceuta> getFarmaceutas() {
        return Service.instance().findAllFarmaceutas();
    }

    public void cargarFarmaceutas() {
        model.setList(Service.instance().findAllFarmaceutas());
    }
}
