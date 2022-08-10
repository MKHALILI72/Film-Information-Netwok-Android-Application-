package Model;

/**
 * Created by mohammad khalili on 7/23/2018.
 */

public class RegisterUserResp {
    private Boolean status;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
