package Hospital.Logic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Service {
    private static Service theInstance;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private final Object socketLock = new Object();

    String sid;

    private Service() {
        try {
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.writeInt(Protocol.SYNC);
            out.flush();
            sid = (String) in.readObject();
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    public String getSid() {
        return sid;
    }


    private Object sendRequestForObject(int code, Object payload){
        try{
            synchronized(socketLock){
                System.out.println("FRONTEND - Enviando codigo solicitado: " + code + "payLoadClass: " + ((payload == null) ? "null" : payload.getClass().getName()));
                out.writeInt(code);
                if (payload != null){
                    out.writeObject(payload);
                }
                out.flush();

                int respCode = in.readInt();
                System.out.println("FRONTEND - Codigo de respuesta recibido: " + respCode + "por: " + code);
                if(respCode == Protocol.ERROR_NO_ERROR){
                    Object obj = in.readObject();
                    System.out.println("FRONTEND - Objeto recibido de clase: " + (obj == null ? "null" : obj.getClass().getName()));
                    if (obj instanceof java.util.List){
                        java.util.List list = (java.util.List) obj;
                        System.out.println("FRONTEND - Tamaño de la lista recibida: " + list.size());
                        if (!list.isEmpty() && list.get(0) != null){
                            System.out.println("FRONTEND - Clase del primer elemento: " + list.get(0).getClass().getName());
                        }
                    }
                    return obj;
                } else {
                    System.err.println("FRONTEND - server returned error code = " + respCode + " for request code: " + code);
                    return null;
                }
            }
        } catch (IOException e) {
            System.err.println("FRONTEND - server returned error code = ");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRequestNoObject(int code, Object payload) throws Exception {
        try {
            synchronized (socketLock) {
                System.out.println("FRONTEND - Enviando codigo solicitado: " + code + "payloadClass: " +(payload == null ? "null" : payload.getClass().getName()));
                out.writeInt(code);
                if(payload != null){
                    out.writeObject(payload);
                }
                out.flush();
                int respCode = in.readInt();
                System.out.println("FRONTEND - Codigo de respuesta recibido: " + respCode + "por: " + code);
                if (respCode != Protocol.ERROR_NO_ERROR) {
                    throw new Exception("ERROR DE SERVIDOR, CODIGO: " + respCode + "solicitado: " + code);
                }
            }

        } catch (IOException ioEx) {
            System.err.println("FRONTEND - IO Exception en sendRequestNoObject: " + ioEx.getMessage());
            ioEx.printStackTrace();
            throw ioEx;
        }

    }


    // =============== Administrador ===============
    public Administrador read(Administrador a) throws Exception {
      Object obj = sendRequestForObject(Protocol.ADMINISTRADOR_READ, a);
      if(obj == null) throw new Exception("ADMINISTRADOR NO EXISTE");
      try {
            return (Administrador) obj;
      } catch (ClassCastException ex) {
          throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Administrador" + obj.getClass().getName(), ex);

      }
    }

    // =============== Médico ===============
    public void create(Medico m) throws Exception {
        sendRequestNoObject(Protocol.MEDICO_CREATE, m);
    }

    public Medico read(Medico m) throws Exception {
        Object obj = sendRequestForObject(Protocol.MEDICO_READ, m);
        if (obj == null) throw new Exception("MÉDICO NO EXISTE");
        try {
            return (Medico) obj;
        } catch (ClassCastException ex) {
            throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Medico: " + obj.getClass().getName(), ex);
        }
    }

    public void update(Medico m) throws Exception {
        sendRequestNoObject(Protocol.MEDICO_UPDATE, m);
    }

    public void Delete(Medico m) throws Exception {
        sendRequestNoObject(Protocol.MEDICO_DELETE, m);
    }

    public List<Medico> search(Medico m) {
        Object obj = sendRequestForObject(Protocol.MEDICO_SEARCH, m);
        if (obj == null) return List.of();
        try {
            return (List<Medico>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Medico>: " + obj.getClass().getName(), ex);
        }
    }

    public Medico searchId(Medico m) {
        Object obj = sendRequestForObject(Protocol.MEDICO_SEARCH_ID, m);
        if (obj == null) return null;
        try {
            return (Medico) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo Medico: " + obj.getClass().getName(), ex);
        }
    }

    public List<Medico> searchIdFilt(Medico m) {
        Object obj = sendRequestForObject(Protocol.MEDICO_SEARCH_ID_FILT, m);
        if (obj == null) return List.of();
        try {
            return (List<Medico>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Medico>: " + obj.getClass().getName(), ex);
        }
    }

    public List<Medico> findAllMedicos() {
        Object obj = sendRequestForObject(Protocol.MEDICO_FIND_ALL, null);
        if (obj == null) return List.of();
        try {
            return (List<Medico>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Medico>: " + obj.getClass().getName(), ex);
        }
    }

    // =============== Paciente ===============
    public void create(Pacientes p) throws Exception {
        sendRequestNoObject(Protocol.PACIENTE_CREATE, p);
    }

    public Pacientes read(Pacientes p) throws Exception {
        Object obj = sendRequestForObject(Protocol.PACIENTE_READ, p);
        if (obj == null) throw new Exception("PACIENTE NO EXISTE");
        try {
            return (Pacientes) obj;
        } catch (ClassCastException ex) {
            throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Pacientes: " + obj.getClass().getName(), ex);
        }
    }

    public void update(Pacientes p) throws Exception {
        sendRequestNoObject(Protocol.PACIENTE_UPDATE, p);
    }

    public void Delete(Pacientes p) throws Exception {
        sendRequestNoObject(Protocol.PACIENTE_DELETE, p);
    }

    public List<Pacientes> search(Pacientes p) {
        Object obj = sendRequestForObject(Protocol.PACIENTE_SEARCH, p);
        if (obj == null) return List.of();
        try {
            return (List<Pacientes>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Pacientes>: " + obj.getClass().getName(), ex);
        }
    }

    public Pacientes searchOne(Pacientes p) {
        Object obj = sendRequestForObject(Protocol.PACIENTE_SEARCH_ONE, p);
        if (obj == null) return null;
        try {
            return (Pacientes) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo Pacientes: " + obj.getClass().getName(), ex);
        }
    }

    public List<Pacientes> searchId(Pacientes p) {
        Object obj = sendRequestForObject(Protocol.PACIENTE_SEARCH_ID, p);
        if (obj == null) return List.of();
        try {
            return (List<Pacientes>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Pacientes>: " + obj.getClass().getName(), ex);
        }
    }

    public List<Pacientes> findAllPacientes() {
        Object obj = sendRequestForObject(Protocol.PACIENTE_FIND_ALL, null);
        if (obj == null) return List.of();
        try {
            return (List<Pacientes>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Pacientes>: " + obj.getClass().getName(), ex);
        }
    }

    // =============== Farmaceuta ===============
    public void create(Farmaceuta f) throws Exception {
        sendRequestNoObject(Protocol.FARMACEUTA_CREATE, f);
    }

    public Farmaceuta read(Farmaceuta f) throws Exception {
        Object obj = sendRequestForObject(Protocol.FARMACEUTA_READ, f);
        if (obj == null) throw new Exception("FARMACEUTA NO EXISTE");
        try {
            return (Farmaceuta) obj;
        } catch (ClassCastException ex) {
            throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Farmaceuta: " + obj.getClass().getName(), ex);
        }
    }

    public void update(Farmaceuta f) throws Exception {
        sendRequestNoObject(Protocol.FARMACEUTA_UPDATE, f);
    }

    public void Delete(Farmaceuta f) throws Exception {
        sendRequestNoObject(Protocol.FARMACEUTA_DELETE, f);
    }

    public List<Farmaceuta> search(Farmaceuta f) {
        Object obj = sendRequestForObject(Protocol.FARMACEUTA_SEARCH, f);
        if (obj == null) return List.of();
        try {
            return (List<Farmaceuta>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Farmaceuta>: " + obj.getClass().getName(), ex);
        }
    }

    public Farmaceuta searchId(Farmaceuta f) {
        Object obj = sendRequestForObject(Protocol.FARMACEUTA_SEARCH_ID, f);
        if (obj == null) return null;
        try {
            return (Farmaceuta) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo Farmaceuta: " + obj.getClass().getName(), ex);
        }
    }

    public List<Farmaceuta> searchIdFilt(Farmaceuta f) {
        Object obj = sendRequestForObject(Protocol.FARMACEUTA_SEARCH_ID_FILT, f);
        if (obj == null) return List.of();
        try {
            return (List<Farmaceuta>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Farmaceuta>: " + obj.getClass().getName(), ex);
        }
    }

    public List<Farmaceuta> findAllFarmaceutas() {
        Object obj = sendRequestForObject(Protocol.FARMACEUTA_FIND_ALL, null);
        if (obj == null) return List.of();
        try {
            return (List<Farmaceuta>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Farmaceuta>: " + obj.getClass().getName(), ex);
        }
    }

    // =============== Medicamento ===============
    public void create(Medicamentos m) throws Exception {
        sendRequestNoObject(Protocol.MEDICAMENTO_CREATE, m);
    }

    public Medicamentos read(Medicamentos m) throws Exception {
        Object obj = sendRequestForObject(Protocol.MEDICAMENTO_READ, m);
        if (obj == null) throw new Exception("MEDICAMENTO NO EXISTE");
        try {
            return (Medicamentos) obj;
        } catch (ClassCastException ex) {
            throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Medicamentos: " + obj.getClass().getName(), ex);
        }
    }

    public void update(Medicamentos m) throws Exception {
        sendRequestNoObject(Protocol.MEDICAMENTO_UPDATE, m);
    }

    public void Delete(Medicamentos m) throws Exception {
        sendRequestNoObject(Protocol.MEDICAMENTO_DELETE, m);
    }

    public List<Medicamentos> search(Medicamentos m) {
        Object obj = sendRequestForObject(Protocol.MEDICAMENTO_SEARCH, m);
        if (obj == null) return List.of();
        try {
            return (List<Medicamentos>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Medicamentos>: " + obj.getClass().getName(), ex);
        }
    }

    public List<Medicamentos> searchId(Medicamentos m) {
        Object obj = sendRequestForObject(Protocol.MEDICAMENTO_SEARCH_ID, m);
        if (obj == null) return List.of();
        try {
            return (List<Medicamentos>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Medicamentos>: " + obj.getClass().getName(), ex);
        }
    }

    public Medicamentos searchOne(Medicamentos m) {
        Object obj = sendRequestForObject(Protocol.MEDICAMENTO_SEARCH_ONE, m);
        if (obj == null) return null;
        try {
            return (Medicamentos) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo Medicamentos: " + obj.getClass().getName(), ex);
        }
    }

    public List<Medicamentos> findAllMedicamentos() {
        Object obj = sendRequestForObject(Protocol.MEDICAMENTO_FIND_ALL, null);
        if (obj == null) return List.of();
        try {
            return (List<Medicamentos>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Medicamentos>: " + obj.getClass().getName(), ex);
        }
    }

    // =============== Receta ===============
    public void create(Recetas r) throws Exception {
        sendRequestNoObject(Protocol.RECETA_CREATE, r);
    }

    public Recetas read(Recetas r) throws Exception {
        Object obj = sendRequestForObject(Protocol.RECETA_READ, r);
        if (obj == null) throw new Exception("RECETA NO EXISTE");
        try {
            return (Recetas) obj;
        } catch (ClassCastException ex) {
            throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Recetas: " + obj.getClass().getName(), ex);
        }
    }

    public List<Recetas> findAllRecetas() {
        Object obj = sendRequestForObject(Protocol.RECETA_FIND_ALL, null);
        if (obj == null) return List.of();
        try {
            return (List<Recetas>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Recetas>: " + obj.getClass().getName(), ex);
        }
    }

    public List<Recetas> getRecetas() {
        Object obj = sendRequestForObject(Protocol.RECETA_GET_RECETAS, null);
        if (obj == null) return List.of();
        try {
            return (List<Recetas>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Recetas>: " + obj.getClass().getName(), ex);
        }
    }

    public List<Recetas> recetasPorPaciente(Recetas r) {
        Object obj = sendRequestForObject(Protocol.RECETA_POR_PACIENTE, r);
        if (obj == null) return List.of();
        try {
            return (List<Recetas>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Recetas>: " + obj.getClass().getName(), ex);
        }
    }

    public Recetas avanzarEstadoReceta(Recetas r) throws Exception {
        Object obj = sendRequestForObject(Protocol.RECETA_AVANZAR_ESTADO, r);
        if (obj == null) throw new Exception("ERROR AL AVANZAR ESTADO DE RECETA");
        try {
            return (Recetas) obj;
        } catch (ClassCastException ex) {
            throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Recetas: " + obj.getClass().getName(), ex);
        }
    }

    // =============== Empleado ===============
    public Empleado read(Empleado e) throws Exception {
        Object obj = sendRequestForObject(Protocol.EMPLEADO_READ, e);
        if (obj == null) throw new Exception("EMPLEADO NO EXISTE");
        try {
            return (Empleado) obj;
        } catch (ClassCastException ex) {
            throw new Exception("Respuesta inesperada del servidor, el objeto no es de tipo Empleado: " + obj.getClass().getName(), ex);
        }
    }

    public List<Empleado> findAllEmpleados() {
        Object obj = sendRequestForObject(Protocol.EMPLEADO_MENSAJES, null);
        if (obj == null) return List.of();
        try {
            return (List<Empleado>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Empleado>: " + obj.getClass().getName(), ex);
        }
    }

    public List<Empleado> findAllOnlineUsers(Empleado emp){
        Object obj = sendRequestForObject(Protocol.EMPLEADO_ONLINE, emp);
        if (obj == null) return List.of();
        try {
            return (List<Empleado>) obj;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Respuesta inesperada del servidor, el objeto no es de tipo List<Empleado>: " + obj.getClass().getName(), ex);
        }
    }

    public void update(Empleado e) throws Exception {
        sendRequestNoObject(Protocol.EMPLEADO_UPDATE, e);
    }

    public void send_user(Empleado e) throws Exception {
        sendRequestNoObject(Protocol.LOGIN, e);
    }
    public void receive_users() throws Exception {
        //sendRequestNoObject(Protocol.RECEIVE_USERS, null);
    }

    public void send_message(Mensaje msg) throws Exception {
       sendRequestNoObject(Protocol.DELIVER_MESSAGE, msg);
    }

    public void stop(Empleado emp) {
        try {
            disconnect(emp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect(Empleado emp) throws Exception {
        sendRequestNoObject(Protocol.DISCONNECT, emp);
        if (out != null) out.close();
        if (in != null) in.close();
        if (socket != null && !socket.isClosed()) socket.close();
    }
}