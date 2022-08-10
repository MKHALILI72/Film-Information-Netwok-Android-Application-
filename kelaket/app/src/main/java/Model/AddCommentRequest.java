package Model;


import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.user;

/**
 * Created by mohammad khalili on 8/7/2018.
 */

public class AddCommentRequest {
    private String email;
    private String password;
    private int f_id;
    private String comment;

    public AddCommentRequest(){
        email=user.getEmail();
        password=user.getPassword();

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
