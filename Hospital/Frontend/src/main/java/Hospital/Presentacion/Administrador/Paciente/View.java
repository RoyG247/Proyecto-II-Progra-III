package Hospital.Presentacion.Administrador.Paciente;

import Hospital.Logic.Pacientes;
import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTextField textIdPaciente;
    private JTextField textNombrePaciente;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTextField textNombreBuscar;
    private JButton buscarButton;
    private JTable personas;
    private JTextField textNumeroTelefonico;
    private DatePicker fechaNacimiento;
    private JComboBox comboBoxIdNombre;
    private JPanel transparentPane;
    private JPanel controlPane;
    private JPanel tablePane;
    private JLabel iconLbl;
    private Image backgroundImage;

    public View() {
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/pacienteIco.png"));
        backgroundImage = backgroundIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        iconLbl.setIcon(new ImageIcon(backgroundImage));
        controlPane.setBorder(new javax.swing.border.AbstractBorder() {
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
        tablePane.setBorder(new javax.swing.border.AbstractBorder() {
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

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Pacientes p = take();
                    try {
                        controller.create(p);
                        JOptionPane.showMessageDialog(null, "Paciente registrado",
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        personas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = personas.getSelectedRow();
                controller.edit(row);
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
                textIdPaciente.setEnabled(true);
                controller.switchMode();

                JOptionPane.showMessageDialog(panel, "Pantalla Limpia", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Pacientes n = take();
                    try {
                        controller.deletePaciente(n);
                        model.setList(controller.getPacientes());
                        controller.switchMode();
                        JOptionPane.showMessageDialog(panel, "Paciente Eliminado", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxIdNombre.getSelectedItem() == "Nombre"){
                    controller.searchPaciente(textNombreBuscar.getText());
                } else if (comboBoxIdNombre.getSelectedItem() == "ID") {
                    controller.searchPacienteId(textNombreBuscar.getText());
                }
            }
        });
        personas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = personas.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) personas.getValueAt(fila, 0);
                    try {
                        controller.read(id);
                        textIdPaciente.setEnabled(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

    }

    public JPanel getPanel() {
        return panel;
    }

    // -------- MVC ---------
    private Controller controller;
    private Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.controller != null) {
            this.controller.cargarPacientes();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.NUMEROTELEFONICO, TableModel.FECHADENACIMIENTO};
                personas.setModel(new TableModel(cols, model.getList()));
            case Model.CURRENT:
                textIdPaciente.setText(model.getCurrent().getId());
                textNombrePaciente.setText(model.getCurrent().getNombre());
                textNumeroTelefonico.setText(model.getCurrent().getNumeroTelefono());
                fechaNacimiento.setDate(model.getCurrent().getFechaNacimiento());
                break;
        }
    }

    // -------- Helpers ---------
    public boolean validate() {
        if (textIdPaciente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El id es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textNombrePaciente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El nombre es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Pacientes take() {
        Pacientes p = new Pacientes("","","",null);
        p.setId(textIdPaciente.getText().trim());
        p.setNombre(textNombrePaciente.getText().trim());
        p.setNumeroTelefono(textNumeroTelefonico.getText().trim());
        p.setFechaNacimiento(fechaNacimiento.getDate() == null ? null : fechaNacimiento.getDate());
        return p;
    }
}
