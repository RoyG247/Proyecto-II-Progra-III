package Hospital.Presentacion.Administrador.Medicamento;

import Hospital.Logic.Medicamentos;
import Hospital.Presentacion.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<Medicamentos> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Medicamentos> rows) {
        super(cols, rows);
    }

    public static final int CODIGO       = 0;
    public static final int NOMBRE       = 1;
    public static final int PRESENTACION = 2;

    @Override
    protected void initColNames() {
        colNames = new String[3];
        colNames[CODIGO]       = "Código";
        colNames[NOMBRE]       = "Nombre";
        colNames[PRESENTACION] = "Presentación";
    }

    @Override
    protected Object getPropetyAt(Medicamentos m, int col) {
        switch (cols[col]) {
            case CODIGO:       return m.getCodigo();
            case NOMBRE:       return m.getNombre();
            case PRESENTACION: return m.getPresentacion();
            default:           return "";
        }
    }
}
