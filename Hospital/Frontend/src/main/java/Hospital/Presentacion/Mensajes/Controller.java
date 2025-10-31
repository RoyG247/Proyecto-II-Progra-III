package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Empleado;
import Hospital.Logic.Service;
import Hospital.Presentacion.ThreadListener;
import Hospital.Presentacion.Sesion;

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

    public void stop(){
        socketListener.stop();
    }


}
