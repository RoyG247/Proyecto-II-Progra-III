package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Empleado;
import Hospital.Logic.Prescripcion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel main;
    private JPanel main1;
    private JButton btnEnviar;
    private JButton btnRecibir;
    private JTable table1;
    private JTextArea prueba;

    public View() {
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row >= 0) {
                    int id = (int) table1.getValueAt(row, 0);
                    try {
                        String msg = prueba.getText();
                        controller.send_message(id ,msg);
                        prueba.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main1, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                JOptionPane.showMessageDialog(main1, "selecione un empleado", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnRecibir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row >= 0) {
                    int id = (int) table1.getValueAt(row, 0);
                    try {
                        JOptionPane.showMessageDialog(main1, controller.message() + " De: " + id, "Información", JOptionPane.INFORMATION_MESSAGE);
                    } catch(Exception ex){
                    JOptionPane.showMessageDialog(main1, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
                JOptionPane.showMessageDialog(main1, "selecione un empleado", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });

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
        if(this.controller != null){
            controller.cargarEmpleados();
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
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
