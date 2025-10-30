package Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;

import Hospital.Logic.Medicamentos;
import Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion.TableModel;
import Hospital.Presentacion.Medicos.Prescripcion.Controller;
import Hospital.Presentacion.Medicos.Prescripcion.Model;
import Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion.editPrescripcion.view;

public class View extends JDialog implements java.beans.PropertyChangeListener {
    view viewEditPrescripcion = new view();
    private JPanel contentPane;
    private JButton buttonAceptar;
    private JButton buttonCancelar;
    private JComboBox comboBoxIdNombre;
    private JTextField textNombreBuscar;
    private JButton buscarButton;
    private JTable tableMedicamentos;
    private JButton buttonOK;
    private JButton buttonCancel;

    public View() {
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
                    controller.searchMedicamentoNombre(textNombreBuscar.getText());
                } else if  (comboBoxIdNombre.getSelectedItem() == "Codigo"){
                    controller.searchMedicamento(textNombreBuscar.getText());
                }
            }
        });
        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCurrentPrescripcion(null);
                dispose();
            }
        });
        tableMedicamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tableMedicamentos.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) tableMedicamentos.getValueAt(fila, 0);
                    if (controller.medicamentoExists(id) == false) {
                        try {
                            controller.readMedicamentos(id);
                            if (model.getCurrentPrescripcion().getMedicamento() != null) {
                                viewEditPrescripcion.setController(controller);
                                viewEditPrescripcion.setModel(model);
                                viewEditPrescripcion.setTitle(model.getCurrentPrescripcion().getMedicamento().getNombre());
                                viewEditPrescripcion.setResizable(false);
                                viewEditPrescripcion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                                viewEditPrescripcion.setLocationRelativeTo(null);
                                viewEditPrescripcion.pack();
                                viewEditPrescripcion.loadData();
                                viewEditPrescripcion.setVisible(true);
                                if (model.getCurrentPrescripcion() != null && model.getCurrentPrescripcion().isModificado()) {
                                    dispose();
                                }
                            } else {
                                throw new Exception("No se ha podido cargar el medicamento");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(tableMedicamentos, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (controller.medicamentoExists(id) == true) {
                        JOptionPane.showMessageDialog(tableMedicamentos, "El medicamento ya ha sido añadido a la prescripción", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

    }

    Model model;
    Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.controller != null) {
            this.controller.cargarMedicamentos();
        }
    }
     public void setController(Controller controller) {
        this.controller = controller;
     }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LISTMEDICAMENTOS:
                int[] cols = {Hospital.Presentacion.Administrador.Medicamento.TableModel.CODIGO, Hospital.Presentacion.Administrador.Medicamento.TableModel.NOMBRE, Hospital.Presentacion.Administrador.Medicamento.TableModel.PRESENTACION};
                tableMedicamentos.setModel(new TableModel(cols, model.getListMedicamentos()));
                break;
        }
    }


}
