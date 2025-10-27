package Hospital.Presentacion.Despacho;

import Hospital.Presentacion.AbstractTableModel;
import java.util.List;
import Hospital.Logic.Recetas;

public class TableModel extends AbstractTableModel<Recetas> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Recetas> rows) {
        super(cols, rows);
    }

    public static final int MEDICO = 0;
    public static final int PACIENTE = 1;
    public static final int FECHARETIRO = 2;
    public static final int ESTADO = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[MEDICO]       = "Medico";
        colNames[PACIENTE]       = "Paciente";
        colNames[FECHARETIRO] = "Fecha Retiro";
        colNames[ESTADO] = "Estado";
    }

    @Override
    protected Object getPropetyAt(Recetas m, int col) {
        switch (cols[col]) {
            case MEDICO:    return (m.getMedico() != null) ? m.getMedico().getNombre() : "Sin médico";
            case PACIENTE:   return m.getPaciente().getNombre();
            case FECHARETIRO: return m.getFechaRetiro();
            case ESTADO: return m.getEstado();
            default:           return "";
        }
    }
}

