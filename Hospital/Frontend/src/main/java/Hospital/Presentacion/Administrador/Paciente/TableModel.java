package Hospital.Presentacion.Administrador.Paciente;

import Hospital.Logic.Pacientes;
import Hospital.Presentacion.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<Pacientes> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<Pacientes> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int NUMEROTELEFONICO = 2;
    public static final int FECHADENACIMIENTO = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[NUMEROTELEFONICO] = "Numero Telefono";
        colNames[FECHADENACIMIENTO] = "Fecha de nacimiento";
    }
    @Override
    protected Object getPropetyAt(Pacientes e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case NOMBRE:
                return e.getNombre();
            case NUMEROTELEFONICO:
                return e.getNumeroTelefono();
            case FECHADENACIMIENTO:
                return e.getFechaNacimiento();
            default:
                return "";
        }
    }

}
