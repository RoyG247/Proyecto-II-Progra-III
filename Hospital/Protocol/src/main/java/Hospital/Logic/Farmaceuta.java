package Hospital.Logic;

import java.io.Serializable;

public class Farmaceuta extends Empleado implements Serializable {
    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre, clave, "FAR");
    }
    public Farmaceuta() {
        super("","","","");
    }
}
