package Hospital.Logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Worker {
    Server srv;
    Socket s;
    ObjectOutputStream os;
    ObjectInputStream is;
    Service service;
    String sid; // Session Id
    Socket as; // Asynchronous Socket
    ObjectOutputStream aos;
    ObjectInputStream ais;

    public Worker(Server srv, Socket s, ObjectOutputStream os, ObjectInputStream is, String sid, Service service) {
        this.srv = srv;
        this.s = s;
        this.os = os;
        this.is = is;
        this.service = service;
        this.sid = sid;
    }

    public void setAs(Socket as, ObjectOutputStream aos, ObjectInputStream ais) {
        this.as = as;
        this.aos = aos;
        this.ais = ais;
    }

    boolean continuar;

    public void start(){
        try{
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable() {
                public void run() {
                    listen();
                }
            });
            continuar = true;
            t.start();
        } catch (Exception ex) {
        }
    }

    public void stop(){
        continuar = false;
        System.out.println("Conexion cerrada...");
    }

    public void listen() {
        int method;
        while (continuar) {
            try {
                method = is.readInt();
                System.out.println("Operacion: " + method);
                switch (method) {
                    // =============== Administrador ===============
                    case Protocol.ADMINISTRADOR_READ:
                        try {
                            Administrador a = service.read((Administrador) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(a);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Médico ===============
                    case Protocol.MEDICO_CREATE:
                        try {
                            Medico m = (Medico) is.readObject();
                            service.create(m);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_READ:
                        try {
                            Medico m = service.read((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(m);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_UPDATE:
                        try {
                            service.update((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_DELETE:
                        try {
                            service.Delete((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_SEARCH:
                        try {
                            List<Medico> lm = service.search((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lm);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_SEARCH_ID:
                        try {
                            Medico m = service.searchId((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(m);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_SEARCH_ID_FILT:
                        try {
                            List<Medico> lm = service.searchIdFilt((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lm);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_FIND_ALL:
                        try {
                            List<Medico> lm = service.findAllMedicos();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lm);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Paciente ===============
                    case Protocol.PACIENTE_CREATE:
                        try {
                            Pacientes p = (Pacientes) is.readObject();
                            service.create(p);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_READ:
                        try {
                            Pacientes p = service.read((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(p);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_UPDATE:
                        try {
                            service.update((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_DELETE:
                        try {
                            service.Delete((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH:
                        try {
                            List<Pacientes> lp = service.search((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lp);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_ONE:
                        try {
                            Pacientes p = service.searchOne((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(p);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_ID:
                        try {
                            List<Pacientes> lp = service.searchId((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lp);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_FIND_ALL:
                        try {
                            List<Pacientes> lp = service.findAllPacientes();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lp);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Farmaceuta ===============
                    case Protocol.FARMACEUTA_CREATE:
                        try {
                            Farmaceuta f = (Farmaceuta) is.readObject();
                            service.create(f);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_READ:
                        try {
                            Farmaceuta f = service.read((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(f);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_UPDATE:
                        try {
                            service.update((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_DELETE:
                        try {
                            service.Delete((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH:
                        try {
                            List<Farmaceuta> lf = service.search((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lf);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH_ID:
                        try {
                            Farmaceuta f = service.searchId((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(f);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH_ID_FILT:
                        try {
                            List<Farmaceuta> lf = service.searchIdFilt((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lf);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_FIND_ALL:
                        try {
                            List<Farmaceuta> lf = service.findAllFarmaceutas();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lf);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Medicamento ===============
                    case Protocol.MEDICAMENTO_CREATE:
                        try {
                            Medicamentos med = (Medicamentos) is.readObject();
                            service.create(med);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_READ:
                        try {
                            Medicamentos med = service.read((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(med);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_UPDATE:
                        try {
                            service.update((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_DELETE:
                        try {
                            service.Delete((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH:
                        try {
                            List<Medicamentos> lmed = service.search((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lmed);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_ID:
                        try {
                            List<Medicamentos> lmed = service.searchId((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lmed);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_ONE:
                        try {
                            Medicamentos med = service.searchOne((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(med);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_FIND_ALL:
                        try {
                            List<Medicamentos> lmed = service.findAllMedicamentos();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lmed);  // Asegúrate de que lmed sea List<Medicamentos> y no List<Recetas>
                            os.flush();  // Añade flush aquí
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.flush();
                            ex.printStackTrace(); // Para debug
                        }
                        break;

                    // =============== Receta ===============
                    case Protocol.RECETA_CREATE:
                        try {
                            Recetas r = (Recetas) is.readObject();
                            service.create(r);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_READ:
                        try {
                            Recetas r = service.read((Recetas) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_FIND_ALL:
                        try {
                            List<Recetas> lr = service.findAllRecetas();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lr);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_GET_RECETAS:
                        try {
                            List<Recetas> lr = service.getRecetas();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lr);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_POR_PACIENTE:
                        try {
                            List<Recetas> lr = service.recetasPorPaciente((Recetas) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lr);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_AVANZAR_ESTADO:
                        try {
                            Recetas r = service.avanzarEstadoReceta((Recetas) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Empleado ===============
                    case Protocol.EMPLEADO_READ:
                        try {
                            Empleado e = service.read((Empleado) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(e);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.EMPLEADO_UPDATE:
                        try {
                            service.update((Empleado) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    case Protocol.LOGIN:
                        try {
                            Empleado user = (Empleado) is.readObject();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_user(this, user);
                        }catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    case Protocol.DISCONNECT:
                        stop();
                        srv.remove(this);
                        break;
                }

                os.flush();
            } catch (IOException e) {
                stop();
            }
        }
    }

    public synchronized void deliver_login(Empleado e){
        if (as != null){
            try {
                aos.writeInt(Protocol.DELIVER_EMPLOYEE);
                aos.writeObject(e);
                aos.flush();
            } catch (IOException ex) {
            }
        }
    }

}
