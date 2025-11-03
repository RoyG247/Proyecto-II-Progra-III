package Hospital.Logic;

public class Mensaje {
    String idEmisor;
    String idReceptor;
    String contenido;

    public Mensaje(String idEmisor, String idReceptor, String contenido) {
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.contenido = contenido;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
