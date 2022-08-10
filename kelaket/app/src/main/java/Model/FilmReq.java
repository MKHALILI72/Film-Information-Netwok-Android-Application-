package Model;

import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.user;

/**
 * Created by mohammad khalili on 7/30/2018.
 */

public class FilmReq {
    public String email;
    public String password;
    public int f_id;
    public  int u_id;

    public FilmReq(){

        email=user.getEmail();
        password=user.getPassword();
        u_id=user.getId();
//        email=user.getEmail();
//        password=user.getPassword();
    }


    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }
}
