package Hospital.Logic;

public abstract class Personas {
    private String id;
    private String nombre;

    protected Personas() { }  // Necesario para JAXB

    public Personas(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
