package Hospital.Presentacion.Administrador.Farmaceuta;

import Hospital.Logic.Farmaceuta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JTextField textIdFarmaceuta;
    private JTextField textNombreFarmaceuta;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTextField textNombreBuscarFarmaceuta;
    private JButton buscarButton;
    private JTable farmaceutas;
    private JPanel panel;
    private JComboBox comboBoxIdNombre;
    private JPanel controlPane;
    private JPanel tablePane;
    private JPanel transparentPane;
    private JLabel iconLbl;
    private Image backgroundImage;

    public View() {
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/farIco.png"));
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
                    Farmaceuta n = take();
                    try {
                        controller.create(n);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        farmaceutas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = farmaceutas.getSelectedRow();
                controller.edit(row);
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
                textIdFarmaceuta.setEnabled(true);
                controller.switchMode();
                JOptionPane.showMessageDialog(panel, "Pantalla Limpia", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Farmaceuta n = take();
                    try {
                        controller.deleteFarmaceuta(n);
                        model.setList(controller.getFarmaceutas());
                        textIdFarmaceuta.setEnabled(true);
                        controller.switchMode();
                        JOptionPane.showMessageDialog(panel, "Farmaceuta Eliminado", "", JOptionPane.INFORMATION_MESSAGE);
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
                    controller.searchFarmaceuta(textNombreBuscarFarmaceuta.getText());
                } else {
                    controller.searchFarmaceutaById(textNombreBuscarFarmaceuta.getText());
                }
            }
        });


        farmaceutas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = farmaceutas.getSelectedRow();
                if (fila >= 0) {
                    String id = (String) farmaceutas.getValueAt(fila, 0);
                    try {
                        controller.read(id);
                        textIdFarmaceuta.setEnabled(false);
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
            this.controller.cargarFarmaceutas();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.ID, TableModel.NOMBRE};
                farmaceutas.setModel(new TableModel(cols, model.getList()));
            case Model.CURRENT:
                textIdFarmaceuta.setText(model.getCurrent().getId());
                textNombreFarmaceuta.setText(model.getCurrent().getNombre());
                break;
        }
        this.panel.revalidate();
    }

    public Farmaceuta take() {
        Farmaceuta m = new Farmaceuta();
        m.setId(textIdFarmaceuta.getText());
        m.setNombre(textNombreFarmaceuta.getText());
        m.setClave(textIdFarmaceuta.getText());
        m.setRol("FAR");
        return m;
    }

    public boolean validate() {
        if (textIdFarmaceuta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El id es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textNombreFarmaceuta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "El nombre es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
