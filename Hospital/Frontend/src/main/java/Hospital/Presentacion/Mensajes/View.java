package Hospital.Presentacion.Mensajes;

import Hospital.Logic.Empleado;

import javax.swing.*;
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
            default:
                // Ignorar eventos no manejados
                break;
        }
    }
}
