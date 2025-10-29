package Hospital.Logic;

import java.io.Serializable;

public class Prescripcion implements Serializable {
    private int id;
    private Medicamentos medicamento;
    private String indicaciones;
    private int cantidad;
    private int duracion;
    private int receta;
    // Duración en días

    public Prescripcion(Medicamentos medicamento, String indicaciones) {
        this.medicamento = medicamento;
        this.indicaciones = indicaciones;
    }

    public Prescripcion() {
        this.medicamento = null;
        this.indicaciones = "";
        this.cantidad = 0;
        this.duracion = 0;
    }

    public int getReceta() {
        return receta;
    }

    public void setReceta(int receta) {
        this.receta = receta;
    }
    public Medicamentos getMedicamento() {
        return medicamento;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setMedicamento(Medicamentos medicamento) {
        this.medicamento = medicamento;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }

    public boolean isModificado() {
        return cantidad != 0 || duracion != 0 || (indicaciones != null && !indicaciones.isEmpty());
    }
    public int getId() {
        return id;
    }

    public void setId(Integer iD) {
        this.id= iD;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }
}
