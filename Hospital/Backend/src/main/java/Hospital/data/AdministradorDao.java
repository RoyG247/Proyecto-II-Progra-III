package Hospital.data;

    import Hospital.Logic.Administrador;
    import Hospital.Logic.Empleado;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class AdministradorDao {
        Database db;

        public AdministradorDao() { db = Database.instance(); }

        public void create(Administrador a) throws Exception {
            // Primero crear el registro base en empleado
            EmpleadoDao empleadoDao = new EmpleadoDao();
            a.setRol("ADM");
            empleadoDao.create(a);

            // Luego insertar en administrador
            String sql = "INSERT INTO administrador (id, nombre) VALUES (?,?)";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, a.getId());
            stm.setString(2, a.getNombre());

            if (db.executeUpdate(stm) == 0) {
                throw new Exception("Administrador ya existe");
            }
        }

        public Administrador read(String id) throws Exception {
            String sql = "SELECT a.id, a.nombre, e.clave FROM administrador a " +
                         "INNER JOIN empleado e ON a.id = e.id WHERE a.id = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = db.executeQuery(stm);
            if (rs.next()) {
                Administrador a = new Administrador();
                a.setId(rs.getString("id"));
                a.setNombre(rs.getString("nombre"));
                a.setClave(rs.getString("clave"));
                a.setRol("ADM");
                return a;
            } else {
                throw new Exception("Administrador no Existe");
            }
        }

        public void update(Administrador a) throws Exception {
            // Actualizar primero en empleado
            EmpleadoDao empleadoDao = new EmpleadoDao();
            empleadoDao.update(a);

            // Luego actualizar en administrador
            String sql = "UPDATE administrador SET nombre = ? WHERE id = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, a.getNombre());
            stm.setString(2, a.getId());
            int count = db.executeUpdate(stm);
            if (count == 0) {
                throw new Exception("Administrador no existe");
            }
        }

        public void delete(Administrador a) throws Exception {
            // Primero eliminar de administrador
            String sql = "DELETE FROM administrador WHERE id = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, a.getId());
            int count = db.executeUpdate(stm);
            if (count == 0) {
                throw new Exception("Administrador no existe");
            }

            // Luego eliminar de empleado
            EmpleadoDao empleadoDao = new EmpleadoDao();
            empleadoDao.delete(a);
        }

        public List<Administrador> findByNombre(Administrador filtro) throws Exception {
            List<Administrador> ad = new ArrayList<>();
            String sql = "SELECT a.id, a.nombre, e.clave FROM administrador a " +
                         "INNER JOIN empleado e ON a.id = e.id WHERE a.nombre LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + filtro.getNombre() + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Administrador a = new Administrador();
                a.setId(rs.getString("id"));
                a.setNombre(rs.getString("nombre"));
                a.setClave(rs.getString("clave"));
                a.setRol("ADM");
                ad.add(a);
            }
            return ad;
        }

        public List<Administrador> findByID(Administrador filtro) throws Exception {
            List<Administrador> ad = new ArrayList<>();
            String sql = "SELECT a.id, a.nombre, e.clave FROM administrador a " +
                         "INNER JOIN empleado e ON a.id = e.id WHERE a.id = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, filtro.getId());
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Administrador a = new Administrador();
                a.setId(rs.getString("id"));
                a.setNombre(rs.getString("nombre"));
                a.setClave(rs.getString("clave"));
                a.setRol("ADM");
                ad.add(a);
            }
            return ad;
        }
    }