package Hospital.Presentacion.login;
import Hospital.Logic.Empleado;
import Hospital.Presentacion.login.CambiarClave.CambiarClave;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog  {
    private JPanel contentPane;
    private JPanel Login;
    private JLabel loginIco;
    private JPanel generalPane;
    private JPanel allPane;
    private JButton cancelarBtt;
    private JButton IniciarSBtt;
    private JPasswordField passField;
    private JTextField idTextField;
    private JLabel bklbl;
    private JButton cambiarClaveBtt;
    private JButton buttonOK;
    private Image backgroundImage;
    CambiarClave cambiarClave = new CambiarClave();


    public Login() {
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/Hospital-Medico.png"));
        backgroundImage = backgroundIcon.getImage().getScaledInstance(250, 500, Image.SCALE_SMOOTH);
        bklbl.setIcon(new ImageIcon(backgroundImage));

        ImageIcon icon = new ImageIcon(getClass().getResource("/loginIco2.png"));
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        loginIco.setIcon(new ImageIcon(img));

        Login.setOpaque(false);
        Login.setBorder(new javax.swing.border.AbstractBorder() {
            final int arc = 24;          // radio esquinas
            final int stroke = 1;        // grosor del borde
            final Color fill  = new Color(215,239,250,180); // fondo translúcido
            final Color line  = new Color(215,239,250,90);  // color del borde
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

        IniciarSBtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLogin()) {
                    Empleado u = take();
                    try {
                        controller.login(u);
                        if (model.isFirstLogin()) {
                            cambiarClave.setController(controller);
                            cambiarClave.setModel(model);
                            cambiarClave.setTitle("Cambiar Clave");
                            cambiarClave.setResizable(false);
                            cambiarClave.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            cambiarClave.setLocationRelativeTo(null);
                            cambiarClave.pack();
                            cambiarClave.setVisible(true);
                        }
                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        cancelarBtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cambiarClaveBtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateLogin()) {
                    Empleado u = take();
                    try {
                        controller.login(u);
                            cambiarClave.setController(controller);
                            cambiarClave.setModel(model);
                            cambiarClave.setTitle("Cambiar Clave");
                            cambiarClave.setResizable(false);
                            cambiarClave.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            cambiarClave.setLocationRelativeTo(null);
                            cambiarClave.pack();
                            cambiarClave.setVisible(true);

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

    public boolean validateLogin() {
        if (controller == null) {
            JOptionPane.showMessageDialog(contentPane, "Error de configuración del sistema",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (idTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "El id es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (passField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(contentPane, "La clave es obligatoria",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Empleado take() {
        Empleado e = new Empleado();
        e.setId(idTextField.getText());
        e.setClave(new String(passField.getPassword()));
        return e;
    }

}
