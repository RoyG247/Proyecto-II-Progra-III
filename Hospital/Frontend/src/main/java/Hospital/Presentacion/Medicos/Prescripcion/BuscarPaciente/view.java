package Hospital.Presentacion.Medicos.Prescripcion.BuscarPaciente;

import Hospital.Presentacion.Medicos.Prescripcion.Controller;
import Hospital.Presentacion.Medicos.Prescripcion.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

public class view extends JDialog implements java.beans.PropertyChangeListener {
    private JPanel contentPane;
    private JButton buttonAceptar;
    private JButton buttonCancelar;
    private JComboBox comboBoxIdNombre;
    private JTextField textNombreBuscar;
    private JButton buscarButton;
    private JTable tablePacientes;


    public view() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonAceptar);
        comboBoxIdNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        textNombreBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxIdNombre.getSelectedItem() == "Nombre"){
                    controller.searchPaciente(textNombreBuscar.getText());
                } else {
                    controller.searchPacienteId(textNombreBuscar.getText());
                }
            }
        });
        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        tablePacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablePacientes.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) tablePacientes.getValueAt(fila, 0);
                    try {
                        controller.readPaciente(id);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(tablePacientes, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        buttonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        if (this.controller != null) {
            this.controller.cargarPacientes();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LISTPACIENTES:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.NUMEROTELEFONICO, TableModel.FECHADENACIMIENTO};
                tablePacientes.setModel(new TableModel(cols, model.getListPacientes()));
                break;
        }
    }
}
