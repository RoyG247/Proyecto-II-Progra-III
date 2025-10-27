package Hospital.Logic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Service() {
        try {
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    // =============== Administrador ===============
    public Administrador read(Administrador a) throws Exception {
        out.writeInt(Protocol.ADMINISTRADOR_READ);
        out.writeObject(a);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Administrador) in.readObject();
        }
        throw new Exception("ADMINISTRADOR NO EXISTE");
    }

    // =============== Médico ===============
    public void create(Medico m) throws Exception {
        out.writeInt(Protocol.MEDICO_CREATE);
        out.writeObject(m);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL CREAR MÉDICO");
        }
    }

    public Medico read(Medico m) throws Exception {
        out.writeInt(Protocol.MEDICO_READ);
        out.writeObject(m);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Medico) in.readObject();
        }
        throw new Exception("MÉDICO NO EXISTE");
    }

    public void update(Medico m) throws Exception {
        out.writeInt(Protocol.MEDICO_UPDATE);
        out.writeObject(m);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ACTUALIZAR MÉDICO");
        }
    }

    public void Delete(Medico m) throws Exception {
        out.writeInt(Protocol.MEDICO_DELETE);
        out.writeObject(m);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ELIMINAR MÉDICO");
        }
    }

    public List<Medico> search(Medico m) {
        try {
            out.writeInt(Protocol.MEDICO_SEARCH);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medico>) in.readObject();
            }
            return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }

    public Medico searchId(Medico m) {
        try {
            out.writeInt(Protocol.MEDICO_SEARCH_ID);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (Medico) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Medico> searchIdFilt(Medico m) {
        try {
            out.writeInt(Protocol.MEDICO_SEARCH_ID_FILT);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medico>) in.readObject();
            }
            return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Medico> findAllMedicos() {
        try {
            out.writeInt(Protocol.MEDICO_FIND_ALL);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medico>) in.readObject();
            }
            return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }

    // =============== Paciente ===============
    public void create(Pacientes p) throws Exception {
        out.writeInt(Protocol.PACIENTE_CREATE);
        out.writeObject(p);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL CREAR PACIENTE");
        }
    }

    public Pacientes read(Pacientes p) throws Exception {
        out.writeInt(Protocol.PACIENTE_READ);
        out.writeObject(p);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Pacientes) in.readObject();
        }
        return null;
    }

    public void update(Pacientes p) throws Exception {
        out.writeInt(Protocol.PACIENTE_UPDATE);
        out.writeObject(p);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ACTUALIZAR PACIENTE");
        }
    }

    public void Delete(Pacientes p) throws Exception {
        out.writeInt(Protocol.PACIENTE_DELETE);
        out.writeObject(p);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ELIMINAR PACIENTE");
        }
    }

    public List<Pacientes> search(Pacientes p) {
        try {
            out.writeInt(Protocol.PACIENTE_SEARCH);
            out.writeObject(p);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Pacientes>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Pacientes searchOne(Pacientes p) {
        try {
            out.writeInt(Protocol.PACIENTE_SEARCH_ONE);
            out.writeObject(p);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (Pacientes) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Pacientes> searchId(Pacientes p) {
        try {
            out.writeInt(Protocol.PACIENTE_SEARCH_ID);
            out.writeObject(p);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Pacientes>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Pacientes> findAllPacientes() {
        try {
            out.writeInt(Protocol.PACIENTE_FIND_ALL);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Pacientes>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // =============== Farmaceuta ===============
    public void create(Farmaceuta f) throws Exception {
        out.writeInt(Protocol.FARMACEUTA_CREATE);
        out.writeObject(f);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL CREAR FARMACEUTA");
        }
    }

    public Farmaceuta read(Farmaceuta f) throws Exception {
        out.writeInt(Protocol.FARMACEUTA_READ);
        out.writeObject(f);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Farmaceuta) in.readObject();
        }
        throw new Exception("FARMACEUTA NO EXISTE");
    }

    public void update(Farmaceuta f) throws Exception {
        out.writeInt(Protocol.FARMACEUTA_UPDATE);
        out.writeObject(f);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ACTUALIZAR FARMACEUTA");
        }
    }

    public void Delete(Farmaceuta f) throws Exception {
        out.writeInt(Protocol.FARMACEUTA_DELETE);
        out.writeObject(f);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ELIMINAR FARMACEUTA");
        }
    }

    public List<Farmaceuta> search(Farmaceuta f) {
        try {
            out.writeInt(Protocol.FARMACEUTA_SEARCH);
            out.writeObject(f);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Farmaceuta>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Farmaceuta searchId(Farmaceuta f) {
        try {
            out.writeInt(Protocol.FARMACEUTA_SEARCH_ID);
            out.writeObject(f);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (Farmaceuta) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Farmaceuta> searchIdFilt(Farmaceuta f) {
        try {
            out.writeInt(Protocol.FARMACEUTA_SEARCH_ID_FILT);
            out.writeObject(f);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Farmaceuta>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Farmaceuta> findAllFarmaceutas() {
        try {
            out.writeInt(Protocol.FARMACEUTA_FIND_ALL);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Farmaceuta>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // =============== Medicamento ===============
    public void create(Medicamentos m) throws Exception {
        out.writeInt(Protocol.MEDICAMENTO_CREATE);
        out.writeObject(m);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL CREAR MEDICAMENTO");
        }
    }

    public Medicamentos read(Medicamentos m) throws Exception {
        out.writeInt(Protocol.MEDICAMENTO_READ);
        out.writeObject(m);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Medicamentos) in.readObject();
        }
        throw new Exception("MEDICAMENTO NO EXISTE");
    }

    public void update(Medicamentos m) throws Exception {
        out.writeInt(Protocol.MEDICAMENTO_UPDATE);
        out.writeObject(m);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ACTUALIZAR MEDICAMENTO");
        }
    }

    public void Delete(Medicamentos m) throws Exception {
        out.writeInt(Protocol.MEDICAMENTO_DELETE);
        out.writeObject(m);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ELIMINAR MEDICAMENTO");
        }
    }

    public List<Medicamentos> search(Medicamentos m) {
        try {
            out.writeInt(Protocol.MEDICAMENTO_SEARCH);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamentos>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Medicamentos> searchId(Medicamentos m) {
        try {
            out.writeInt(Protocol.MEDICAMENTO_SEARCH_ID);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamentos>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Medicamentos searchOne(Medicamentos m) {
        try {
            out.writeInt(Protocol.MEDICAMENTO_SEARCH_ONE);
            out.writeObject(m);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (Medicamentos) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Medicamentos> findAllMedicamentos() {
        try {
            out.writeInt(Protocol.MEDICAMENTO_FIND_ALL);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamentos>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // =============== Receta ===============
    public void create(Recetas r) throws Exception {
        out.writeInt(Protocol.RECETA_CREATE);
        out.writeObject(r);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL CREAR RECETA");
        }
    }

    public Recetas read(Recetas r) throws Exception {
        out.writeInt(Protocol.RECETA_READ);
        out.writeObject(r);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Recetas) in.readObject();
        }
        throw new Exception("RECETA NO EXISTE");
    }

    public List<Recetas> findAllRecetas() {
        try {
            out.writeInt(Protocol.RECETA_FIND_ALL);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Recetas>) in.readObject();
            }
            return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Recetas> getRecetas() {
        try {
            out.writeInt(Protocol.RECETA_GET_RECETAS);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Recetas>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Recetas> recetasPorPaciente(Recetas r) {
        try {
            out.writeInt(Protocol.RECETA_POR_PACIENTE);
            out.writeObject(r);
            out.flush();
            if (in.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Recetas>) in.readObject();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Recetas avanzarEstadoReceta(Recetas r) throws Exception {
        out.writeInt(Protocol.RECETA_AVANZAR_ESTADO);
        out.writeObject(r);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Recetas) in.readObject();
        }
        throw new Exception("ERROR AL AVANZAR ESTADO");
    }

    // =============== Empleado ===============
    public Empleado read(Empleado e) throws Exception {
        out.writeInt(Protocol.EMPLEADO_READ);
        out.writeObject(e);
        out.flush();
        if (in.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Empleado) in.readObject();
        }
        throw new Exception("EMPLEADO NO EXISTE");
    }

    public void update(Empleado e) throws Exception {
        out.writeInt(Protocol.EMPLEADO_UPDATE);
        out.writeObject(e);
        out.flush();
        if (in.readInt() != Protocol.ERROR_NO_ERROR) {
            throw new Exception("ERROR AL ACTUALIZAR EMPLEADO");
        }
    }

    // =============== Desconexión ===============
    private void disconnect() throws Exception {
        out.writeInt(Protocol.DISCONNECT);
        out.flush();
        socket.shutdownOutput();
        socket.close();
    }

    public void stop() {
        try {
            disconnect();
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}