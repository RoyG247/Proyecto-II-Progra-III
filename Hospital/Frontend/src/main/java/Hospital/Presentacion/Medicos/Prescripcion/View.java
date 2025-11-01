package Hospital.Presentacion.Medicos.Prescripcion;

import Hospital.Logic.Prescripcion;
import Hospital.Logic.Recetas;
import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import Hospital.Presentacion.Medicos.Prescripcion.BuscarPaciente.*;

import java.time.LocalDate;


public class View implements PropertyChangeListener {
    private Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion.View viewModPrescripcion = new Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion.View();
    private Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion.editPrescripcion.view viewEditPrescripcion = new Hospital.Presentacion.Medicos.Prescripcion.ModPrescripcion.editPrescripcion.view();
    private JPanel GeneralPane;
    private DatePicker fechaRetiro;
    private JTable prescripciones;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private JButton detallesButton;
    private JButton buscarPaciente;
    private JButton agregarMedicamentoButton;
    private JPanel controlPane;
    private JPanel RecetaPane;
    private JPanel notOpaquePane;
    private JPanel ajustarPane;
    private JLabel pacienteLbl;
    private JLabel iconLbl;
    private JLabel userLbl;
    private JLabel codeLbl;
    private JPanel recetaMedPane;
    private JButton Modificar_Prescripcion;
    private Image backgroundImage;
    view viewBuscarPaciente = new view();
    //Implentar el boton para mostrar la vista de ModPrescripcion
    //Tambien en el setController y setModel hay que pasarle el controller y model a ModPrescripcion

    public View(){
        fechaRetiro.setDate(LocalDate.now());
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/receta.png"));        backgroundImage = backgroundIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        iconLbl.setIcon(new ImageIcon(backgroundImage));
        controlPane.setOpaque(false);
        RecetaPane.setOpaque(false);
        ajustarPane.setOpaque(false);
        recetaMedPane.setOpaque(false);
        RecetaPane.setBorder(new javax.swing.border.AbstractBorder() {
            final int arc = 24;          // radio esquinas
            final int stroke = 1;        // grosor del borde
            final Color fill  = new Color(255,255,255,180); // fondo translúcido
            final Color line  = new Color(255,255,255,180);  // color del borde
            final String title = "Iniciar Sesión";         // título

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Relleno redondeado
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setColor(fill);
                g2.fill(new java.awt.geom.RoundRectangle2D.Double(x, y, w-1, h-1, arc, arc));

                // Trazo "dentro" del área para que no se “salga” en las esquinas
                g2.setStroke(new BasicStroke(stroke));
                g2.setColor(line);
                double pad = stroke / 2.0;
                g2.draw(new java.awt.geom.RoundRectangle2D.Double(x+pad, y+pad, w-1-2*pad, h-1-2*pad, arc, arc));
                g2.dispose();
            }

            @Override public Insets getBorderInsets(Component c) { return new Insets(0,0,0,0); }
            @Override public Insets getBorderInsets(Component c, Insets insets) {
                insets.left = insets.top = insets.right = insets.bottom = 0; return insets;
            }
        });



        buscarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Llamar al controlador para que abra la vista de buscar paciente
                 viewBuscarPaciente.setController(controller);
                 viewBuscarPaciente.setModel(model);
                 viewBuscarPaciente.setLocationRelativeTo(null);
                 viewBuscarPaciente.setResizable(false);
                 viewBuscarPaciente.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                 viewBuscarPaciente.pack();
                 viewBuscarPaciente.setVisible(true);
                 if (model.getCurrent().getPaciente() != null) {
                     setPacienteNombre(model.getCurrent().getPaciente().getNombre());
                 }
            }
        });
        agregarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prescripcion newP = new Prescripcion();
                model.setCurrentPrescripcion(newP);
                viewModPrescripcion.setController(controller);
                viewModPrescripcion.setModel(model);
                viewModPrescripcion.setResizable(false);
                viewModPrescripcion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                viewModPrescripcion.setLocationRelativeTo(null);
                viewModPrescripcion.pack();
                viewModPrescripcion.setVisible(true);
                if (model.getCurrentPrescripcion() != null && model.getCurrentPrescripcion().getMedicamento() != null) {
                    controller.addPrescripcion(model.getCurrentPrescripcion());
                }
            }
        });
        descartarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = prescripciones.getSelectedRow();
                if (row >= 0) {
                    String medicamentoNombre = (String) prescripciones.getValueAt(row, TableModel.MEDICAMENTO);
                    try {
                        controller.removePrescripcion(medicamentoNombre);
                        actualizarTablaPrescripciones();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GeneralPane, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                    }
                     else if(row < 0){
                    JOptionPane.showMessageDialog(GeneralPane, "Seleccione una medicamento para descartar.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               controller.clear();
                setPacienteNombre("Paciente no seleccionado");
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    try {
                        Recetas r = take();
                        controller.create(r);
                        JOptionPane.showMessageDialog(GeneralPane, "Receta creada con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
                        controller.clear();
                        fechaRetiro.setDate(LocalDate.now());
                        setPacienteNombre("Paciente no seleccionado");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(GeneralPane, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        Modificar_Prescripcion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = prescripciones.getSelectedRow();
                if (row >= 0) {
                    String medicamentoNombre = (String) prescripciones.getValueAt(row, TableModel.MEDICAMENTO);
                    Prescripcion p = controller.getPrescripcionByNombre(medicamentoNombre);
                    if (p != null) {
                        model.setCurrentPrescripcion(p);
                        viewEditPrescripcion.setController(controller);
                        viewEditPrescripcion.setModel(model);
                        viewEditPrescripcion.setResizable(false);
                        viewEditPrescripcion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        viewEditPrescripcion.setLocationRelativeTo(null);
                        viewEditPrescripcion.pack();
                        viewEditPrescripcion.loadData();
                        viewEditPrescripcion.setVisible(true);
                        if (model.getCurrentPrescripcion() != null) {
                            try {
                                controller.updatePrescripcion(model.getCurrentPrescripcion());
                                actualizarTablaPrescripciones();
                            }catch (Exception ex) {
                                JOptionPane.showMessageDialog(GeneralPane, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(GeneralPane, "Seleccione una prescripción para modificar.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public JPanel getPanel() {
        return GeneralPane;
    }

    public void setPacienteNombre(String nombre) {
        pacienteLbl.setText(nombre);
    }

    //-------- MVC ---------
    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.controller != null) {
            this.controller.cargarPacientes();
            this.controller.setMedico();
        }
    }
    public void setDatosUser(String user, String code) {
        userLbl.setText("Usuario: " + user);
        codeLbl.setText("Identificación: " + code);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LISTPRESCRIPCIONES:
                int[] cols = {TableModel.MEDICAMENTO, TableModel.PRESENTACION, TableModel.CANTIDAD, TableModel.INDICACIONES, TableModel.DURACION};
                prescripciones.setModel(new TableModel(cols, model.getListPrescripciones()));
        }
        this.GeneralPane.revalidate();
    }

    public void actualizarTablaPrescripciones() {
        int[] cols = {TableModel.MEDICAMENTO, TableModel.PRESENTACION, TableModel.CANTIDAD, TableModel.INDICACIONES, TableModel.DURACION};
        prescripciones.setModel(new TableModel(cols, model.getListPrescripciones()));
        GeneralPane.revalidate();
    }

    private boolean validateFields() {
        if (model.getCurrent().getPaciente() == null) {
            JOptionPane.showMessageDialog(GeneralPane, "Seleccione un paciente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (model.getListPrescripciones().isEmpty()) {
            JOptionPane.showMessageDialog(GeneralPane, "Agregue al menos un medicamento a la prescripción.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (fechaRetiro.getDate() == null) {
            JOptionPane.showMessageDialog(GeneralPane, "Seleccione una fecha de retiro.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (fechaRetiro.getDate().isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(GeneralPane, "La fecha de retiro no puede ser anterior a la fecha actual.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
         model.getCurrent().setFechaRetiro(fechaRetiro.getDate());
        return true;
    }

    private Recetas take() {
        Recetas r = new Recetas(null, null, null);
        r.setMedico(model.getCurrent().getMedico());
        r.setFechadeEmision(LocalDate.now());
        r.setFechaRetiro(fechaRetiro.getDate());
        r.setPaciente(model.getCurrent().getPaciente());
        r.setPrescripciones(model.getListPrescripciones());
        model.setCurrent(r);
        return r;
    }


}
