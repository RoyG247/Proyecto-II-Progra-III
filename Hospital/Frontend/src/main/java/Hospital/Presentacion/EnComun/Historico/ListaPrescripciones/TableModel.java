package Hospital.Presentacion.EnComun.Historico.ListaPrescripciones;

import Hospital.Logic.Prescripcion;
import Hospital.Presentacion.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel<Prescripcion> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<Prescripcion> rows) {super(cols, rows);}

    public static final int MEDICAMENTO = 0;
    public static final int PRESENTACION = 1;
    public static final int CANTIDAD = 2;
    public static final int INDICACIONES = 3;
    public static final int DURACION = 4;


    @Override
    protected void initColNames() {
        colNames = new String[5];
        colNames[MEDICAMENTO] = "Medicamento";
        colNames[PRESENTACION] = "Presentación";
        colNames[CANTIDAD] = "Cantidad";
        colNames[INDICACIONES] = "Indicaciones";
        colNames[DURACION] = "Duración";
    }
    @Override
    protected Object getPropetyAt(Prescripcion e, int col) {
        switch (cols[col]) {
            case MEDICAMENTO:
                return e.getMedicamento().getNombre();
            case PRESENTACION:
                return e.getMedicamento().getPresentacion();
            case CANTIDAD:
                return e.getCantidad();
            case INDICACIONES:
                return e.getIndicaciones();
            case DURACION:
                return e.getDuracion();
            default:
                return "";
        }
    }

}

