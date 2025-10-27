package Hospital.Logic;

public class Medico extends Empleado {
    private String especialidad;

    public Medico(String id, String nombre, String clave, String especialidad) {
        super(id, nombre, clave, "MED");
        this.especialidad = especialidad;
    }

    public Medico() {
        super("","","","");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
