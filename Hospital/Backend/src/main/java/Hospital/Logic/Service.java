package Hospital.Logic;

import Hospital.data.*;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }


    private AdministradorDao administradorDao;
    private MedicoDao medicoDao;
    private PacientesDao pacientesDao;
    private FarmaceutaDao farmaceutaDao;
    private MedicamentoDao medicamentoDao;
    private EmpleadoDao empleadoDao;
    private RecetaDao recetaDao;
    private PrescripcionDao prescripcionDao;


    private Service(){
        try{
            administradorDao= new AdministradorDao();
            medicoDao= new MedicoDao();
            pacientesDao= new PacientesDao();
            farmaceutaDao= new FarmaceutaDao();
            medicamentoDao= new MedicamentoDao();
            empleadoDao= new EmpleadoDao();
            recetaDao= new RecetaDao();
            prescripcionDao= new PrescripcionDao();
        }
        catch(Exception e){
            System.exit(-1);
        }
    }

    public void stop(){
        try {
            Database.instance().close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // =============== Usuarios ===============

    public Empleado read(Empleado p) throws Exception {
       return empleadoDao.read(p.getId());
    }

    public void update(Empleado p) throws Exception {
        empleadoDao.update(p);
    }

    public Administrador read(Administrador e) throws Exception {
        return administradorDao.read(e.getId());
    }



    // =============== Medicos ===============
    public void create(Medico e) throws Exception {
       medicoDao.create(e);
    }

    public void update(Medico p) throws Exception {
        medicoDao.update(p);
    }


    public Medico read(Medico e) throws Exception {
        return medicoDao.read(e.getId());
    }

    public List<Medico> findAllMedicos() throws Exception {
        Medico filter = new Medico();
        filter.setNombre("");
        return medicoDao.findByNombre(filter);
    }

    public void Delete(Medico e) throws Exception {
        if (e != null) {
            medicoDao.delete(e);
        } else {
            throw new Exception("Medico no existe");
        }
    }

    public List<Medico> search(Medico m){
        try {
            return medicoDao.findByNombre(m);
        }catch(Exception e){
            return null;
        }
    }

    public Medico searchId(Medico m){
        try {
            return medicoDao.findByID(m);
        }catch(Exception e){
            return null;
        }
    }

    public List<Medico> searchIdFilt(Medico m){
        try {
            return medicoDao.findByIDFilt(m);
        }catch(Exception e){
            return null;
        }
    }

//    public Medico searchOne(Medico m){
//        return data.getMedicos().stream()
//                .filter(i -> i.getNombre().toLowerCase().contains(m.getNombre().toLowerCase()))
//                .findFirst()
//                .orElse(null);
//    }
    //==========Pacientes==================

    public void create(Pacientes p) throws Exception {
        pacientesDao.create(p);
    }

    public Pacientes read(Pacientes p) throws Exception {
        try {
            return pacientesDao.read(p.getId());
        }catch(Exception e){
            return null;
        }
    }

    public void update(Pacientes p) throws Exception {
        pacientesDao.update(p);
    }

    public List<Pacientes> findAllPacientes() {
       try {
           Pacientes filter = new Pacientes();
           filter.setNombre("");
           return pacientesDao.findByNombre(filter);
       }catch(Exception e){
              return null;
       }
    }

    public void Delete(Pacientes p) throws Exception {
        if (p != null) {
            pacientesDao.delete(p.getId());
        }else{
            throw new Exception("Persona no existe");
        }
    }

    public Pacientes searchOne(Pacientes p){
        try {
            return pacientesDao.findByIDUnico(p);
        }catch(Exception e){
            return null;
        }
    }

    public List<Pacientes> searchId(Pacientes p){
        return pacientesDao.findByID(p);
    }

    public List<Pacientes> search(Pacientes p){
        try {
            return pacientesDao.findByNombre(p);
        } catch(Exception e){}
            return null;
        }
    //=========Farmaceuta============
    public void create(Farmaceuta p) throws Exception {
       farmaceutaDao.create(p);
    }

    public void update(Farmaceuta p) throws Exception {
        farmaceutaDao.update(p);
    }

    public Farmaceuta read(Farmaceuta p) throws Exception {
        return farmaceutaDao.read(p.getId());
    }

    public List<Farmaceuta> findAllFarmaceutas() {
        try {
            Farmaceuta filter = new Farmaceuta();
            filter.setNombre("");
            return farmaceutaDao.findByNombre(filter);
        }catch (Exception e){
            return null;
        }
    }

    public void Delete(Farmaceuta p) throws Exception {
        if (p != null) {
            farmaceutaDao.delete(p);
        }else{
            throw new Exception("Farmaceuta no existe");
        }
    }

//    public Farmaceuta searchOne(Farmaceuta p){
//        return data.getFarmaceutas().stream()
//                .filter(i -> i.getNombre().toLowerCase().contains(p.getNombre().toLowerCase()))
//                .findFirst()
//                .orElse(null);
//    }

    public List<Farmaceuta> search(Farmaceuta p){
        try{
        return farmaceutaDao.findByNombre(p);
        }catch(Exception e){
            return null;
        }
    }

    public Farmaceuta searchId(Farmaceuta p){
        try{
            return farmaceutaDao.findByID(p);
        }catch(Exception e){
            return null;
        }
    }

    public List<Farmaceuta> searchIdFilt(Farmaceuta p){
        try{
            return farmaceutaDao.findByIDFilt(p);
        }catch(Exception e){
            return null;
        }
    }
    // =============== Medicamentos ===============
    public void create(Medicamentos m) throws Exception {
        medicamentoDao.create(m);
    }

    public Medicamentos read(Medicamentos m) throws Exception {
        return medicamentoDao.read(m.getCodigo());
    }

    public List<Medicamentos> findAllMedicamentos() {
        try {
            Medicamentos filter = new Medicamentos();
            filter.setNombre("");
            return medicamentoDao.findByNombre(filter);
        }catch (Exception e){
            return null;
        }
    }

    public void Delete(Medicamentos m) throws Exception {
        if (m != null) {
            medicamentoDao.delete(m);
        }else{
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamentos> search(Medicamentos m){
        try {
            return medicamentoDao.findByNombre(m);
        }catch (Exception e){
            return null;
        }
    }

    public List<Medicamentos> searchId(Medicamentos m){
        try {
            return medicamentoDao.findByCodigo(m);
        }catch (Exception e){
            return null;
        }
    }

    public Medicamentos searchOne(Medicamentos m){
        try {
            return medicamentoDao.findByCodigoUnico(m);
        }catch (Exception e){
            return null;
        }
    }
    public void update(Medicamentos m) throws Exception {
        medicamentoDao.update(m);
    }
    // =============== Prescripciones ===============

    //Recetas
    public void create(Recetas r) throws Exception {
        recetaDao.create(r);
    }

    public List<Recetas> findAllRecetas() {
        try {
            Recetas filter = new Recetas();
            filter.setEstado("");
            return recetaDao.findByEstado(filter);
        }catch (Exception e){
            return new ArrayList<Recetas>();
        }
    }

    public Recetas read(Recetas r) throws Exception {
        return recetaDao.read(r);
    }
    // Despacho
    public List<Recetas> getRecetas() {
        try{
            Recetas filter = new Recetas();
            filter.setEstado("");
            return recetaDao.findByEstado(filter);
        }catch (Exception e){
            return null;
        }
    }

//    public List<Recetas> findRecetasPorRangoFecha(java.time.LocalDate from, java.time.LocalDate to) {
//       try {
//           Recetas filter = new Recetas();
//           filter.setEstado("");
//           return recetaDao.findByEstado(filter).stream()
//                   .filter(r -> r.getFechaRetiro() != null &&
//                           !r.getFechaRetiro().isBefore(from) &&
//                           !r.getFechaRetiro().isAfter(to))
//                   .collect(java.util.stream.Collectors.toList());
//       }catch (Exception e){
//           return null;
//       }
//    }

    public List<Recetas> recetasPorPaciente(Recetas r) {
        try {
            List<Recetas> lista = new ArrayList<>();
            Recetas filter = new Recetas();
            filter.setEstado("");
            for(Recetas receta : recetaDao.findByEstado(filter)){
                if(receta.getPaciente().getId().equals(r.getPaciente().getId())){
                    if(receta.getId() == r.getId()){
                        receta = r;
                        recetaDao.update(receta);
                        lista.add(receta);
                    }
                    else{
                        lista.add(receta);
                    }
                }
            }
            return lista;
        }catch (Exception e){
            return null;
        }
    }
    public Recetas avanzarEstadoReceta(Recetas r) throws Exception {
        if (r == null) throw new Exception("Seleccione una receta.");
        switch (r.getEstado()) {
            case "CONFECCIONADA":
                r.setEstado("PROCESO");
                return r;
            case "PROCESO":
                r.setEstado("LISTA");
                return r;
            case "LISTA":
                r.setEstado("ENTREGADA");
                return r;
            case "ENTREGADA":
                return null;
            default:
                throw new Exception("Estado inválido: " + r.getEstado());
        }
    }
}
