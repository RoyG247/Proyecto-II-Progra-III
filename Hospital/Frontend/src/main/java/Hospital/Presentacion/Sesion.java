package Hospital.Presentacion;

import Hospital.Logic.Empleado;
import Hospital.Logic.Medico;

import java.util.Objects;

public class Sesion {
    private static Empleado usuario;

    public static Empleado getUsuario() {
        return usuario;
    }
    public static void setUsuario(Empleado usuario) {
        Sesion.usuario = usuario;
    }
    public static void logout() {
        Sesion.usuario = null;
    }
    public static boolean isLoggedIn() {
        return usuario != null;
    }
    public static Medico getMedico() {
        if (usuario instanceof Medico) {
            return (Medico) usuario;
        }
        return null;
    }

    public static boolean firstLogin() {
        return Objects.equals(usuario.getClave(), usuario.getId());
    }
}

