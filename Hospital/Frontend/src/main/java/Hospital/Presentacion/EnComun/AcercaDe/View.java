package Hospital.Presentacion.EnComun.AcercaDe;

import javax.swing.*;
import java.awt.*;

public class View {
    private JPanel generalPane;
    private JLabel bckLbl;
    private Image backgroundImage;

    public View() {
        ImageIcon backgroundIcon = new ImageIcon("resources/fondoHospital.png");
        backgroundImage = backgroundIcon.getImage().getScaledInstance(900, 400, Image.SCALE_SMOOTH);
        bckLbl.setIcon(new ImageIcon(backgroundImage));
    }

    public JPanel getPanel() {return generalPane;}
}
