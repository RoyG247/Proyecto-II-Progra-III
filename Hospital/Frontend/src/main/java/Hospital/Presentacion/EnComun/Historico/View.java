package Hospital.Presentacion.EnComun.Historico;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class View implements PropertyChangeListener {
    private JTable recetas;
    private JLabel textMedico;
    private JLabel textPaciente;
    private JLabel textFechaEmision;
    private JLabel textFechaRetiro;
    private JPanel panel;
    private JTable prescripciones;


    public View() {

        recetas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila =  recetas.getSelectedRow();
                if (fila >= 0) {
                    String nombreMedico = (String)  recetas.getValueAt(fila, 0);
                    String nombrePaciente = (String) recetas.getValueAt(fila, 1);
                    LocalDate fechaRet = (LocalDate) recetas.getValueAt(fila, 2);
                    int id = (int) recetas.getValueAt(fila, 3);
                    try {
                            controller.read(nombreMedico, nombrePaciente, fechaRet, id);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
        });
    }

    public JPanel getPanel() { return panel; }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.controller != null) {
            controller.cargarHistorico();
        }
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols ={TableModel.MEDICO, TableModel.PACIENTE, TableModel.FECHARETIRO, TableModel.NUMERO};
                recetas.setModel(new TableModel(cols, model.getList()));
                break;
            case Model.LISTPRESCRIPCION:
                int[] cols2 = {Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.MEDICAMENTO,
                        Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.PRESENTACION,
                        Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.CANTIDAD,
                        Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.INDICACIONES,
                        Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.DURACION};
                prescripciones.setModel(new Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel(cols2, model.getListPrescripcion()));
                break;
            case Model.CURRENT:
                if (model != null && model.getCurrent() != null) {
                    // Medico
                    if (model.getCurrent().getMedico() != null) {
                        textMedico.setText(model.getCurrent().getMedico().getNombre());
                    } else {
                        textMedico.setText("");
                    }

                    // Paciente
                    if (model.getCurrent().getPaciente() != null) {
                        textPaciente.setText(model.getCurrent().getPaciente().getNombre());
                    } else {
                        textPaciente.setText("");
                    }

                    // Fechas
                    if (model.getCurrent().getFechadeEmision() != null) {
                        textFechaEmision.setText(model.getCurrent().getFechadeEmision().toString());
                    } else {
                        textFechaEmision.setText("");
                    }

                    if (model.getCurrent().getFechaRetiro() != null) {
                        textFechaRetiro.setText(model.getCurrent().getFechaRetiro().toString());
                    } else {
                        textFechaRetiro.setText("");
                    }
                    if (model.getCurrent().getPrescripciones() != null &&
                            !model.getCurrent().getPrescripciones().isEmpty()) {
                        int[] cols3 = {
                                Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.MEDICAMENTO,
                                Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.PRESENTACION,
                                Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.CANTIDAD,
                                Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.INDICACIONES,
                                Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel.DURACION
                        };
                        prescripciones.setModel(new Hospital.Presentacion.EnComun.Historico.ListaPrescripciones.TableModel(
                                cols3, model.getCurrent().getPrescripciones()));
                    }
                } else {
                    // Si model o current es null
                    textMedico.setText("");
                    textPaciente.setText("");
                    textFechaEmision.setText("");
                    textFechaRetiro.setText("");
                }
                break;
            default:
                break;
        }
        panel.revalidate();
    }

    public void actualizarTablaRecetas() {
        int[] cols ={TableModel.MEDICO, TableModel.PACIENTE, TableModel.FECHARETIRO, TableModel.NUMERO};
        recetas.setModel(new TableModel(cols, model.getList()));
        panel.revalidate();
    }
}
