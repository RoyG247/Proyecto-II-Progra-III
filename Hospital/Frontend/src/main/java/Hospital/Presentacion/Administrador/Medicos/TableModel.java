package Hospital.Presentacion.Administrador.Medicos;

import Hospital.Logic.Medico;
import Hospital.Presentacion.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<Medico> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<Medico> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int ESPECIALIDAD = 2;


    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[ESPECIALIDAD] = "Especialidad";
    }
    @Override
    protected Object getPropetyAt(Medico e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case NOMBRE:
                return e.getNombre();
            case ESPECIALIDAD:
                return e.getEspecialidad();
            default:
                return "";
        }
    }

}
