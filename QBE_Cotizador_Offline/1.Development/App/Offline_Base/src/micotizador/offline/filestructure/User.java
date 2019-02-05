package micotizador.offline.filestructure;


/**
 * Created by Admin on 06/07/2015.
 */
public class User {

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCIUser() {
        return CIUser;
    }

    public void setCIUser(String CIUser) {
        this.CIUser = CIUser;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPuntoVentaId() {
        return PuntoVentaId;
    }

    public void setPuntoVentaId(String puntoVentaId) {
        PuntoVentaId = puntoVentaId;
    }

    private int UserID;
    private String Name;
    private String UserName;
    private String Password;
    private String CIUser;
    private String LastName;
    private String PuntoVentaId;
    private boolean admin;
    private String Agencia;

    public String getAgencia() {
        return Agencia;
    }

    public void setAgencia(String agencia) {
        this.Agencia = agencia;
    }

    public boolean getisAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}