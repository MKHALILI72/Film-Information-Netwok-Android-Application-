package Server;
/**
 * Created by mohammad khalili on 7/22/2018.
 */
public class UrlReq {


    public static String callServerFunction(int number) {
        String Base_URL = "http://172.17.100.2:8080/";
        switch (number) {
            case 1:
                 Base_URL += "registerUser";
                 break;
            case 2:
                Base_URL +="getListFilm";
                break;
            case 3:
                Base_URL+="login";
                break;
            case 4:
                Base_URL+="getfilminfo";
                break;
            case 5:
                Base_URL+="getlistcomment";
                break;
            case 6:
                Base_URL+="setRateFilm";
                break;
            case 7:
                Base_URL+="adddeletefavorite";
                break;
            case 8:
                Base_URL+="addcomment";
                break;
            case 9:
                Base_URL+="getuserinfobyid";
                break;
            case 10:
                Base_URL+="getuserfavoritelist";
                break;
            case 11:
                Base_URL+="upload";
                break;
            case 12:
                Base_URL+="sendmail";
                break;
            case 13:
                Base_URL+="editeuserinfo";
                break;



        }
        return Base_URL;
    }
}
