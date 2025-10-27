package Hospital.Presentacion.Despacho;

import Hospital.Logic.Recetas;
import Hospital.Logic.Service;

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

    public void filtrarRecetas(Recetas r) {
        if (r.getPaciente().getId() == null || r.getPaciente().getId().isEmpty()) {
            model.setRecetas(Service.instance().findAllRecetas());
        } else {
            model.setRecetas(Service.instance().recetasPorPaciente(r));
        }
    }

    public void avanzarEstado(Recetas r) throws Exception {
        Service.instance().avanzarEstadoReceta(r);
        model.setRecetas(Service.instance().recetasPorPaciente(r));
    }
}
