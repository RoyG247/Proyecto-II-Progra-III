// java
package Hospital.data;

import Hospital.Logic.Administrador;
import Hospital.Logic.Empleado;
import Hospital.Logic.Farmaceuta;
import Hospital.Logic.Medico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao {
    Database db;

    public EmpleadoDao(){ db = Database.instance(); }

   public void create(Empleado e) throws Exception{
       String sql = "INSERT INTO empleado (id, clave, rol) VALUES (?,?,?)";
       PreparedStatement stm = db.prepareStatement(sql);
       stm.setString(1, e.getId());
       stm.setString(2, e.getClave());
       stm.setString(3, e.getRol());
       if (db.executeUpdate(stm) == 0){
           throw new Exception("Empleado ya existe");
       }
   }

    public Empleado read(String id) throws Exception{
        String sql = "SELECT e.id, e.clave, e.rol FROM empleado e WHERE e.id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs);
        } else {
            throw new Exception("Empleado no Existe");
        }
    }

    public void update(Empleado e) throws Exception{
        String sql = "UPDATE empleado SET clave = ? WHERE id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getClave());
        stm.setString(2, e.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Empleado no existe");
        }
    }

    public void delete(Empleado e) throws Exception{
        String sql = "DELETE FROM empleado WHERE id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Empleado no existe");
        }
    }

    public List<Empleado> findByRol(Empleado filtro) throws Exception{
        List<Empleado> list = new ArrayList<Empleado>();
        String sql = "SELECT e.id, e.clave, e.rol FROM empleado e WHERE e.rol LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + (filtro.getRol() == null ? "" : filtro.getRol()) + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            list.add(from(rs));
        }
        return list;
    }

    public List<Empleado> findAll() throws Exception{
        List<Empleado> list = new ArrayList<Empleado>();
        String sql = "SELECT e.id, e.clave, e.rol FROM empleado e";
        PreparedStatement stm = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            list.add(from(rs));
        }
        return list;
    }

    public Empleado authenticate(String id, String clave) throws Exception{
        String sql = "SELECT e.id, e.clave, e.rol FROM empleado e WHERE e.id = ? AND e.clave = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        stm.setString(2, clave);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs);
        } else {
            throw new Exception("Credenciales inválidas");
        }
    }

    private Empleado from(ResultSet rs) throws SQLException {
        String rol = rs.getString("rol");
        if (rol == null) {
            throw new SQLException("Rol nulo en registro de empleado");
        }

        Empleado emp;
        switch (rol.trim().toUpperCase()) {
            case "FARMACEUTA":
            case "FAR":
                emp = new Farmaceuta();
                break;
            case "MED":
            case "MEDICO":
                emp = new Medico();
                break;
            case "ADM":
            case "ADMINISTRADOR":
                emp = new Administrador();
                break;
            default:
                throw new SQLException("Tipo de empleado desconocido: " + rol);
        }
        emp.setId(rs.getString("id"));
        emp.setClave(rs.getString("clave"));
        emp.setRol(rol);
        return emp;
    }
}