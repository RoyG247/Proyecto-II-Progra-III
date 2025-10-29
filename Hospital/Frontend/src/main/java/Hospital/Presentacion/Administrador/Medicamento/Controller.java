package Hospital.Presentacion.Administrador.Medicamento;

import Hospital.Application;
import Hospital.Logic.Farmaceuta;
import Hospital.Logic.Medicamentos;
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

    public void create(Medicamentos e) throws  Exception{
            switch (model.getMode()) {
                case Application.MODE_CREATE:
                    Service.instance().create(e);
                    break;
                case Application.MODE_EDIT:
                    Service.instance().update(e);
                    break;
            }
        model.setCurrent(new Medicamentos("","",""));
        model.setList(Service.instance().findAllMedicamentos());
    }
    public void edit(int row){
        Medicamentos e = model.getList().get(row);
        model.setCurrent(e);
        model.setMode(Application.MODE_EDIT);
    }

    public void switchMode(){
        model.setMode(Application.MODE_CREATE);
    }

    public void read(String codigo) throws Exception {
        Medicamentos e = new Medicamentos("","","");
        e.setCodigo(codigo);
        try {
            model.setCurrent(Service.instance().read(e));
        } catch (Exception ex) {
            Medicamentos b = new Medicamentos("","","");
            b.setCodigo(codigo);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Medicamentos("", "", ""));
    }

    public void deleteMedicamento(Medicamentos e)throws Exception{
        Medicamentos b = (Medicamentos) Service.instance().searchOne(e);
        Service.instance().Delete(b);
        model.setCurrent(new Medicamentos("","",""));
        model.setList(Service.instance().findAllMedicamentos());
    }

    public void searchMedicamento(String nombre) {
        Medicamentos m = new Medicamentos("","","");
        m.setNombre(nombre);
        model.setList(Service.instance().search(m));
    }

    public void searchMedicamentoCodigo(String codigo) {
        Medicamentos m = new Medicamentos("","","");
        m.setCodigo(codigo);
        model.setList(Service.instance().searchId(m));
    }

    public List<Medicamentos> searchOneMedicamento(String nombre) {
        Medicamentos m = new Medicamentos("","","");
        m.setNombre(nombre);
        return Service.instance().search(m);
    }

    public List<Medicamentos> getMedicamentos() {
        return Service.instance().findAllMedicamentos();
    }

    public void cargarMedicamentos() {
        model.setList(Service.instance().findAllMedicamentos());
    }
}
