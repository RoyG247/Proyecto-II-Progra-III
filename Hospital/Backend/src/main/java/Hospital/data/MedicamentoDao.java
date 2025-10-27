package Hospital.data;

import Hospital.Logic.Medicamentos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDao {
    Database ds;

    public MedicamentoDao() { ds= Database.instance();}

    public void create(Medicamentos m) throws Exception{
        String sql="INSERT INTO medicamento (codigo, nombre, presentacion) values (?,?,?)";
        PreparedStatement stm = ds.prepareStatement(sql);
        stm.setString(1, m.getCodigo());
        stm.setString(2, m.getNombre());
        stm.setString(3, m.getPresentacion());
        if (ds.executeUpdate(stm)==0){
            throw new Exception("Medicamento ya existe");
        }
    }

    public Medicamentos read(String codigo) throws Exception{
        String sql="SELECT m.codigo, m.nombre, m.presentacion FROM medicamento m " +
                "WHERE m.codigo = ?";
        PreparedStatement stm = ds.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs =  ds.executeQuery(stm);
        if (rs.next()) {
            Medicamentos m = new Medicamentos("", "", "");
            m.setCodigo(rs.getString("codigo"));
            m.setNombre(rs.getString("nombre"));
            m.setPresentacion(rs.getString("presentacion"));
            return m;
        }
        else{
            throw new Exception ("Medicamento no Existe");
        }
    }

    public void update(Medicamentos m) throws Exception{
        String sql="update medicamento m set m.nombre=?, m.presentacion=? where m.codigo=?";
        PreparedStatement stm = ds.prepareStatement(sql);
        stm.setString(1, m.getNombre());
        stm.setString(2, m.getPresentacion());
        stm.setString(3, m.getCodigo());
        if (ds.executeUpdate(stm)==0){
            throw new Exception("Medicamento no existe");
        }
    }

    public void delete(Medicamentos m) throws Exception{
        String sql="delete from medicamento where codigo = ? ";
        PreparedStatement stm = ds.prepareStatement(sql);
        stm.setString(1, m.getCodigo());
        int count = ds.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamentos> findByNombre(Medicamentos filtro) throws Exception{
        List<Medicamentos> ms=new ArrayList<Medicamentos>();
        try {
            String sql="select * from medicamento m where m.nombre like ?";
            PreparedStatement stm = ds.prepareStatement(sql);
            stm.setString(1, "%"+ filtro.getNombre() +"%");
            ResultSet rs =  ds.executeQuery(stm);
            Medicamentos m;
            while (rs.next()) {
                m=from(rs,"m");
                if (m!=null){
                    ms.add(m);
                }
            }
        } catch (SQLException ex) { }
        return ms;
    }

    public Medicamentos findByCodigoUnico(Medicamentos filtro) throws Exception{
        try {
            String sql="select * from medicamento m where m.codigo=?";
            PreparedStatement stm = ds.prepareStatement(sql);
            stm.setString(1,  filtro.getCodigo());
            ResultSet rs =  ds.executeQuery(stm);
            Medicamentos m;
            while (rs.next()) {
                m=from(rs,"m");
                return m;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Medicamentos> findByCodigo(Medicamentos filtro) throws Exception{
        List<Medicamentos> ms=new ArrayList<Medicamentos>();
        try {
            String sql="select * from medicamento m where m.codigo=?";
            PreparedStatement stm = ds.prepareStatement(sql);
            stm.setString(1, filtro.getCodigo());
            ResultSet rs =  ds.executeQuery(stm);
            Medicamentos m;
            while (rs.next()) {
                m=from(rs,"m");
                    ms.add(m);
            }
        } catch (SQLException ex) { }
        return ms;
    }

    public Medicamentos from(ResultSet rs, String alias){
        try {
            Medicamentos m= new Medicamentos("", "", "");
            m.setCodigo(rs.getString(alias + ".codigo"));
            m.setNombre(rs.getString(alias + ".nombre"));
            m.setPresentacion(rs.getString(alias + ".presentacion"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
}
