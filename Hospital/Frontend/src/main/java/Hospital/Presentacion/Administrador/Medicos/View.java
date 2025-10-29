package Hospital.Presentacion.Administrador.Medicos;

import Hospital.Logic.Medico;
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
    private JTextField textIdMedico;
    private JLabel idMedico;
    private JLabel nombreMedico;
    private JTextField textNombreMedico;
    private JButton guardarMedico;
    private JButton limpiarMedico;
    private JButton borrarMedico;
    private JTextField textNombreBuscar;
    private JButton buscarMedico;
    private JTextField textEspecialidadMedico;
    private JLabel especialidadMedico;
    private JTable medicos;
    private JComboBox comboBoxIdNombre;
    private JPanel transparentPane;
    private JPanel controlPane;
    private JPanel tablePane;
    private JLabel iconLbl;
    private Image backgroundImage;


    public View() {
        ImageIcon backgroundIcon = new ImageIcon("resources/medicoIco.png");
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

        guardarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Medico n = take();
                    try {
                        controller.create(n);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        medicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = medicos.getSelectedRow();
                controller.edit(row);
            }
        });

        limpiarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
                textIdMedico.setEnabled(true);
                controller.switchMode();
                JOptionPane.showMessageDialog(panel, "Pantalla Limpia", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        borrarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Medico n = take();
                    try {
                        controller.deleteMedico(n);
                        model.setList(controller.getMedicos());
                        controller.switchMode();
                        JOptionPane.showMessageDialog(panel, "Medico Eliminado", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buscarMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxIdNombre.getSelectedItem() == "Nombre"){
                    controller.searchMedico(textNombreBuscar.getText());
                } else {
                    controller.searchMedicoId(textNombreBuscar.getText());
                }
            }
        });


        medicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = medicos.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) medicos.getValueAt(fila, 0);
                    try {
                        controller.read(id);
                        textIdMedico.setEnabled(false);
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
            this.controller.cargarMedicos();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.ESPECIALIDAD};
                medicos.setModel(new TableModel(cols, model.getList()));
            case Model.CURRENT:
                textIdMedico.setText(model.getCurrent().getId());
                textNombreMedico.setText(model.getCurrent().getNombre());
                textEspecialidadMedico.setText(model.getCurrent().getEspecialidad());
                break;
        }
        this.panel.revalidate();
    }

    public Medico take() {
        Medico m = new Medico();
        m.setId(textIdMedico.getText());
        m.setNombre(textNombreMedico.getText());
        m.setEspecialidad(textEspecialidadMedico.getText());
        m.setClave(textIdMedico.getText());// La clave inicial es el mismo id
        m.setRol("MED");
        return m;
    }

    public boolean validate() {
        if (textIdMedico.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El id es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textNombreMedico.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El nombre es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
