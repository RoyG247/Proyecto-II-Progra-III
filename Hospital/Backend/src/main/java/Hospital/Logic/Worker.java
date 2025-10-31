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
                System.out.println("========================================");
                System.out.println(">>> PETICION RECIBIDA: Código = " + method);

                switch (method) {
                    // =============== Administrador ===============
                    case Protocol.ADMINISTRADOR_READ:
                        System.out.println(">>> Tipo: ADMINISTRADOR_READ");
                        try {
                            Administrador a = service.read((Administrador) is.readObject());
                            System.out.println(">>> RESPUESTA: Administrador = " + (a != null ? a.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(a);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en ADMINISTRADOR_READ: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Médico ===============
                    case Protocol.MEDICO_CREATE:
                        System.out.println(">>> Tipo: MEDICO_CREATE");
                        try {
                            Medico m = (Medico) is.readObject();
                            System.out.println(">>> Objeto recibido: " + m.getClass().getName());
                            service.create(m);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_CREATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_READ:
                        System.out.println(">>> Tipo: MEDICO_READ");
                        try {
                            Medico m = service.read((Medico) is.readObject());
                            System.out.println(">>> RESPUESTA: Medico = " + (m != null ? m.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(m);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_READ: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_UPDATE:
                        System.out.println(">>> Tipo: MEDICO_UPDATE");
                        try {
                            service.update((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_UPDATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_DELETE:
                        System.out.println(">>> Tipo: MEDICO_DELETE");
                        try {
                            service.Delete((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_DELETE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_SEARCH:
                        System.out.println(">>> Tipo: MEDICO_SEARCH");
                        try {
                            List<Medico> lm = service.search((Medico) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lm != null ? lm.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lm != null ? lm.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lm);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_SEARCH: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_SEARCH_ID:
                        System.out.println(">>> Tipo: MEDICO_SEARCH_ID");
                        try {
                            Medico m = service.searchId((Medico) is.readObject());
                            System.out.println(">>> RESPUESTA: Medico = " + (m != null ? m.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(m);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_SEARCH_ID: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_SEARCH_ID_FILT:
                        System.out.println(">>> Tipo: MEDICO_SEARCH_ID_FILT");
                        try {
                            List<Medico> lm = service.searchIdFilt((Medico) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lm != null ? lm.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lm != null ? lm.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lm);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_SEARCH_ID_FILT: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_FIND_ALL:
                        System.out.println(">>> Tipo: MEDICO_FIND_ALL");
                        try {
                            List<Medico> lm = service.findAllMedicos();
                            System.out.println(">>> RESPUESTA: List tipo = " + (lm != null ? lm.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lm != null ? lm.size() : "null"));
                            if (lm != null && !lm.isEmpty()) {
                                System.out.println(">>> RESPUESTA: Primer elemento tipo = " + lm.get(0).getClass().getName());
                            }
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lm);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICO_FIND_ALL: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Paciente ===============
                    case Protocol.PACIENTE_CREATE:
                        System.out.println(">>> Tipo: PACIENTE_CREATE");
                        try {
                            Pacientes p = (Pacientes) is.readObject();
                            System.out.println(">>> Objeto recibido: " + p.getClass().getName());
                            service.create(p);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_CREATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_READ:
                        System.out.println(">>> Tipo: PACIENTE_READ");
                        try {
                            Pacientes p = service.read((Pacientes) is.readObject());
                            System.out.println(">>> RESPUESTA: Paciente = " + (p != null ? p.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(p);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_READ: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_UPDATE:
                        System.out.println(">>> Tipo: PACIENTE_UPDATE");
                        try {
                            service.update((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_UPDATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_DELETE:
                        System.out.println(">>> Tipo: PACIENTE_DELETE");
                        try {
                            service.Delete((Pacientes) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_DELETE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH:
                        System.out.println(">>> Tipo: PACIENTE_SEARCH");
                        try {
                            List<Pacientes> lp = service.search((Pacientes) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lp != null ? lp.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lp != null ? lp.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lp);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_SEARCH: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_ONE:
                        System.out.println(">>> Tipo: PACIENTE_SEARCH_ONE");
                        try {
                            Pacientes p = service.searchOne((Pacientes) is.readObject());
                            System.out.println(">>> RESPUESTA: Paciente = " + (p != null ? p.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(p);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_SEARCH_ONE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_ID:
                        System.out.println(">>> Tipo: PACIENTE_SEARCH_ID");
                        try {
                            List<Pacientes> lp = service.searchId((Pacientes) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lp != null ? lp.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lp != null ? lp.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lp);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_SEARCH_ID: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_FIND_ALL:
                        System.out.println(">>> Tipo: PACIENTE_FIND_ALL");
                        try {
                            List<Pacientes> lp = service.findAllPacientes();
                            System.out.println(">>> RESPUESTA: List tipo = " + (lp != null ? lp.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lp != null ? lp.size() : "null"));
                            if (lp != null && !lp.isEmpty()) {
                                System.out.println(">>> RESPUESTA: Primer elemento tipo = " + lp.get(0).getClass().getName());
                            }
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lp);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en PACIENTE_FIND_ALL: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Farmaceuta ===============
                    case Protocol.FARMACEUTA_CREATE:
                        System.out.println(">>> Tipo: FARMACEUTA_CREATE");
                        try {
                            Farmaceuta f = (Farmaceuta) is.readObject();
                            System.out.println(">>> Objeto recibido: " + f.getClass().getName());
                            service.create(f);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_CREATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_READ:
                        System.out.println(">>> Tipo: FARMACEUTA_READ");
                        try {
                            Farmaceuta f = service.read((Farmaceuta) is.readObject());
                            System.out.println(">>> RESPUESTA: Farmaceuta = " + (f != null ? f.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(f);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_READ: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_UPDATE:
                        System.out.println(">>> Tipo: FARMACEUTA_UPDATE");
                        try {
                            service.update((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_UPDATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_DELETE:
                        System.out.println(">>> Tipo: FARMACEUTA_DELETE");
                        try {
                            service.Delete((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_DELETE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH:
                        System.out.println(">>> Tipo: FARMACEUTA_SEARCH");
                        try {
                            List<Farmaceuta> lf = service.search((Farmaceuta) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lf != null ? lf.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lf != null ? lf.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lf);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_SEARCH: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH_ID:
                        System.out.println(">>> Tipo: FARMACEUTA_SEARCH_ID");
                        try {
                            Farmaceuta f = service.searchId((Farmaceuta) is.readObject());
                            System.out.println(">>> RESPUESTA: Farmaceuta = " + (f != null ? f.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(f);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_SEARCH_ID: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH_ID_FILT:
                        System.out.println(">>> Tipo: FARMACEUTA_SEARCH_ID_FILT");
                        try {
                            List<Farmaceuta> lf = service.searchIdFilt((Farmaceuta) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lf != null ? lf.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lf != null ? lf.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lf);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_SEARCH_ID_FILT: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_FIND_ALL:
                        System.out.println(">>> Tipo: FARMACEUTA_FIND_ALL");
                        try {
                            List<Farmaceuta> lf = service.findAllFarmaceutas();
                            System.out.println(">>> RESPUESTA: List tipo = " + (lf != null ? lf.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lf != null ? lf.size() : "null"));
                            if (lf != null && !lf.isEmpty()) {
                                System.out.println(">>> RESPUESTA: Primer elemento tipo = " + lf.get(0).getClass().getName());
                            }
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lf);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en FARMACEUTA_FIND_ALL: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Medicamento ===============
                    case Protocol.MEDICAMENTO_CREATE:
                        System.out.println(">>> Tipo: MEDICAMENTO_CREATE");
                        try {
                            Medicamentos med = (Medicamentos) is.readObject();
                            System.out.println(">>> Objeto recibido: " + med.getClass().getName());
                            service.create(med);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_CREATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_READ:
                        System.out.println(">>> Tipo: MEDICAMENTO_READ");
                        try {
                            Medicamentos med = service.read((Medicamentos) is.readObject());
                            System.out.println(">>> RESPUESTA: Medicamento = " + (med != null ? med.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(med);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_READ: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_UPDATE:
                        System.out.println(">>> Tipo: MEDICAMENTO_UPDATE");
                        try {
                            service.update((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_UPDATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_DELETE:
                        System.out.println(">>> Tipo: MEDICAMENTO_DELETE");
                        try {
                            service.Delete((Medicamentos) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_DELETE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH:
                        System.out.println(">>> Tipo: MEDICAMENTO_SEARCH");
                        try {
                            List<Medicamentos> lmed = service.search((Medicamentos) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lmed != null ? lmed.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lmed != null ? lmed.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lmed);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_SEARCH: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_ID:
                        System.out.println(">>> Tipo: MEDICAMENTO_SEARCH_ID");
                        try {
                            List<Medicamentos> lmed = service.searchId((Medicamentos) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lmed != null ? lmed.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lmed != null ? lmed.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lmed);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_SEARCH_ID: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_ONE:
                        System.out.println(">>> Tipo: MEDICAMENTO_SEARCH_ONE");
                        try {
                            Medicamentos med = service.searchOne((Medicamentos) is.readObject());
                            System.out.println(">>> RESPUESTA: Medicamento = " + (med != null ? med.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(med);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_SEARCH_ONE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_FIND_ALL:
                        System.out.println(">>> Tipo: MEDICAMENTO_FIND_ALL");
                        try {
                            List<Medicamentos> lmed = service.findAllMedicamentos();
                            System.out.println(">>> RESPUESTA: List tipo = " + (lmed != null ? lmed.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lmed != null ? lmed.size() : "null"));
                            if (lmed != null && !lmed.isEmpty()) {
                                System.out.println(">>> RESPUESTA: Primer elemento tipo = " + lmed.get(0).getClass().getName());
                            }
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lmed);
                            os.flush();
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en MEDICAMENTO_FIND_ALL: " + ex.getMessage());
                            ex.printStackTrace();
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.flush();
                        }
                        break;

                    // =============== Receta ===============
                    case Protocol.RECETA_CREATE:
                        System.out.println(">>> Tipo: RECETA_CREATE");
                        try {
                            Recetas r = (Recetas) is.readObject();
                            System.out.println(">>> Objeto recibido: " + r.getClass().getName());
                            service.create(r);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en RECETA_CREATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_READ:
                        System.out.println(">>> Tipo: RECETA_READ");
                        try {
                            Recetas r = service.read((Recetas) is.readObject());
                            System.out.println(">>> RESPUESTA: Receta = " + (r != null ? r.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en RECETA_READ: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_FIND_ALL:
                        System.out.println(">>> Tipo: RECETA_FIND_ALL");
                        try {
                            List<Recetas> lr = service.findAllRecetas();
                            System.out.println(">>> RESPUESTA: List tipo = " + (lr != null ? lr.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lr != null ? lr.size() : "null"));
                            if (lr != null && !lr.isEmpty()) {
                                System.out.println(">>> RESPUESTA: Primer elemento tipo = " + lr.get(0).getClass().getName());
                            }
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lr);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en RECETA_FIND_ALL: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_GET_RECETAS:
                        System.out.println(">>> Tipo: RECETA_GET_RECETAS");
                        try {
                            List<Recetas> lr = service.getRecetas();
                            System.out.println(">>> RESPUESTA: List tipo = " + (lr != null ? lr.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lr != null ? lr.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lr);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en RECETA_GET_RECETAS: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_POR_PACIENTE:
                        System.out.println(">>> Tipo: RECETA_POR_PACIENTE");
                        try {
                            List<Recetas> lr = service.recetasPorPaciente((Recetas) is.readObject());
                            System.out.println(">>> RESPUESTA: List tipo = " + (lr != null ? lr.getClass().getName() : "null"));
                            System.out.println(">>> RESPUESTA: List size = " + (lr != null ? lr.size() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lr);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en RECETA_POR_PACIENTE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_AVANZAR_ESTADO:
                        System.out.println(">>> Tipo: RECETA_AVANZAR_ESTADO");
                        try {
                            Recetas r = service.avanzarEstadoReceta((Recetas) is.readObject());
                            System.out.println(">>> RESPUESTA: Receta = " + (r != null ? r.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en RECETA_AVANZAR_ESTADO: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // =============== Empleado ===============
                    case Protocol.EMPLEADO_READ:
                        System.out.println(">>> Tipo: EMPLEADO_READ");
                        try {
                            Empleado e = service.read((Empleado) is.readObject());
                            System.out.println(">>> RESPUESTA: Empleado = " + (e != null ? e.getClass().getName() : "null"));
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(e);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en EMPLEADO_READ: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.EMPLEADO_UPDATE:
                        System.out.println(">>> Tipo: EMPLEADO_UPDATE");
                        try {
                            service.update((Empleado) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            System.out.println(">>> ERROR en EMPLEADO_UPDATE: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    case Protocol.LOGIN:
                        System.out.println(">>> Tipo: LOGIN");
                        try {
                            Empleado user = (Empleado) is.readObject();
                            System.out.println(">>> Usuario recibido: " + user.getClass().getName());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_user(this, user);
                        }catch (Exception ex) {
                            System.out.println(">>> ERROR en LOGIN: " + ex.getMessage());
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    case Protocol.DISCONNECT:
                        System.out.println(">>> Tipo: DISCONNECT");
                        stop();
                        srv.remove(this);
                        break;

                    default:
                        System.out.println(">>> ADVERTENCIA: Código de operación desconocido: " + method);
                        break;
                }

                os.flush();
                System.out.println(">>> FIN DE PETICION");
                System.out.println("========================================");
            } catch (IOException e) {
                System.out.println(">>> ERROR de comunicación: " + e.getMessage());
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
