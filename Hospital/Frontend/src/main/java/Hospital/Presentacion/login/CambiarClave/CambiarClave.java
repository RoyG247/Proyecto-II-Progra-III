package Hospital.Presentacion.login.CambiarClave;

import Hospital.Presentacion.login.Controller;
import Hospital.Presentacion.login.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CambiarClave extends JDialog {
    private JPanel contentPane;
    private JPanel generalPane;
    private JPanel Login;
    private JPanel allPane;
    private JButton CambiarBtt;
    private JPasswordField confirmFld;
    private JLabel IconoLBL;
    private JPasswordField newpassFld;
    private JPanel Apane;

    public CambiarClave() {


        ImageIcon backgroundIcon = new ImageIcon("Resources/clave.png");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        IconoLBL.setIcon(new ImageIcon(backgroundImage));

        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setTitle("Cambiar Clave");
        setLocationRelativeTo(null);
        setResizable(false);


        CambiarBtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validatePss()) {
                    try {
                        controller.cambiarClave(newpassFld.getText(), confirmFld.getText());
                        JOptionPane.showMessageDialog(CambiarClave.this, "Contraseña cambiada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
        model.addPropertyChangeListener(null);
    }

    public boolean validatePss() {
        if (newpassFld.getText().isEmpty() || confirmFld.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
