package Model;

/**
 * Created by mohammad khalili on 7/24/2018.
 */

public class LoginRequest {
    public String email;
    public String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
