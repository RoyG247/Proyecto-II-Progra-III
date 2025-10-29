package Hospital;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Hospital.Logic.Service;
import Hospital.Presentacion.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Service.instance();
        doLogin();
        if (Sesion.isLoggedIn()) {
            doRun();
        }

    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);
    private static void doLogin() {
        Hospital.Presentacion.login.Model model = new Hospital.Presentacion.login.Model();
        Hospital.Presentacion.login.Login view = new Hospital.Presentacion.login.Login();
        view.setTitle("Login");
        view.pack();
        view.setLocationRelativeTo(null);
        Hospital.Presentacion.login.Controller controller = new Hospital.Presentacion.login.Controller(view, model);
        view.setVisible(true);
    }

    private static void doRun(){
        JFrame window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.instance().stop();
            }
        });

        //Pestaña medicos
        Hospital.Presentacion.Administrador.Medicos.Model modelMedicos = new Hospital.Presentacion.Administrador.Medicos.Model();
        Hospital.Presentacion.Administrador.Medicos.View viewMedicos = new Hospital.Presentacion.Administrador.Medicos.View();
        Hospital.Presentacion.Administrador.Medicos.Controller controllerMedicos = new Hospital.Presentacion.Administrador.Medicos.Controller(viewMedicos, modelMedicos);

        //Pestaña pacientes
        Hospital.Presentacion.Administrador.Paciente.Model modelPacientes = new Hospital.Presentacion.Administrador.Paciente.Model();
        Hospital.Presentacion.Administrador.Paciente.View viewPacientes = new Hospital.Presentacion.Administrador.Paciente.View();
        Hospital.Presentacion.Administrador.Paciente.Controller controllerPacientes = new Hospital.Presentacion.Administrador.Paciente.Controller(viewPacientes, modelPacientes);

        //Pestaña farmaceuta
        Hospital.Presentacion.Administrador.Farmaceuta.Model modelFarmaceuta = new Hospital.Presentacion.Administrador.Farmaceuta.Model();
        Hospital.Presentacion.Administrador.Farmaceuta.View viewFarmaceuta = new Hospital.Presentacion.Administrador.Farmaceuta.View();
        Hospital.Presentacion.Administrador.Farmaceuta.Controller controllerFarmaceuta = new Hospital.Presentacion.Administrador.Farmaceuta.Controller(viewFarmaceuta, modelFarmaceuta);

        //Prestaña medicamentos
        Hospital.Presentacion.Administrador.Medicamento.Model modelMedicamentos = new Hospital.Presentacion.Administrador.Medicamento.Model();
        Hospital.Presentacion.Administrador.Medicamento.View viewMedicamentos = new Hospital.Presentacion.Administrador.Medicamento.View();
        Hospital.Presentacion.Administrador.Medicamento.Controller controllerMedicamentos = new Hospital.Presentacion.Administrador.Medicamento.Controller(viewMedicamentos, modelMedicamentos);

        // Pestaña prescripcion
        Hospital.Presentacion.Medicos.Prescripcion.Model modelPrescripcion = new Hospital.Presentacion.Medicos.Prescripcion.Model();
        Hospital.Presentacion.Medicos.Prescripcion.View viewPrescripcion = new Hospital.Presentacion.Medicos.Prescripcion.View();
        Hospital.Presentacion.Medicos.Prescripcion.Controller controllerPrescripcion = new Hospital.Presentacion.Medicos.Prescripcion.Controller(viewPrescripcion, modelPrescripcion);
        viewPrescripcion.setDatosUser(Sesion.getUsuario().getNombre(), Sesion.getUsuario().getId());

        //Pestañas en comun
        //Pestaña dashboard
        Hospital.Presentacion.EnComun.Dashboard.Model modelDashboard = new Hospital.Presentacion.EnComun.Dashboard.Model();
        Hospital.Presentacion.EnComun.Dashboard.View viewDashboard = new Hospital.Presentacion.EnComun.Dashboard.View();
        Hospital.Presentacion.EnComun.Dashboard.Controller controllerDashboard = new Hospital.Presentacion.EnComun.Dashboard.Controller(viewDashboard, modelDashboard);
        //viewDashboard.inicializarGrafico();
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                String titulo = tabbedPane.getTitleAt(index);
                if ("Dashboard".equals(titulo)) {
                    controllerDashboard.mostrarPieChart();
                    controllerDashboard.mostrarGraficoRecetas();
                }
            }
        });

        //Pestaña Historico
        Hospital.Presentacion.EnComun.Historico.Model modelHistorico = new Hospital.Presentacion.EnComun.Historico.Model();
        Hospital.Presentacion.EnComun.Historico.View viewHistorico = new Hospital.Presentacion.EnComun.Historico.View();
        Hospital.Presentacion.EnComun.Historico.Controller controllerHistorico = new Hospital.Presentacion.EnComun.Historico.Controller(viewHistorico, modelHistorico);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                    int index = tabbedPane.getSelectedIndex();
                    String titulo = tabbedPane.getTitleAt(index);
                    if ("Historico".equals(titulo)) {
                        viewHistorico.actualizarTablaRecetas();
                    }
            }
        });
        //Pestania acerca De
        Hospital.Presentacion.EnComun.AcercaDe.View viewAcercaDe = new Hospital.Presentacion.EnComun.AcercaDe.View();

        //Pestaña Despacho
        Hospital.Presentacion.Despacho.Model modelDespacho = new Hospital.Presentacion.Despacho.Model();
        Hospital.Presentacion.Despacho.View viewDespacho = new Hospital.Presentacion.Despacho.View();
        Hospital.Presentacion.Despacho.Controller controllerDespacho = new Hospital.Presentacion.Despacho.Controller(viewDespacho, modelDespacho);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                String titulo = tabbedPane.getTitleAt(index);
                if ("Despacho".equals(titulo)) {
                    viewDespacho.actualizarTablaRecetas();
                }
            }
        });

        window.setTitle("Despacho de Recetas - " + Sesion.getUsuario().getNombre() + " (" + Sesion.getUsuario().getRol() + ")");
        switch (Sesion.getUsuario().getRol()) {
            case "ADM":
                tabbedPane.addTab("Médicos", viewMedicos.getPanel());
                tabbedPane.addTab("Pacientes", viewPacientes.getPanel());
                tabbedPane.addTab("Farmacéuticos", viewFarmaceuta.getPanel());
                tabbedPane.addTab("Medicamentos", viewMedicamentos.getPanel());
                tabbedPane.addTab("Dashboard", viewDashboard.getGrafico());
                tabbedPane.addTab("Historico", viewHistorico.getPanel());
                tabbedPane.addTab("Acerca de", viewAcercaDe.getPanel());
                break;
            case "MED":
                tabbedPane.addTab("Prescripcion", viewPrescripcion.getPanel());
                tabbedPane.addTab("Dashboard", viewDashboard.getGrafico());
                tabbedPane.addTab("Historico", viewHistorico.getPanel());
                tabbedPane.addTab("Acerca de", viewAcercaDe.getPanel());
                break;
            case "FAR":
                tabbedPane.addTab("Despacho", viewDespacho.getPanel());
                tabbedPane.addTab("Dashboard", viewDashboard.getGrafico());
                tabbedPane.addTab("Historico", viewHistorico.getPanel());
                tabbedPane.addTab("Acerca de", viewAcercaDe.getPanel());

                break;
            default:
                break;
        }
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(900, 600 );
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public final static int MODE_CREATE=1;
    public final static int MODE_EDIT=2;
}

