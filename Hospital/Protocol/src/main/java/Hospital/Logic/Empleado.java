package Hospital.Logic;

import java.io.Serializable;

public class Empleado extends Personas implements Serializable {
    private String clave;
    private String rol;

    public Empleado() { super(); }

    public Empleado(String id, String nombre, String clave, String rol) {
        super(id, nombre);
        this.clave = clave;
        this.rol = rol;
    }


    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getRol() {return rol;}
    public void setRol(String rol) {this.rol = rol;}
}
