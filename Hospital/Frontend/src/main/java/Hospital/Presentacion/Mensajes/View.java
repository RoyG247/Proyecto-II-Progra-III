package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Mensaje;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel main;
    private JPanel main1;
    private JButton btnEnviar;
    private JButton btnRecibir;
    private JTable usersTable;
    private JTextArea prueba;
    private JLabel userSelect;

    public View() {
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = usersTable.getSelectedRow();
                if (row >= 0) {
                    String id = (String) usersTable.getValueAt(row, 0);
                    try {
                        Mensaje msg = new Mensaje();
                        msg.setIdReceptor(id);
                        msg.setContenido(prueba.getText());
                        controller.send_message(msg);
                        prueba.setText("");
                        userSelect.setText("Seleccione un usuario");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main1, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                //JOptionPane.showMessageDialog(main1, "Selecione un empleado", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnRecibir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = usersTable.getSelectedRow();
                if (model.getCurrentmessage() == null) {
                    JOptionPane.showMessageDialog(main1, "No tiene mensajes pendientes", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (row >= 0) {
                    String id = (String) usersTable.getValueAt(row, 0);
                    try {
                        JOptionPane.showMessageDialog(main1, model.getCurrentmessage().getContenido() + " De: " + id, "Información", JOptionPane.INFORMATION_MESSAGE);
                        TableModel tableModel = (TableModel) usersTable.getModel();
                        tableModel.desmarcarEmpleado(id);
                        controller.limpiar();
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(main1, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        usersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = usersTable.getSelectedRow();
                if (row >= 0) {
                    String id = (String) usersTable.getValueAt(row, 0);
                    String name = controller.getUserNameById(id);
                    userSelect.setText(name);
                } else {
                    userSelect.setText("");
                }
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
                usersTable.setModel(new TableModel(cols, model.getUsers()) {
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
                usersTable.setRowHeight(24);
                break;
            case Model.MESSAGE:
                Mensaje mensaje = model.getCurrentmessage();
                if (mensaje != null && mensaje.getIdEmisor() != null) {
                    TableModel tableModel = (TableModel) usersTable.getModel();
                    tableModel.marcarEmpleado(mensaje.getIdEmisor());
                }
                break;
            default:
                break;
        }
    }
}
