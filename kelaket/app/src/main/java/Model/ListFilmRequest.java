package Model;

/**
 * Created by mohammad khalili on 7/24/2018.
 */

public class ListFilmRequest {

    private String email;
    private String password;
    private String genre;
    private String country;
    private String search;
    public ListFilmRequest(){

    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public String getSearch() {
        return search;
    }
}
