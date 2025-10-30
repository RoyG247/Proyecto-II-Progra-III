package Hospital.Presentacion.login;

import Hospital.Logic.*;
import Hospital.Presentacion.Sesion;

public class Controller {
    Login view;
    Model model;

   public Controller(Login view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void login(Empleado usuario) throws Exception {
        Empleado logged = Service.instance().read(usuario);
        switch (logged.getRol()){
            case "ADM":
                Administrador administrador = new Administrador();
                administrador.setId(logged.getId());
                administrador = Service.instance().read(administrador);
                logged.setNombre(administrador.getNombre());
                break;
            case "MED":
                Medico medico = new Medico();
                medico.setId(logged.getId());
                medico = Service.instance().read(medico);
                logged = medico;
                break;
            case "FAR":
                Farmaceuta farmaceuta = new Farmaceuta();
                farmaceuta.setId(logged.getId());
                farmaceuta= Service.instance().read(farmaceuta);
                logged.setNombre(farmaceuta.getNombre());
                break;
            default:
                throw new Exception("Rol no reconocido");
        }
        if (!logged.getClave().equals(usuario.getClave()) || !logged.getId().equals(usuario.getId())) {
            throw new Exception("Clave o usuario incorrectos");
        }
        try {
            Service.instance().send_user(logged);
        } catch (Exception e) {
            throw new Exception("No se pudo establecer conexion con el servidor de mensajes");
        }
        Sesion.setUsuario(logged);
    }

    public void cambiarClave( String nueva, String confimar ) throws Exception {
      try {
          if (!nueva.equals(confimar)) {
              throw new Exception("Las claves no coinciden");
          }
          if (nueva.isEmpty()) {
              throw new Exception("La clave no puede estar vacia");
          }
          Empleado u = Sesion.getUsuario();
          u.setClave(nueva);
          if(u instanceof Medico){
              Medico m = (Medico)u;
              Service.instance().update(m);
          } else if (u instanceof Farmaceuta){
              Farmaceuta f = (Farmaceuta)u;
              f.setClave(nueva);
              Service.instance().update(f);
          } else if (u instanceof Administrador){
              Administrador ad = (Administrador)u;
              ad.setClave(nueva);
              Service.instance().update(ad);
          } else {
              throw new Exception("Tipo de usuario no reconocido");
          }
          Service.instance().update(u);
          Sesion.setUsuario(u);

      } catch (Exception e) {
          throw new Exception(e.getMessage());
      }
    }

}