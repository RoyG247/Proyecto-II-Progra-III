package Hospital.Presentacion.EnComun.Dashboard;

import Hospital.Logic.Medicamentos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Set;

public class View extends JFrame implements java.beans.PropertyChangeListener {
    private JPanel graficoLineas;
    private JPanel graficoPie;
    private JPanel contentPane;
    private JPanel prueba;
    private JComboBox anioDComB;
    private JComboBox mesDComB;
    private JComboBox anioHComB;
    private JComboBox mesHComB;
    private JComboBox medicaComB;
    private JButton filtrarChartsBtt;
    private JPanel controlPane;
    private JPanel subCPane;
    private JTable medicamentosTbl;
    private JScrollPane scrollTbl;

    public View(){
        setTitle("Dashboard de Medicamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graficoLineas = new JPanel();
        contentPane = new JPanel();
        graficoPie = new JPanel();
        contentPane.setBackground(new Color(215,239,250));
        subCPane.setBorder(new javax.swing.border.AbstractBorder() {
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
        contentPane.add(graficoLineas);
        contentPane.add(graficoPie);
        contentPane.add(scrollTbl);
        contentPane.add(controlPane);


        // Configurar el ActionListener del botón para llamar al controller
        filtrarChartsBtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.filtrarGrafico();
                }
            }
        });
    }

    public JPanel getGrafico() {
        return contentPane;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    // Métodos getter para que el controller pueda acceder a los comboboxes
    public JComboBox getAnioDComB() {
        return anioDComB;
    }

    public JComboBox getMesDComB() {
        return mesDComB;
    }

    public JComboBox getAnioHComB() {
        return anioHComB;
    }

    public JComboBox getMesHComB() {
        return mesHComB;
    }

    public JComboBox getMedicaComB() {
        return medicaComB;
    }

    public void mostrarGrafico(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Cantidad de recetas del mes",
                "Fecha",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultShapesVisible(true);
        ChartPanel chartPanel = new ChartPanel(chart,
                400,
                300,
                200,
                150,
                800,
                600,
                true,
                true,
                true,
                true,
                true,
                true);

        graficoLineas.removeAll();
        graficoLineas.add(chartPanel);
        graficoLineas.revalidate();  // Agregar para refrescar la vista
        graficoLineas.repaint();     // Agregar para refrescar la vista
    }

    // Nuevo método para mostrar el pie chart
    public void mostrarPieChart(DefaultPieDataset dataset) {
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Estado de las Recetas",
                dataset,
                true,
                true,
                false
        );

        ChartPanel pieChartPanel = new ChartPanel(pieChart,
                400,
                300,
                200,
                150,
                800,
                600,
                true,
                true,
                true,
                true,
                true,
                true);

        graficoPie.removeAll();
        graficoPie.add(pieChartPanel);
        graficoPie.revalidate();
        graficoPie.repaint();
    }

    public void mostrarAniosEnCombo(Set<Integer> anios) {
        anioDComB.removeAllItems();
        anioHComB.removeAllItems();
        for (Integer anio : anios) {
            anioDComB.addItem(anio);
            anioHComB.addItem(anio);
        }

        // Seleccionar por defecto el primer y último año si hay datos
        if (!anios.isEmpty()) {
            anioDComB.setSelectedIndex(0); // Primer año
            anioHComB.setSelectedIndex(anios.size() - 1); // Último año
        }
    }

    public void mostrarMesesEnCombo(Set<String> meses) {
        mesDComB.removeAllItems();
        mesHComB.removeAllItems();
        for (String mes : meses) {
            mesDComB.addItem(mes);
            mesHComB.addItem(mes);
        }

        // Seleccionar por defecto el primer y último mes si hay datos
        if (!meses.isEmpty()) {
            mesDComB.setSelectedIndex(0); // Primer mes
            mesHComB.setSelectedIndex(meses.size() - 1); // Último mes
        }
    }

    public void mostrarMedicamentosEnCombo(List<Medicamentos> medicamentos) {
        medicaComB.removeAllItems();

        // Agregar opción "Todos" para mostrar todos los medicamentos
        medicaComB.addItem("Todos");

        for (Medicamentos medicamento : medicamentos) {
            medicaComB.addItem(medicamento.getNombre());
        }

        // Seleccionar "Todos" por defecto
        medicaComB.setSelectedIndex(0);
    }

    // Método para mostrar mensajes de error
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método para mostrar mensajes informativos
    public void mostrarMensajeInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarTabla(TableModel tableModel) {
        medicamentosTbl.setModel(tableModel);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}