package Hospital.Logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    ServerSocket srv;
    List<Worker> workers;
    List<Empleado> empleados;
    Service service;

    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<>());
            empleados = Collections.synchronizedList(new ArrayList<>());
            service = Service.instance();
            System.out.println("Servidor iniciado...");
        } catch (IOException ex) {
            System.exit(-1);
        }
    }

    public void run() {
        boolean continuar = true;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        Socket socket;
        Worker worker;

        while (continuar) {
            try {
                socket = srv.accept();
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());

                int method = ois.readInt();
                switch (method) {
                    case Protocol.SYNC:
                        String sid = "Usr-" + (workers.size() + 1);
                        oos.writeObject(sid);
                        oos.flush();
                        worker = new Worker(this, socket, oos, ois, sid, service);
                        workers.add(worker);
                        worker.start();
                        break;
                    case Protocol.ASYNC:
                        sid = (String) ois.readObject();
                        for (Worker w : workers) {
                            if (w.sid.equals(sid)) {
                                w.setAs(socket, oos, ois);
                                break;
                            }
                        }
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deliver_users(Worker from, Empleado e) {
        empleados.add(e);
        from.setEmpleadoAsociado(e);

        for (Worker w : workers) {
            if (w != from) {
                Empleado nuevoUsuario = new Empleado();
                nuevoUsuario.setId(e.getId());
                nuevoUsuario.setNombre(e.getNombre());
                nuevoUsuario.setRol(e.getRol());
                w.deliver_login(nuevoUsuario);
            }
        }

        if (from != null) {
            List<Empleado> usuariosConectados = filtrarEmpleadosExternos(e);
            from.deliver_users(usuariosConectados);
        }
    }


    public void remove(Worker worker, Empleado empleado) {
        workers.remove(worker);
        empleados.remove(empleado);

        for (Worker w : workers) {
            Empleado empleadoActual = w.getEmpleadoAsociado();
            List<Empleado> usuariosConectados = filtrarEmpleadosExternos(empleadoActual);
            w.deliver_users(usuariosConectados);
        }
    }

    public List<Empleado> filtrarEmpleadosExternos(Empleado empleadoActual) {
        List<Empleado> usuariosConectados = new ArrayList<>();

        if (empleadoActual == null) {
            return new ArrayList<>(empleados);
        }

        for (Empleado emp : empleados) {
            if (!emp.getId().equals(empleadoActual.getId())) {
                usuariosConectados.add(emp);
            }
        }

        return usuariosConectados;
    }

    public void deliver_message(Worker from, Mensaje message) {
        for (Worker w: workers){
            if(w.getEmpleadoAsociado().getId().equals(message.getIdReceptor())){
                w.deliver_message(message);
            }
        }
    }

}