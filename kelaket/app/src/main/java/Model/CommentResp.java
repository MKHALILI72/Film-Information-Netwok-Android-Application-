package Model;

import android.widget.ImageView;

/**
 * Created by mohammad khalili on 7/30/2018.
 */

public class CommentResp {
    public Boolean status;
    public String propic;
    public String username;
    public String comment;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
