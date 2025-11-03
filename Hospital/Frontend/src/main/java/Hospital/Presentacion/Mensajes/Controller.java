package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Empleado;
import Hospital.Logic.Service;
import Hospital.Presentacion.ThreadListener;

import java.util.ArrayList;
import java.util.List;

public class Controller implements ThreadListener {

    View view;
    Model model;

    SocketListener socketListener;

    public Controller(View viewMensajes, Model modelMensajes) {
        this.view = viewMensajes;
        this.model = modelMensajes;
        view.setController(this);
        view.setModel(model);

        try {
            socketListener = new SocketListener(this, ((Service)Service.instance()).getSid());
            socketListener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void deliver_user(Empleado user) {
        try {
            model.setCurrentUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliver_users(List<Empleado> users) {
        try {
            model.setUsers((ArrayList<Empleado>) users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliver_message(String message) {
        try {
            model.setMessage(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String message(){
        return model.getMessage();
    }
    public void send_message(int e, String message) {
        try {
            Service.instance().send_message(e, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void stop(){
        socketListener.stop();
    }

    public void cargarEmpleados(){
        model.setUsers(model.getUsers());
    }
}
