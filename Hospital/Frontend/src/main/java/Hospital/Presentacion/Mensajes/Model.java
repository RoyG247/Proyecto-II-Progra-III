package Hospital.Presentacion.Mensajes;
import Hospital.Logic.Empleado;
import Hospital.Presentacion.AbstractModel;

import java.util.ArrayList;

public class Model extends AbstractModel{
    Empleado currentUser;
    ArrayList<Empleado> users;

    public Model() {
        this.users = new ArrayList<>();
    }


    public Empleado getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Empleado currentUser) {
        this.currentUser = currentUser;
        firePropertyChange(USER_ONLINE);
    }

    public ArrayList<Empleado> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Empleado> users) {
        this.users = users;
    }

    public static final String USER_ONLINE = "online";
}
