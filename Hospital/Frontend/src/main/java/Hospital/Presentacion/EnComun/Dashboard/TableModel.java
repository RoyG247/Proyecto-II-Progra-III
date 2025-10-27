package Hospital.Presentacion.EnComun.Dashboard;

import Hospital.Presentacion.AbstractTableModel;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel<TableModel.Fila> implements javax.swing.table.TableModel {

    private List<String> meses; // columnas dinámicas
    public static final int MEDICAMENTO = 0;

    public TableModel(DefaultCategoryDataset dataset) {
        super(null, convertirDataset(dataset));

        // Initialize meses using the same dataset
        this.meses = extractMeses(dataset);

        // Initialize cols array
        cols = new int[1 + meses.size()];
        cols[0] = MEDICAMENTO;
        for (int i = 0; i < meses.size(); i++) {
            cols[i + 1] = i + 1;
        }

        // Call initColNames() again now that meses is properly initialized
        initColNames();
    }

    private static List<String> extractMeses(DefaultCategoryDataset dataset) {
        List<String> meses = new ArrayList<>();
        for (int col = 0; col < dataset.getColumnCount(); col++) {
            meses.add((String) dataset.getColumnKey(col));
        }
        return meses;
    }

    public static class Fila {
        private String medicamento;
        private List<Number> cantidades;

        public Fila(String medicamento, int mesesCount) {
            this.medicamento = medicamento;
            this.cantidades = new ArrayList<>();
            for (int i = 0; i < mesesCount; i++) {
                cantidades.add(0);
            }
        }


        public String getMedicamento() { return medicamento; }
        public Number getCantidad(int index) { return cantidades.get(index); }
        public void setCantidad(int index, Number value) { cantidades.set(index, value); }
    }

    private static List<Fila> convertirDataset(DefaultCategoryDataset dataset) {
        List<Fila> filas = new ArrayList<>();

        int mesesCount = dataset.getColumnCount();

        for (int row = 0; row < dataset.getRowCount(); row++) {
            String medicamento = (String) dataset.getRowKey(row);
            Fila fila = new Fila(medicamento, mesesCount);

            for (int col = 0; col < dataset.getColumnCount(); col++) {
                Number cantidad = dataset.getValue(row, col);
                if (cantidad != null) {
                    fila.setCantidad(col, cantidad);
                }
            }

            filas.add(fila);
        }

        return filas;
    }

    @Override
    protected void initColNames() {
        // Guard against null meses (called from super constructor)
        if (meses == null) {
            colNames = new String[0]; // Temporary empty array
            return;
        }

        colNames = new String[1 + meses.size()];
        colNames[MEDICAMENTO] = "Medicamento";
        for (int i = 0; i < meses.size(); i++) {
            colNames[i + 1] = meses.get(i);
        }
    }

    @Override
    protected Object getPropetyAt(Fila fila, int col) {
        if (col == MEDICAMENTO) {
            return fila.getMedicamento();
        } else {
            return fila.getCantidad(col - 1);
        }
    }
}

