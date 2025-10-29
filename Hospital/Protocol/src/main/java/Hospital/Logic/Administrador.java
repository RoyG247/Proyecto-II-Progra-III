package Hospital.Logic;


import java.io.Serializable;

public class Administrador extends Empleado implements Serializable {
    public Administrador(String id, String nombre, String clave) {
        super(id, nombre, clave,"ADM");
    }
    public Administrador(){}
}
