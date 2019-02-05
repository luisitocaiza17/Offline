package micotizador.offline.filestructure;

import java.util.List;

/**
 * Created by Admin on 06/07/2015.
 */
public class Security {


    public List<String> getLog() {
        return Log;
    }

    public void setLog(List<String> log) {
        Log = log;
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }

    private List<User> Users;
    private List<String> Log;
}
