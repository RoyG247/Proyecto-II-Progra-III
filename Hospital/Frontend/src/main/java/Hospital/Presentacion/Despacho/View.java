package Hospital.Presentacion.Despacho;

import Hospital.Logic.Pacientes;
import Hospital.Logic.Recetas;
import Hospital.Logic.Service;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class View implements PropertyChangeListener {
    private JTable Recetas;
    private JPanel Despacho;
    private JButton continuarButton;
    private JTextField textIDPaciente;
    private JButton enviarButton;
    private JPanel panel;

    public View(){
        Despacho.setVisible(false);
        continuarButton.addActionListener(e -> {
            int fila =  Recetas.getSelectedRow();
            if (fila >= 0) {
                Recetas r = model.getRecetas().get(fila);
                 try {
                    controller.avanzarEstado(r);
                    actualizarRecetasPorPersona(r);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        enviarButton.addActionListener(e -> {
            String filtroPaciente = textIDPaciente.getText();
            Recetas r = new Recetas(null, null, null);
            Pacientes p= new Pacientes();
            p.setId(filtroPaciente);
            r.setPaciente(p);
            if(filtroPaciente.isEmpty()){
                Despacho.setVisible(false);
            }else if (controller != null) {
                controller.filtrarRecetas(r);
                Despacho.setVisible(true);
            }
        });
    }

    public JPanel getPanel() { return panel; }

    //-------- MVC ---------
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
            case Model.RECETAS:
                int[] cols ={TableModel.MEDICO, TableModel.PACIENTE, TableModel.FECHARETIRO, TableModel.ESTADO};
                Recetas.setModel(new TableModel(cols, model.getRecetas()));
                continuarButton.setVisible(true);
                panel.revalidate();
                break;
            default:
        }
    }

    public void actualizarTablaRecetas() {
        int[] cols ={TableModel.MEDICO, TableModel.PACIENTE, TableModel.FECHARETIRO, TableModel.ESTADO};
        Recetas.setModel(new TableModel(cols, model.getRecetas()));
        Despacho.revalidate();
    }

    public void actualizarRecetasPorPersona(Recetas recetas) {
        int[] cols ={TableModel.MEDICO, TableModel.PACIENTE, TableModel.FECHARETIRO, TableModel.ESTADO};
        Recetas.setModel(new TableModel(cols, Service.instance().recetasPorPaciente(recetas)));
        Despacho.revalidate();
    }
}
