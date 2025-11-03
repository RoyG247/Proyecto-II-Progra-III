package Hospital.Presentacion;

import Hospital.Logic.*;

import java.util.List;

public interface ThreadListener {
    public void deliver_user(Empleado user);
    public void deliver_users(List<Empleado> users);
    public void deliver_message(String message);
}
