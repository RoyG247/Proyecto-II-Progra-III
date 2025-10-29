package Hospital.Presentacion.EnComun.Dashboard;

import Hospital.Logic.Medicamentos;
import Hospital.Logic.Prescripcion;
import Hospital.Logic.Recetas;
import Hospital.Logic.Service;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.data.category.DefaultCategoryDataset;

import java.time.Month;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;


public class Model {
    List<Recetas> recetas;

    public static final String INFOMED = "infomed";

    public Model() {
        recetas = Service.instance().findAllRecetas();
    }

    public void refreshData() {
        recetas = Service.instance().findAllRecetas();
    }

    public List<Recetas> getRecords() {
        return recetas;
    }

    public void setRecords(List<Recetas> recetas) {
        this.recetas = recetas;
    }

    public DefaultCategoryDataset getRecetasDatasetPorMesYMedicamentoOrdenado() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (recetas == null) return dataset;

        TreeMap<YearMonth, Map<String, Integer>> mapaMeses = new TreeMap<>();

        for (Recetas receta : recetas) {
            if (receta.getPrescripciones() == null || receta.getFechaRetiro() == null) continue;

            YearMonth mes = YearMonth.from(receta.getFechaRetiro());
            Map<String, Integer> meds = mapaMeses.getOrDefault(mes, new HashMap<>());

            for (Prescripcion prescripcion : receta.getPrescripciones()) {
                if (prescripcion.getMedicamento() == null) continue;

                String nombreMedicamento = prescripcion.getMedicamento().getNombre();
                int cantidad = prescripcion.getCantidad();

                meds.put(nombreMedicamento, meds.getOrDefault(nombreMedicamento, 0) + cantidad);
            }

            mapaMeses.put(mes, meds);
        }

        for (Map.Entry<YearMonth, Map<String, Integer>> entry : mapaMeses.entrySet()) {
            String mesStr = entry.getKey().toString(); // "YYYY-MM"
            Map<String, Integer> meds = entry.getValue();

            for (Map.Entry<String, Integer> medEntry : meds.entrySet()) {
                dataset.addValue(medEntry.getValue(), medEntry.getKey(), mesStr);
            }
        }

        return dataset;
    }
    public DefaultPieDataset getEstadoRecetasDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        if (recetas == null) return dataset;

        Map<String, Integer> contadorEstados = new HashMap<>();

        for (Recetas receta : recetas) {
            String estado = receta.getEstado();
            contadorEstados.put(estado, contadorEstados.getOrDefault(estado, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : contadorEstados.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }
    public DefaultPieDataset getEstadoRecetasDatasetFiltrado(Integer anioDesde, Month mesDesde,
                                                             Integer anioHasta, Month mesHasta,
                                                             String medicamentoFiltro) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        if (recetas == null) return dataset;

        YearMonth fechaDesde = YearMonth.of(anioDesde, mesDesde);
        YearMonth fechaHasta = YearMonth.of(anioHasta, mesHasta);

        Map<String, Integer> contadorEstados = new HashMap<>();

        for (Recetas receta : recetas) {
            boolean cumpleFiltroMedicamento = false;
            boolean cumpleFiltroFecha = false;

            if (medicamentoFiltro == null || medicamentoFiltro.equals("Todos")) {
                cumpleFiltroMedicamento = true;
            } else if (receta.getPrescripciones() != null) {
                for (Prescripcion prescripcion : receta.getPrescripciones()) {
                    if (prescripcion.getMedicamento() != null &&
                            prescripcion.getMedicamento().getNombre().equals(medicamentoFiltro)) {
                        cumpleFiltroMedicamento = true;
                        break;
                    }
                }
            }
            if (!cumpleFiltroMedicamento) continue;

            if (receta.getFechaRetiro() != null) {
                YearMonth mesRetiro = YearMonth.from(receta.getFechaRetiro());
                if (!mesRetiro.isBefore(fechaDesde) && !mesRetiro.isAfter(fechaHasta)) {
                    cumpleFiltroFecha = true;
                }
            } else {
                // Para recetas no retiradas, usar fecha de emisión
                if (receta.getFechadeEmision() != null) {
                    YearMonth mesEmision = YearMonth.from(receta.getFechadeEmision());
                    if (!mesEmision.isBefore(fechaDesde) && !mesEmision.isAfter(fechaHasta)) {
                        cumpleFiltroFecha = true;
                    }
                }
            }

            if (cumpleFiltroFecha) {
                String estado = receta.getEstado();
                contadorEstados.put(estado, contadorEstados.getOrDefault(estado, 0) + 1);
            }
        }

        // Agregar cada estado al dataset
        for (Map.Entry<String, Integer> entry : contadorEstados.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }

    public DefaultCategoryDataset getRecetasDatasetFiltrado(Integer anioDesde, Month mesDesde,
                                                            Integer anioHasta, Month mesHasta,
                                                            String medicamentoFiltro) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (recetas == null) return dataset;

        YearMonth fechaDesde = YearMonth.of(anioDesde, mesDesde);
        YearMonth fechaHasta = YearMonth.of(anioHasta, mesHasta);

        TreeMap<YearMonth, Map<String, Integer>> mapaMeses = new TreeMap<>();

        for (Recetas receta : recetas) {
            if (receta.getPrescripciones() == null || receta.getFechaRetiro() == null) continue;

            YearMonth mes = YearMonth.from(receta.getFechaRetiro());

            // Filtrar por rango de fechas
            if (mes.isBefore(fechaDesde) || mes.isAfter(fechaHasta)) {
                continue;
            }

            Map<String, Integer> meds = mapaMeses.getOrDefault(mes, new HashMap<>());

            for (Prescripcion prescripcion : receta.getPrescripciones()) {
                if (prescripcion.getMedicamento() == null) continue;

                String nombreMedicamento = prescripcion.getMedicamento().getNombre();
                int cantidad = prescripcion.getCantidad();

                // Filtrar por medicamento si se especifica (null o "Todos" significa todos los medicamentos)
                if (medicamentoFiltro != null && !medicamentoFiltro.equals("Todos") &&
                        !nombreMedicamento.equals(medicamentoFiltro)) {
                    continue;
                }

                meds.put(nombreMedicamento, meds.getOrDefault(nombreMedicamento, 0) + cantidad);
            }

            if (!meds.isEmpty()) {
                mapaMeses.put(mes, meds);
            }
        }

        // Convertir el mapa a dataset
        for (Map.Entry<YearMonth, Map<String, Integer>> entry : mapaMeses.entrySet()) {
            String mesStr = entry.getKey().toString(); // "YYYY-MM"
            Map<String, Integer> meds = entry.getValue();

            for (Map.Entry<String, Integer> medEntry : meds.entrySet()) {
                dataset.addValue(medEntry.getValue(), medEntry.getKey(), mesStr);
            }
        }


        return dataset;
    }

    public Set<Integer> getAniosActivos() {
        Set<Integer> anios = new TreeSet<>();
        for (Recetas receta : recetas) {
            if (receta.getFechaRetiro() != null) {
                anios.add(receta.getFechaRetiro().getYear());
            }
        }
        return anios;
    }

    public Set<String> getMesesActivos() {
        Set<String> meses = new TreeSet<>();
        for (Recetas receta : recetas) {
            if (receta.getFechaRetiro() != null) {
                String mes = String.valueOf(receta.getFechaRetiro().getMonth());
                meses.add(mes);
            }
        }
        return meses;
    }
}