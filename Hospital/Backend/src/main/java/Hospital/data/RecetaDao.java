package Hospital.data;

import Hospital.Logic.Prescripcion;
import Hospital.Logic.Recetas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class RecetaDao {
    Database db;

    public RecetaDao() {
        db = Database.instance();
    }

    public void create(Recetas r) throws Exception {
        String sql = "INSERT INTO recetas (fechadeEmision, fechaRetiro, estado, paciente, medico) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setObject(1, r.getFechadeEmision());
        stm.setObject(2, r.getFechaRetiro());
        stm.setString(3, r.getEstado());
        stm.setString(4, r.getPaciente().getId());
        stm.setString(5, r.getMedico().getId());

        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Receta no se pudo crear");
        }

        // Obtener el ID generado
        String sqlGetId = "SELECT LAST_INSERT_ID() as id";
        PreparedStatement stmId = db.prepareStatement(sqlGetId);
        ResultSet rs = db.executeQuery(stmId);
        if (rs.next()) {
            r.setId(rs.getInt("id"));
        }

        // Crear las prescripciones asociadas
        if (r.getPrescripciones() != null && !r.getPrescripciones().isEmpty()) {
            PrescripcionDao pdao = new PrescripcionDao();
            for (Prescripcion p : r.getPrescripciones()) {
                p.setReceta(r.getId());
                pdao.create(p);
            }
        }
    }

    public Recetas read(Recetas r) throws Exception {
        try {

            String sql = "SELECT r.numero, r.fechadeEmision, r.fechaRetiro, r.estado, " +
                    "r.paciente, r.medico " +
                    "FROM recetas r " +
                    "WHERE r.numero = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setInt(1, r.getId());
            ResultSet rs2 = db.executeQuery(stm);

            if (rs2.next()) {
                r.setId(rs2.getInt("numero"));
                r.setFechadeEmision(rs2.getObject("fechadeEmision", LocalDate.class));
                r.setFechaRetiro(rs2.getObject("fechaRetiro", LocalDate.class));
                r.setEstado(rs2.getString("estado"));

                PacientesDao pdao = new PacientesDao();
                r.setPaciente(pdao.read(rs2.getString("paciente")));

                MedicoDao mdao = new MedicoDao();
                r.setMedico(mdao.read(rs2.getString("medico")));

                // Cargar las prescripciones asociadas
                PrescripcionDao prescDao = new PrescripcionDao();
                r.setPrescripciones(prescDao.findByReceta(r.getId()));

                return r;
            } else {
                throw new Exception("Receta no existe");
            }
        } catch (SQLException ex) {
            throw new Exception("Error al leer receta: " + ex.getMessage(), ex);
        }
    }

    public void update(Recetas r) throws Exception {
        String sql = "UPDATE recetas SET fechadeEmision = ?, fechaRetiro = ?, estado = ?, " +
                "paciente = ?, medico = ? WHERE numero = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setObject(1, r.getFechadeEmision());
        stm.setObject(2, r.getFechaRetiro());
        stm.setString(3, r.getEstado());
        stm.setString(4, r.getPaciente().getId());
        stm.setString(5, r.getMedico().getId());
        stm.setInt(6, r.getId());

        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Receta no existe");
        }

        // Actualizar prescripciones: eliminar las anteriores y crear las nuevas
        PrescripcionDao pdao = new PrescripcionDao();
        pdao.deleteByReceta(r.getId());

        if (r.getPrescripciones() != null && !r.getPrescripciones().isEmpty()) {
            for (Prescripcion p : r.getPrescripciones()) {
                p.setReceta(r.getId());
                pdao.create(p);
            }
        }
    }

    public void delete(Recetas r) throws Exception {
        // Primero eliminar las prescripciones asociadas
        PrescripcionDao pdao = new PrescripcionDao();
        pdao.deleteByReceta(r.getId());

        // Luego eliminar la receta
        String sql = "DELETE FROM recetas WHERE numero = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, r.getId());

        if (db.executeUpdate(stm) == 0) {
            throw new Exception("Receta no existe");
        }
    }

    public Recetas findByID(Recetas filtro) throws Exception {
        try {
            String sql = "SELECT r.numero, r.fechadeEmision, r.fechaRetiro, r.estado, " +
                    "r.paciente, r.medico " +
                    "FROM recetas r " +
                    "WHERE r.numero = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setInt(1, filtro.getId());
            ResultSet rset = db.executeQuery(stm);

            PacientesDao pdao = new PacientesDao();
            MedicoDao mdao = new MedicoDao();
            PrescripcionDao prescDao = new PrescripcionDao();

            while (rset.next()) {
                Recetas r = new Recetas(null, null, null);
                r.setId(rset.getInt("numero"));
                r.setFechadeEmision(rset.getObject("fechadeEmision", LocalDate.class));
                r.setFechaRetiro(rset.getObject("fechaRetiro", LocalDate.class));
                r.setEstado(rset.getString("estado"));
                r.setPaciente(pdao.read(rset.getString("paciente")));
                r.setMedico(mdao.read(rset.getString("medico")));
                r.setPrescripciones(prescDao.findByReceta(r.getId()));
                r.setEstado(filtro.getEstado());
                return r;
            }
        } catch (SQLException ex) {
            throw new Exception("Error al buscar recetas por ID: " + ex.getMessage(), ex);
        }
        return null;
    }

    public List<Recetas> findByEstado(Recetas filtro) throws Exception {
        List<Recetas> rs = new ArrayList<>();
        try {
            String sql = "SELECT r.numero, r.fechadeEmision, r.fechaRetiro, r.estado, " +
                    "r.paciente, r.medico " +
                    "FROM recetas r " +
                    "WHERE r.estado LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + filtro.getEstado() + "%");
            ResultSet rset = db.executeQuery(stm);

            PacientesDao pdao = new PacientesDao();
            MedicoDao mdao = new MedicoDao();
            PrescripcionDao prescDao = new PrescripcionDao();

            while (rset.next()) {
                Recetas r = new Recetas(null, null, null);
                r.setId(rset.getInt("numero"));
                r.setFechadeEmision(rset.getObject("fechadeEmision", LocalDate.class));
                r.setFechaRetiro(rset.getObject("fechaRetiro", LocalDate.class));
                r.setEstado(rset.getString("estado"));
                r.setPaciente(pdao.read(rset.getString("paciente")));
                r.setMedico(mdao.read(rset.getString("medico")));
                r.setPrescripciones(prescDao.findByReceta(r.getId()));
                rs.add(r);
            }
        } catch (SQLException ex) {
            throw new Exception("Error al buscar recetas: " + ex.getMessage(), ex);
        }
        return rs;
    }
}