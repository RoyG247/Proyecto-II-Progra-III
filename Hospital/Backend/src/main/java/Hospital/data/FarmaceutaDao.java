package Hospital.data;

import Hospital.Logic.Empleado;
import Hospital.Logic.Farmaceuta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaDao {
    Database db;

    public FarmaceutaDao() { db = Database.instance(); }

    public void create(Farmaceuta f) throws Exception {
        EmpleadoDao empleadoDao = new EmpleadoDao();
        f.setRol("FAR");
        empleadoDao.create(f);
        String sql = "INSERT INTO farmaceuta (id, nombre) VALUES (?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, f.getId());
        stm.setString(2, f.getNombre());

        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Farmaceuta ya existe");
        }
    }

    public Farmaceuta read(String id) throws Exception {
        String sql = "SELECT f.id, f.nombre, e.clave FROM farmaceuta f " +
                "INNER JOIN empleado e ON f.id = e.id WHERE f.id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Farmaceuta a = new Farmaceuta();
            a.setId(rs.getString("id"));
            a.setNombre(rs.getString("nombre"));
            a.setClave(rs.getString("clave"));
            a.setRol("FAR");
            return a;
        } else {
            throw new Exception("Farmaceuta no Existe");
        }
    }

    public void update(Farmaceuta f) throws Exception {
        EmpleadoDao empleadoDao = new EmpleadoDao();
        empleadoDao.update(f);

        String sql = "UPDATE farmaceuta SET nombre = ? WHERE id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, f.getNombre());
        stm.setString(2, f.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Farmaceuta no existe");
        }
    }

    public void delete(Farmaceuta f) throws Exception {
        String sql = "DELETE FROM farmaceuta WHERE id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, f.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Farmaceuta no existe");
        }
        EmpleadoDao empleadoDao = new EmpleadoDao();
        Empleado emp = new Farmaceuta();
        emp.setId(f.getId());
        empleadoDao.delete(emp);
    }

    public List<Farmaceuta> findByNombre(Farmaceuta filtro) throws Exception {
        List<Farmaceuta> fs = new ArrayList<>();
        String sql = "SELECT f.id, f.nombre, e.clave FROM farmaceuta f " +
                "INNER JOIN empleado e ON f.id = e.id WHERE f.nombre LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + filtro.getNombre() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Farmaceuta f = new Farmaceuta();
            f.setId(rs.getString("id"));
            f.setNombre(rs.getString("nombre"));
            f.setClave(rs.getString("clave"));
            f.setRol("FAR");
            fs.add(f);
        }
        return fs;
    }

    public Farmaceuta findByID(Farmaceuta filtro) throws Exception {
        String sql = "SELECT f.id, f.nombre, e.clave FROM farmaceuta f " +
                "INNER JOIN empleado e ON f.id = e.id WHERE f.id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, filtro.getId());
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Farmaceuta f = new Farmaceuta();
            f.setId(rs.getString("id"));
            f.setNombre(rs.getString("nombre"));
            f.setClave(rs.getString("clave"));
            f.setRol("FAR");
            return f;
        } else {
            throw new Exception("Farmaceuta no Existe");
        }
    }

    public List<Farmaceuta> findByIDFilt(Farmaceuta filtro) throws Exception {
        List<Farmaceuta> fs = new ArrayList<>();
        String sql = "SELECT f.id, f.nombre, e.clave FROM farmaceuta f " +
                "INNER JOIN empleado e ON f.id = e.id WHERE f.id LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + filtro.getId() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Farmaceuta f = new Farmaceuta();
            f.setId(rs.getString("id"));
            f.setNombre(rs.getString("nombre"));
            f.setClave(rs.getString("clave"));
            f.setRol("FAR");
            fs.add(f);
        }
        return fs;
    }

    public Farmaceuta from(ResultSet rs, String alias) {
        try {
            Farmaceuta f = new Farmaceuta();
            f.setId(rs.getString(alias + ".id"));
            f.setNombre(rs.getString(alias + ".nombre"));
            f.setClave(rs.getString(alias + ".clave"));
            return f;
        } catch (SQLException ex) {
            return null;
        }
    }
}