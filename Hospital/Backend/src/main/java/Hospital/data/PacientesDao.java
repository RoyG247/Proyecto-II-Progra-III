package Hospital.data;

import Hospital.Logic.Pacientes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class PacientesDao {
    Database db;

    public PacientesDao(){ db=  Database.instance(); }

    public void create(Pacientes p) throws Exception{
        String sql="insert into Pacientes (id, nombre, numTelefono, fechaNacimiento) values (?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getId());
        stm.setString(2, p.getNombre());
        stm.setString(3, p.getNumeroTelefono());
        stm.setObject(4, p.getFechaNacimiento());
        if (db.executeUpdate(stm) == 0) {
            throw new Exception ("Paciente ya existe");
        }
    }

    public Pacientes read(String id) throws Exception{
        String sql="SELECT p.id, p.nombre,p.numTelefono,p.fechaNacimiento FROM pacientes p " +
                "WHERE p.id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs =  db.executeQuery(stm);
        if (rs.next()) {
            return from(rs,"p");
        }
        else{
            throw new Exception ("Paciente no Existe");
        }
    }

    public void update(Pacientes p) throws Exception{
        String sql="update Pacientes set nombre=?, numTelefono=?, fechaNacimiento=? where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getNombre());
        stm.setString(2, p.getNumeroTelefono());
        stm.setObject(3, p.getFechaNacimiento());
        stm.setString(4, p.getId());
        if (db.executeUpdate(stm)==0){
            throw new Exception ("Paciente no existe");
        }
    }

    public void delete(String id) throws Exception{
        String sql="delete from Pacientes where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        if (db.executeUpdate(stm)==0){
            throw new Exception ("Paciente no existe");
        }
    }

    public List<Pacientes> findByNombre(Pacientes filtro) throws Exception{
        List<Pacientes> ps=new ArrayList<Pacientes>();
        try {
            String sql="select * from Pacientes p where p.nombre like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + filtro.getNombre() + "%");
            ResultSet rs =  db.executeQuery(stm);
            Pacientes p;
            while (rs.next()) {
                p=from(rs,"p");
                ps.add(p);
            }
        } catch (SQLException ex) { }
        return ps;
    }

    public List<Pacientes> findByID(Pacientes filtro){
        List<Pacientes> ps=new ArrayList<Pacientes>();
        try {
            String sql="select * from Pacientes p where p.id= ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, filtro.getId());
            ResultSet rs =  db.executeQuery(stm);
            Pacientes p;
            while (rs.next()) {
                p=from(rs,"p");
                ps.add(p);
            }
        } catch (SQLException ex) { }
        return ps;
    }

    public Pacientes findByIDUnico(Pacientes filtro) throws Exception{
        try {
            String sql="select * from Pacientes p where p.id= ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1,  filtro.getId());
            ResultSet rs =  db.executeQuery(stm);
            Pacientes p;
            while (rs.next()) {
                p=from(rs,"p");
                return p;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Pacientes from(ResultSet rs, String alias){
        try {
            Pacientes p= new Pacientes();
            p.setId(rs.getString(alias + ".id"));
            p.setNombre(rs.getString(alias + ".nombre"));
            p.setNumeroTelefono(rs.getString(alias + ".numTelefono"));
            p.setFechaNacimiento(rs.getObject(alias + ".fechaNacimiento", LocalDate.class));
            return p;
        } catch (SQLException ex) {
            return null;
        }
    }
}
