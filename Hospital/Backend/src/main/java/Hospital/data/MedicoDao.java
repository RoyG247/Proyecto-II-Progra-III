package Hospital.data;

import Hospital.Logic.Empleado;
import Hospital.Logic.Medico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class MedicoDao {
    Database db;

    public MedicoDao() { db= Database.instance();}

   public void create(Medico m) throws Exception {
        EmpleadoDao empleadoDao = new EmpleadoDao();
        m.setRol("MED");
        empleadoDao.create(m);

        String sql = "INSERT INTO medico (id, nombre, especialidad) VALUES (?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getId());
        stm.setString(2, m.getNombre());
        stm.setString(3, m.getEspecialidad());
        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Medico ya existe");
        }
    }

    public Medico read(String id) throws Exception {
        String sql = "SELECT m.id, m.nombre, m.especialidad, e.clave FROM medico m " +
                "INNER JOIN empleado e ON m.id = e.id WHERE m.id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Medico m = new Medico();
            m.setId(rs.getString("id"));
            m.setNombre(rs.getString("nombre"));
            m.setEspecialidad(rs.getString("especialidad"));
            m.setClave(rs.getString("clave"));
            m.setRol("MED");
            return m;
        } else {
            throw new Exception("Medico no Existe");
        }
    }

    public void update(Medico m) throws Exception {
        EmpleadoDao empleadoDao = new EmpleadoDao();
        empleadoDao.update(m);

        String sql = "UPDATE medico SET nombre = ?, especialidad = ? WHERE id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getNombre());
        stm.setString(2, m.getEspecialidad());
        stm.setString(3, m.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Medico no existe");
        }
    }

    public void delete(Medico m) throws Exception {
        String sql = "DELETE FROM medico WHERE id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Medico no existe");
        }
        EmpleadoDao empleadoDao = new EmpleadoDao();
        Empleado emp = new Medico();
        emp.setId(m.getId());
        empleadoDao.delete(emp);
    }


    public List<Medico> findByNombre(Medico filtro) throws Exception {
        List<Medico> ms = new ArrayList<>();
        String sql = "SELECT m.id, m.nombre, m.especialidad, e.clave FROM medico m " +
                "INNER JOIN empleado e ON m.id = e.id WHERE m.nombre LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + filtro.getNombre() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Medico m = new Medico();
            m.setId(rs.getString("id"));
            m.setNombre(rs.getString("nombre"));
            m.setEspecialidad(rs.getString("especialidad"));
            m.setClave(rs.getString("clave"));
            m.setRol("MED");
            ms.add(m);
        }
        return ms;
    }

    public Medico findByID(Medico filtro) throws Exception {
        String sql = "SELECT m.id, m.nombre, m.especialidad, e.clave FROM medico m " +
                "INNER JOIN empleado e ON m.id = e.id WHERE m.id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, filtro.getId());
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Medico m = new Medico();
            m.setId(rs.getString("id"));
            m.setNombre(rs.getString("nombre"));
            m.setEspecialidad(rs.getString("especialidad"));
            m.setClave(rs.getString("clave"));
            m.setRol("MED");
            return m;
        } else {
            throw new Exception("Medico no Existe");
        }
    }

    public List<Medico> findByIDFilt(Medico filtro) throws Exception {
        List<Medico> ms = new ArrayList<>();
        String sql = "SELECT m.id, m.nombre, m.especialidad, e.clave FROM medico m " +
                "INNER JOIN empleado e ON m.id = e.id WHERE m.id LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + filtro.getId() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Medico m = new Medico();
            m.setId(rs.getString("id"));
            m.setNombre(rs.getString("nombre"));
            m.setEspecialidad(rs.getString("especialidad"));
            m.setClave(rs.getString("clave"));
            m.setRol("MED");
            ms.add(m);
        }
        return ms;
    }



    public Medico from(ResultSet rs, String alias){
        try {
            Medico m= new Medico();
            m.setId(rs.getString(alias + ".id"));
            m.setNombre(rs.getString(alias + ".nombre"));
            m.setEspecialidad(rs.getString(alias + ".especialidad"));
            m.setClave(rs.getString(alias + ".clave"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
}
