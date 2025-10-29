package Hospital.Presentacion.Administrador.Medicamento;

import Hospital.Logic.Medicamentos;
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
    private JTextField textCodigoMedicamento;
    private JTextField textNombreMedicamento;
    private JTextField textPresentacionMedicamento;

    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;

    private JTextField textBuscarNombre;
    private JButton buscarButton;

    private JTable medicamentos;
    private JComboBox comboBoxCodigoNombre;
    private JPanel transparentPane;
    private JPanel controlPane;
    private JPanel tablePane;
    private JLabel iconLbl;
    private Image backgroundImage;


    public View() {
        ImageIcon backgroundIcon = new ImageIcon("resources/medicamentoIco.png");
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
                    Medicamentos n = take();
                    try {
                        controller.create(n);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        medicamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row =  medicamentos.getSelectedRow();
                controller.edit(row);
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
                textCodigoMedicamento.setEnabled(true);
                controller.switchMode();
                JOptionPane.showMessageDialog(panel, "Pantalla Limpia", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Medicamentos n = take();
                    try {
                        controller.deleteMedicamento(n);
                        model.setList(controller.getMedicamentos());
                        controller.switchMode();
                        JOptionPane.showMessageDialog(panel, "Medicamento Eliminado", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxCodigoNombre.getSelectedItem() == "Nombre") {
                    controller.searchMedicamento(textBuscarNombre.getText());
                } else {
                    controller.searchMedicamentoCodigo(textBuscarNombre.getText());
                }
            }
        });

        medicamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = medicamentos.getSelectedRow();
                if (fila >= 0) {
                    String codigo = (String) medicamentos.getValueAt(fila, 0);
                    try {
                        controller.read(codigo);
                        textCodigoMedicamento.setEnabled(false);
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

    // -------- Setters MVC --------
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        if (this.controller != null) {
            this.controller.cargarMedicamentos();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST: {
                int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.PRESENTACION};
                medicamentos.setModel(new TableModel(cols, model.getList()));
                break;
            }
            case Model.CURRENT: {
                textCodigoMedicamento.setText(model.getCurrent().getCodigo());
                textNombreMedicamento.setText(model.getCurrent().getNombre());
                textPresentacionMedicamento.setText(model.getCurrent().getPresentacion());
                break;
            }
        }
        if (panel != null) panel.revalidate();
    }

    // -------- Helpers --------
    public Medicamentos take() {
        Medicamentos m = new Medicamentos("", "", "");
        m.setCodigo(textCodigoMedicamento.getText());
        m.setNombre(textNombreMedicamento.getText());
        m.setPresentacion(textPresentacionMedicamento.getText());
        return m;
    }

    public boolean validate() {
        if (textCodigoMedicamento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El código es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textNombreMedicamento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textPresentacionMedicamento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "La presentación es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
