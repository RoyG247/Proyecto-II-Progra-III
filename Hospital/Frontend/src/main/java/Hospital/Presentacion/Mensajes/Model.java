package Hospital.Presentacion.Mensajes;
import Hospital.Logic.Empleado;
import Hospital.Presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Model extends AbstractModel{
    Empleado currentUser;
    ArrayList<Empleado> users;
    String message;

    public static final String USER_ONLINE = "online";
    public static final String USERS = "usuarios";

    public Model() {
        this.users = new ArrayList<>();
        this.currentUser = new Empleado();
    }


    public Empleado getCurrentUser() {
        return currentUser;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(USER_ONLINE);
        firePropertyChange(USERS);
    }

    public void setCurrentUser(Empleado currentUser) {
        Empleado oldUser = this.currentUser;
        this.currentUser = currentUser;
        firePropertyChange(USER_ONLINE, oldUser, currentUser);
    }

    public ArrayList<Empleado> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Empleado> users) {
        ArrayList<Empleado> oldUsers = this.users;
        this.users = users;
        firePropertyChange(USERS, oldUsers, users);
    }

    public void addUser(Empleado user) {
        this.users.add(user);
        firePropertyChange(USERS, null, this.users);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
