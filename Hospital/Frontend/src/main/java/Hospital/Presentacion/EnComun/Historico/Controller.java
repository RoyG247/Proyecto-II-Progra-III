package Hospital.Presentacion.EnComun.Historico;

import Hospital.Logic.*;
import Hospital.Presentacion.EnComun.Historico.Model;
import Hospital.Presentacion.EnComun.Historico.View;

import java.time.LocalDate;

public class Controller {
    View view;
    Model model;


    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        model.setList(Service.instance().getRecetas());
        view.setController(this);
        view.setModel(model);
    }

    public void read(String nombreMedico, String NombrePaciente, LocalDate fechaRetiro, int id) throws Exception {
        Recetas recetas = new Recetas(null,null,null);
        Pacientes p = new Pacientes();
        p.setNombre(NombrePaciente);
        Medico m = new Medico();
        m.setNombre(nombreMedico);
        recetas.setMedico(m);
        recetas.setPaciente(p);
        recetas.setFechaRetiro(fechaRetiro);
        recetas.setId(id);
        try {
            Recetas r =Service.instance().read(recetas);
            model.setCurrent(r);
        } catch (Exception ex) {
            Recetas r = new Recetas(null,null,null);
            r.setMedico(m);
            r.setPaciente(p);
            r.setFechaRetiro(fechaRetiro);
            model.setCurrent(r);
            throw ex;
        }
    }

    public void cargarHistorico() {
        model.setList(Service.instance().findAllRecetas());
    }

}
