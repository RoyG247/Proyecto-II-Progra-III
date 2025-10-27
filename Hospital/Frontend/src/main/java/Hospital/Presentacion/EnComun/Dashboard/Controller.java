package Hospital.Presentacion.EnComun.Dashboard;

import Hospital.Logic.Medicamentos;
import Hospital.Logic.Service;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.time.Month;
import java.util.List;
import java.util.Set;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        cargarAniosActivos();
        cargarMesesActivos();
        cargarMedicamento();
        mostrarGraficoRecetas();
        mostrarPieChart();
        actualizarTablaMedicamentos();
    }

    public void mostrarGraficoRecetas() {
        DefaultCategoryDataset dataset = model.getRecetasDatasetPorMesYMedicamentoOrdenado();
        view.mostrarGrafico(dataset);
    }
    public void mostrarPieChart() {
        DefaultPieDataset pieDataset = model.getEstadoRecetasDataset();
        view.mostrarPieChart(pieDataset);
    }

    // Nuevo método para filtrar el gráfico
    public void filtrarGrafico() {
        try {
            Integer anioDesde = (Integer) view.getAnioDComB().getSelectedItem();
            Integer anioHasta = (Integer) view.getAnioHComB().getSelectedItem();
            String mesDesdeStr = (String) view.getMesDComB().getSelectedItem();
            String mesHastaStr = (String) view.getMesHComB().getSelectedItem();
            String medicamentoSeleccionado = (String) view.getMedicaComB().getSelectedItem();

            // Validar que se hayan seleccionado todos los valores necesarios
            if (anioDesde == null || anioHasta == null || mesDesdeStr == null || mesHastaStr == null) {
                return;
            }

            // Convertir strings de mes a Month enum
            Month mesDesde = Month.valueOf(mesDesdeStr);
            Month mesHasta = Month.valueOf(mesHastaStr);

            // Validar que el rango sea válido
            if (anioDesde > anioHasta ||
                    (anioDesde.equals(anioHasta) && mesDesde.getValue() > mesHasta.getValue())) {
                view.mostrarMensajeError("El rango de fechas no es válido. La fecha de inicio debe ser menor o igual a la fecha final.");
                return;
            }

            DefaultCategoryDataset dataset = model.getRecetasDatasetFiltrado(
                    anioDesde, mesDesde, anioHasta, mesHasta, medicamentoSeleccionado
            );
            view.mostrarGrafico(dataset);

            // Actualizar pie chart con datos filtrados
            DefaultPieDataset pieDataset = model.getEstadoRecetasDatasetFiltrado(
                    anioDesde, mesDesde, anioHasta, mesHasta, medicamentoSeleccionado
            );
            view.mostrarPieChart(pieDataset);

            DefaultCategoryDataset datasetFiltrado = model.getRecetasDatasetFiltrado(
                    anioDesde, mesDesde, anioHasta, mesHasta, medicamentoSeleccionado
            );
            TableModel tableModel = new TableModel(datasetFiltrado);
            view.mostrarTabla(tableModel);

        } catch (Exception e) {
            view.mostrarMensajeError("Error al filtrar los datos: " + e.getMessage());
        }


    }

    public void cargarAniosActivos() {
        Set<Integer> anios = model.getAniosActivos();
        view.mostrarAniosEnCombo(anios);
    }

    public void cargarMesesActivos(){
        Set<String> meses = model.getMesesActivos();
        view.mostrarMesesEnCombo(meses);
    }

    public void cargarMedicamento() {
        List<Medicamentos> medicamentos = Service.instance().findAllMedicamentos();
        view.mostrarMedicamentosEnCombo(medicamentos);
    }

    public void actualizarTablaMedicamentos() {
        DefaultCategoryDataset dataset = model.getRecetasDatasetPorMesYMedicamentoOrdenado();
        TableModel tableModel = new TableModel(dataset);
        view.mostrarTabla(tableModel);
    }
}