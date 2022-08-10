package com.example.mohammadkhalili.kelaket.loginpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.mohammadkhalili.kelaket.R;
import com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.RegisterUserResp;
import Model.User;



import Server.UrlReq;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.user;

/**
 * Created by mohammad khalili on 7/6/2018.
 */

public class RegisterActivity extends AppCompatActivity {
    public EditText password;
    public EditText username;
    public EditText repassword;
    public EditText description;
    public EditText email;
    public Button registerbtn;
    public ProgressBar progress;
    public String resp;
    public ImageView back;
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        setitem();
        progress.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equals("")||password.getText().toString().equals("")||repassword.getText().toString().equals("")||username.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(),"فیلدها به طور کامل پر نشده است",Toast.LENGTH_LONG).show();
                }else
                {
                    UrlReq myru=new UrlReq();
                    String url=myru.callServerFunction(1);
                    user=new User();
                    if(isEmailValid(email.getText().toString())) {
                        if(password.getText().toString().equals(repassword.getText().toString())) {
                            progress.setVisibility(View.VISIBLE);
                            user.setEmail(email.getText().toString());
                            user.setPassword(password.getText().toString());
                            user.setUsername(username.getText().toString());
                            user.setDescription(description.getText().toString());
                            user.setIslogin(false);
                            user.setProfilepic("");
                            String json = new Gson().toJson(user);
                            Log.i("jsonuser", json);
                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                            Request req = new Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .build();
                            OkHttpClient client = new OkHttpClient();

                            client.newCall(req).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.i("erroronfail",e.getMessage());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progress.setVisibility(View.INVISIBLE);
                                            Toast.makeText(RegisterActivity.this,"متاسفانه در ازتباط با سرور مشکلی پیش آمده است",Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                          ResponseBody reb=response.body();
                                          resp=reb.string();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            RegisterUserResp registerUserResp=new Gson().fromJson(resp,RegisterUserResp.class);
                                            if(registerUserResp.getStatus()==false){
                                                Toast.makeText(RegisterActivity.this,"کاربری با ایمیل شما وجود دارد",Toast.LENGTH_LONG).show();
                                            }else{
                                                user.setId(registerUserResp.getId());
                                                user.setIslogin(true);
                                                Log.i("mohammad_khalili",Integer.toString(registerUserResp.getId()));
                                                Log.i("mohammad_khalili",registerUserResp.getStatus().toString());
                                                if(registerUserResp.getStatus()==true) {
                                                    Intent in = new Intent(RegisterActivity.this, MainListFilmActivity.class);
                                                    SharedPreferences.Editor editor = getSharedPreferences("saveuserinfo", MODE_PRIVATE).edit();
                                                    String userstr=new Gson().toJson(user);
                                                    editor.putString("user",userstr);
                                                    editor.commit();
                                                    startActivity(in);
                                                    login.login.finish();
                                                    finish();

                                                }
                                            }
                                            progress.setVisibility(View.INVISIBLE);


                                        }
                                    });
                                }
                            });

                        }else
                            Toast.makeText(RegisterActivity.this,"رمز عبور و تکرار آن با هم تطابق ندارد!!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(RegisterActivity.this,"ایمیل خود را اشتباه وارد کرده اید!",Toast.LENGTH_LONG).show();
                    }



                }
            }
        });


    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void setitem(){
       password=(EditText) findViewById(R.id.password) ;
       repassword=(EditText)findViewById(R.id.repassword);
       username=(EditText)findViewById(R.id.username);
       email=(EditText)findViewById(R.id.email);
       description=(EditText)findViewById(R.id.description);
       registerbtn=(Button)findViewById(R.id.registerbtn);
        progress=(ProgressBar) findViewById(R.id.progress);
        back=(ImageView)findViewById(R.id.back);


   }
}
