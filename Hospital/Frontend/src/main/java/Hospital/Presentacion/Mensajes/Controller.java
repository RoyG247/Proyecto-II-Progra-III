package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Empleado;
import Hospital.Logic.Mensaje;
import Hospital.Logic.Service;
import Hospital.Presentacion.Sesion;
import Hospital.Presentacion.ThreadListener;

import java.util.ArrayList;
import java.util.List;

public class Controller implements ThreadListener {
    private View view;
    private Model model;
    private SocketListener socketListener;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        // Iniciar el listener cuando se crea el controller
        iniciarListener();
    }

    private void iniciarListener() {
        try {
            String sid = Service.instance().getSid();
            socketListener = new SocketListener(this, sid);
            socketListener.start();

            // Establecer el usuario actual
            model.setCurrentUser(Sesion.getUsuario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarEmpleados() {
        try {
            List<Empleado> empleados = Service.instance().findAllOnlineUsers(Sesion.getUsuario());
            model.setUsers(new ArrayList<>(empleados));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send_message(Mensaje msg) throws Exception {
        msg.setIdEmisor(Sesion.getUsuario().getId());
        Service.instance().send_message(msg);
    }

    public void limpiar(){
        model.setCurrentMessage(null);
    }

    public String getUserNameById(String id) {
        for (Empleado user : model.getUsers()) {
            if (user.getId().equals(id)) {
                return user.getNombre();
            }
        }
        return "Desconocido";
    }



    // Implementación de ThreadListener
    @Override
    public void deliver_user(Empleado user) {
        model.addUser(user);
    }

    @Override
    public void deliver_users(List<Empleado> users) {
        model.setUsers(new ArrayList<>(users));
    }

    @Override
    public void deliver_message(Mensaje message) {
        model.setCurrentMessage(message);
    }

    public void stop() {
        if (socketListener != null) {
            socketListener.stop();
        }
    }
}