package Hospital.Logic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Recetas {
    int id;
    List<Prescripcion> prescripciones = new ArrayList<>();
    private Pacientes paciente;
    private Medico medico;
    private LocalDate fechadeEmision;
    private LocalDate fechaRetiro;
    private String estado = "CONFECCIONADA";

    public Recetas(List<Prescripcion> prescripciones, Pacientes paciente) {
        this.prescripciones = prescripciones != null ? prescripciones : new ArrayList<>();
        this.paciente = paciente;
        fechadeEmision = LocalDate.now();
    }

    public Recetas(List<Prescripcion> prescripciones, Pacientes paciente, Medico medico) {
        this.prescripciones = prescripciones != null ? prescripciones : new ArrayList<>();
        this.paciente = paciente;
        this.medico = medico;
        fechadeEmision = LocalDate.now();
    }

    public Recetas() {
        this.prescripciones = new ArrayList<>();
        this.paciente = null;
        this.medico = null;
        fechadeEmision = LocalDate.now();
    }

    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones != null ? prescripciones : new ArrayList<>();
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDate getFechadeEmision() {
        return fechadeEmision;
    }

    public void setFechadeEmision(LocalDate fechadeEmision) {
        this.fechadeEmision = fechadeEmision;
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (!estado.equals("CONFECCIONADA") &&
                !estado.equals("PROCESO") &&
                !estado.equals("LISTA") &&
                !estado.equals("ENTREGADA") &&
        !estado.equals("")) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }
}
