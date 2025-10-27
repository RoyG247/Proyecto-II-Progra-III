package Hospital.Presentacion.EnComun.Historico;

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
    public static final int NUMERO = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[MEDICO]       = "Medico";
        colNames[PACIENTE]       = "Paciente";
        colNames[FECHARETIRO] = "Fecha Retiro";
        colNames[NUMERO]       = "Numero";
    }

    @Override
    protected Object getPropetyAt(Recetas m, int col) {
        switch (cols[col]) {
            case MEDICO:    return (m.getMedico() != null) ? m.getMedico().getNombre() : "Sin médico";
            case PACIENTE:   return m.getPaciente().getNombre();
            case FECHARETIRO: return m.getFechaRetiro();
            case NUMERO:    return m.getId();
            default:           return "";
        }
    }
}

