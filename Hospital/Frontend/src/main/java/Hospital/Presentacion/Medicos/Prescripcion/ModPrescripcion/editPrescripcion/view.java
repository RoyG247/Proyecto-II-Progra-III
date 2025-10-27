package Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion.editPrescripcion;

import Hospital.Logic.Prescripcion;
import Hospital.Presentacion.Medicos.Prescripcion.Controller;
import Hospital.Presentacion.Medicos.Prescripcion.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class view extends JDialog {
    private JPanel contentPane;
    private JButton buttonAceptar;
    private JButton buttonCancelar;
    private JSpinner cantidadSpin;
    private JSpinner duracionSpin;
    private JTextArea indicacionesTxt;
    private JButton buttonOK;

    public view() {
        super((JFrame) null, "");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateEdit()) {
                    Prescripcion p = take();
                    try {
                        controller.update(p);
                        dispose();
                        JOptionPane.showMessageDialog(null, "Prescripcion modificada",
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonCancelar.addActionListener(new ActionListener() {
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
    }
    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    public boolean validateEdit() {
     if (cantidadSpin.getValue().toString().isEmpty())  {
            JOptionPane.showMessageDialog(null, "La dosis no puede estar vacía",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (duracionSpin.getValue().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La frecuencia no puede estar vacía",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (indicacionesTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar indicaciones",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Prescripcion take() {
        Prescripcion p = new Prescripcion();
        p.setCantidad(Integer.parseInt(cantidadSpin.getValue().toString()));
        p.setDuracion(Integer.parseInt(duracionSpin.getValue().toString()));
        p.setIndicaciones(indicacionesTxt.getText());
        return p;
    }

}
