package Hospital.Logic;

import java.io.Serializable;
import java.time.LocalDate;

public class Pacientes extends Personas implements Serializable {
    private String numeroTelefono;
    private LocalDate fechaNacimiento;


    public Pacientes(String id, String nombre, String numeroTelefono, LocalDate fechaNacimiento) {
        super(id, nombre);
        this.numeroTelefono = numeroTelefono;
        this.fechaNacimiento = fechaNacimiento;
    }
    public Pacientes() {
        super("","");
    }
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}