package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Empleado;
import Hospital.Presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Empleado> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<Empleado> rows) {super(cols, rows);}

    private final java.util.Map<Integer, Boolean> seleccionados = new java.util.HashMap<>();

    public static final int ID = 0;
    public static final int BUTTON = 1;


    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "Id";
        colNames[BUTTON] = "Mensajes?";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return cols[columnIndex] == BUTTON;
    }

    @Override
    public void setValueAt(Object aValue, Object rowObject, int col) {
        Empleado e = (Empleado) rowObject;
        switch (cols[col]) {
            case BUTTON:
                boolean marcado = (Boolean) aValue;
                seleccionados.put(Integer.valueOf(e.getId()), marcado);
                fireTableCellUpdated(rows.indexOf(e), col);
                break;
        }
    }
    @Override
    protected Object getPropetyAt(Empleado e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case BUTTON:
                return seleccionados.getOrDefault(Integer.valueOf(e.getId()), false);
            default:
                return "";
        }
    }

    public void marcarEmpleado(String idEmpleado) {
        try {
            Integer id = Integer.valueOf(idEmpleado);
            seleccionados.put(id, true);

            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).getId().equals(idEmpleado)) {
                    fireTableCellUpdated(i, getColumnIndex(BUTTON));
                    break;
                }
            }
        } catch (NumberFormatException e) {
            // Ignorar si el ID no es válido
        }
    }
    public void desmarcarEmpleado(String idEmpleado) {
        try {
            Integer id = Integer.valueOf(idEmpleado);
            seleccionados.put(id, false);

            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).getId().equals(idEmpleado)) {
                    fireTableCellUpdated(i, getColumnIndex(BUTTON));
                    break;
                }
            }
        } catch (NumberFormatException e) {
            // Ignorar si el ID no es válido
        }
    }

    private int getColumnIndex(int colType) {
        for (int i = 0; i < cols.length; i++) {
            if (cols[i] == colType) {
                return i;
            }
        }
        return -1;
    }

}