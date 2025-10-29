package Hospital.Logic;

import java.io.Serializable;

public class Medicamentos  implements Serializable {
    private String codigo;
    private String nombre;
    private String presentacion;


    public Medicamentos(String codigo, String nombre, String presentacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.presentacion = presentacion;
    }
    public Medicamentos() {
        this.codigo = "";
        this.nombre = "";
        this.presentacion = "";
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }


}