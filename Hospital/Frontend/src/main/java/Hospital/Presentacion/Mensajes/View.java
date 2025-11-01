package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel main;
    private JPanel main1;
    private JButton btnEnviar;
    private JButton btnRecibir;
    private JTable table1;
    private JTextField prueba;

    public View() {

    }
    public JPanel getPanel() {
        return main1;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.controller != null) {
            controller.cargarEmpleados();
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.USER_ONLINE:
                Empleado usuario = (Empleado) evt.getNewValue();
                if (usuario != null) {
                    prueba.setText("Usuario en línea: " + usuario.getNombre());
                } else {
                    break;
                }
                break;
            case Model.USERS:
                int[] cols = {TableModel.ID, TableModel.BUTTON};
                table1.setModel(new TableModel(cols, model.getUsers()) {
                    @Override
                    public Class<?> getColumnClass(int columnIndex) {
                        if (columnIndex == 1) return Boolean.class;
                        return super.getColumnClass(columnIndex);
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column == 1;
                    }
                });

                table1.setRowHeight(24);
                break;
            default:
                // Ignorar eventos no manejados
                break;
        }
    }
}
