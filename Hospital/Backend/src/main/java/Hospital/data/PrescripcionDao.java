package Hospital.data;

import Hospital.Logic.Prescripcion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionDao {
    Database db;

    public PrescripcionDao() { db = Database.instance(); }

    public void create(Prescripcion p) throws Exception {
        String sql = "INSERT INTO prescripcion (medicamento, indicaciones, cantidad, duracion, receta) VALUES (?,?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getMedicamento().getCodigo());
        stm.setString(2, p.getIndicaciones());
        stm.setInt(3, p.getCantidad());
        stm.setInt(4, p.getDuracion());
        stm.setInt(5, p.getReceta());
        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Prescripcion ya existe");
        }
    }

    public Prescripcion read(int id) throws Exception {
        String sql = "SELECT p.idprescripcion, p.indicaciones, p.cantidad, p.duracion, p.receta, " +
                "m.codigo, m.nombre, m.presentacion " +
                "FROM prescripcion p " +
                "INNER JOIN medicamento m ON p.medicamento = m.codigo " +
                "WHERE p.idprescripcion = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = db.executeQuery(stm);

        if (rs.next()) {
            Prescripcion p = new Prescripcion();
            p.setId(rs.getInt("idprescripcion"));
            p.setIndicaciones(rs.getString("indicaciones"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setDuracion(rs.getInt("duracion"));
            p.setReceta(rs.getInt("receta"));

            MedicamentoDao md = new MedicamentoDao();
            p.setMedicamento(md.read(rs.getString("codigo")));
            return p;
        } else {
            throw new Exception("Prescripcion no Existe");
        }
    }

    public void update(Prescripcion p) throws Exception {
        String sql = "UPDATE prescripcion SET medicamento = ?, indicaciones = ?, cantidad = ?, " +
                "duracion = ?, receta = ? WHERE idprescripcion = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getMedicamento().getCodigo());
        stm.setString(2, p.getIndicaciones());
        stm.setInt(3, p.getCantidad());
        stm.setInt(4, p.getDuracion());
        stm.setInt(5, p.getReceta());
        stm.setInt(6, p.getId());
        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Prescripcion no existe");
        }
    }

    public void delete(Prescripcion p) throws Exception {
        String sql = "DELETE FROM prescripcion WHERE idprescripcion = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, p.getId());
        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Prescripcion no existe");
        }
    }

    public void deleteByReceta(int recetaId) throws Exception {
        String sql = "DELETE FROM prescripcion WHERE receta = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, recetaId);
        db.executeUpdate(stm);
    }

    public List<Prescripcion> findByReceta(int recetaId) throws Exception {
        List<Prescripcion> ps = new ArrayList<>();
        String sql = "SELECT p.idprescripcion, p.indicaciones, p.cantidad, p.duracion, p.receta, " +
                "m.codigo, m.nombre, m.presentacion " +
                "FROM prescripcion p " +
                "INNER JOIN medicamento m ON p.medicamento = m.codigo " +
                "WHERE p.receta = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, recetaId);
        ResultSet rs = db.executeQuery(stm);

        MedicamentoDao md = new MedicamentoDao();
        while (rs.next()) {
            Prescripcion p = new Prescripcion();
            p.setId(rs.getInt("idprescripcion"));
            p.setIndicaciones(rs.getString("indicaciones"));
            p.setCantidad(rs.getInt("cantidad"));
            p.setDuracion(rs.getInt("duracion"));
            p.setReceta(rs.getInt("receta"));
            p.setMedicamento(md.read(rs.getString("codigo")));
            ps.add(p);
        }
        return ps;
    }

    public List<Prescripcion> findByID(Prescripcion filter) {
        List<Prescripcion> ps = new ArrayList<>();
        try {
            String sql = "SELECT p.idprescripcion, p.indicaciones, p.cantidad, p.duracion, p.receta, " +
                    "m.codigo, m.nombre, m.presentacion " +
                    "FROM prescripcion p " +
                    "INNER JOIN medicamento m ON p.medicamento = m.codigo " +
                    "WHERE p.idprescripcion = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setInt(1, filter.getId());
            ResultSet rs = db.executeQuery(stm);

            MedicamentoDao md = new MedicamentoDao();
            while (rs.next()) {
                Prescripcion p = new Prescripcion();
                p.setId(rs.getInt("idprescripcion"));
                p.setIndicaciones(rs.getString("indicaciones"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setDuracion(rs.getInt("duracion"));
                p.setReceta(rs.getInt("receta"));
                p.setMedicamento(md.read(rs.getString("codigo")));
                ps.add(p);
            }
        } catch (Exception e) {
        }
        return ps;
    }
}