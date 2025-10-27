package Hospital.Logic;

public class Farmaceuta extends Empleado{
    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre, clave, "FAR");
    }
    public Farmaceuta() {
        super("","","","");
    }
}
