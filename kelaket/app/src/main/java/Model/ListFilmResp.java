package Model;

/**
 * Created by mohammad khalili on 7/24/2018.
 */

public class ListFilmResp {
    private boolean status;
    private int id;
    private String name;
    private String poster;
    private String createdate;
    private float rate;
    private int nvote;
    private String director;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getNvote() {
        return nvote;
    }

    public void setNvote(int nvote) {
        this.nvote = nvote;
    }
}
