package mx.iteso.iturbeh.proyectointegrador.modelo.db;



/**
 * Created by iturbeh on 3/25/18.
 */

public class UserSession {

    String userName;
    String accessToken;

    public UserSession() {
    }

    public UserSession(String userName, String accessToken) {
        this.userName = userName;
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
