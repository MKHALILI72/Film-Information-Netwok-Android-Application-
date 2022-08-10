package Model;

/**
 * Created by mohammad khalili on 7/30/2018.
 */

public class FilmResp {
    public Boolean status;
    public int f_id;
    public String f_name;
    public String poster;
    public String f_director;
    public String f_createdate;
    public String f_keyactor;
    public String f_summary;
    public String f_budget;
    public String f_genre;
    public String f_trailer;
    public float f_rate;
    public int f_nvote;
    public String f_country;
    public Boolean f_addedfavorite;
    public Boolean f_isfilmscored;

    public String getF_trailer() {
        return f_trailer;
    }

    public void setF_trailer(String f_trailer) {
        this.f_trailer = f_trailer;
    }

    public Boolean getF_addedfavorite() {
        return f_addedfavorite;
    }

    public void setF_addedfavorite(Boolean f_addedfavorite) {
        this.f_addedfavorite = f_addedfavorite;
    }

    public Boolean getF_isfilmscored() {
        return f_isfilmscored;
    }

    public void setF_isfilmscored(Boolean f_isfilmscored) {
        this.f_isfilmscored = f_isfilmscored;
    }

    public String getF_country() {
        return f_country;
    }

    public void setF_country(String f_country) {
        this.f_country = f_country;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getF_director() {
        return f_director;
    }

    public void setF_director(String f_director) {
        this.f_director = f_director;
    }

    public String getF_createdate() {
        return f_createdate;
    }

    public void setF_createdate(String f_createdate) {
        this.f_createdate = f_createdate;
    }

    public String getF_keyactor() {
        return f_keyactor;
    }

    public void setF_keyactor(String f_keyactor) {
        this.f_keyactor = f_keyactor;
    }

    public String getF_summary() {
        return f_summary;
    }

    public void setF_summary(String f_summary) {
        this.f_summary = f_summary;
    }

    public String getF_budget() {
        return f_budget;
    }

    public void setF_budget(String f_budget) {
        this.f_budget = f_budget;
    }

    public String getF_genre() {
        return f_genre;
    }

    public void setF_genre(String f_genre) {
        this.f_genre = f_genre;
    }

    public float getF_rate() {
        return f_rate;
    }

    public void setF_rate(float f_rate) {
        this.f_rate = f_rate;
    }

    public int getF_nvote() {
        return f_nvote;
    }

    public void setF_nvote(int f_nvote) {
        this.f_nvote = f_nvote;
    }
}
