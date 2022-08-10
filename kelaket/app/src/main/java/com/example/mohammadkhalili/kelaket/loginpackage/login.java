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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammadkhalili.kelaket.R;
import com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity;
import com.google.gson.Gson;

import java.io.IOException;

import Model.LoginRequest;
import Model.LoginResp;
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

public class login extends AppCompatActivity {
    public Button registerbutton;
    public Button signinbutton;
    public EditText email;
    public EditText password;
    public TextView forgotpass;
    public static Activity  login;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginpage);
        login=this;


        setViewId();
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registeractivity=new Intent(login.this,RegisterActivity.class);
                startActivity(registeractivity);
            }
        });
        signinbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String emailtext=email.getText().toString();
                String passwordtext=password.getText().toString();
                if(emailtext.equals("")||passwordtext.equals("")){
                    Toast.makeText(login.this,"ایمیل یا رمز عبور خود را وارد نکرده اید",Toast.LENGTH_SHORT).show();
                }else{
                    UrlReq urlreq=new UrlReq();
                    String url=urlreq.callServerFunction(3);
                    LoginRequest loginrequest=new LoginRequest();
                    loginrequest.setEmail(emailtext);
                    loginrequest.setPassword(passwordtext);
                    String json=new Gson().toJson(loginrequest);
                    RequestBody requestbody=RequestBody.create(MediaType.parse("application/json"), json);
                    Log.i("mohammad_khalili",json);
                    Request request=new Request.Builder()
                            .url(url)
                            .post(requestbody)
                            .build();
                    OkHttpClient okhttpclient=new OkHttpClient();
                    okhttpclient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("mohammad_khalili",e.getMessage());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(login.this,"متاسفانه در ازتباط با سرور مشکلی پیش آمده است",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            ResponseBody responseBody=response.body();
                            String body=responseBody.string();
                            Log.i("mohammad_khalili",body);
                            LoginResp loginResp=new Gson().fromJson(body,LoginResp.class);
                            if(loginResp.getStatus()==true){
                                    user=new User();
                                    user.setIslogin(true);
                                    user.setPassword(loginResp.getPassword());
                                    user.setEmail(loginResp.getEmail());
                                    user.setId(loginResp.getId());
                                    user.setDescription(loginResp.getDescription());
                                    user.setUsername(loginResp.getUsername());
                                Log.i("mohammad_khalili","tainjadorst");
                                Intent intent=new Intent(login.this,MainListFilmActivity.class);
                                startActivity(intent);
                                SharedPreferences.Editor editor = getSharedPreferences("saveuserinfo", MODE_PRIVATE).edit();
                                String userstr=new Gson().toJson(user);
                                editor.putString("user",userstr);
                                editor.commit();
                                finish();
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(login.this,"کاربر یافت نشد",Toast.LENGTH_LONG).show();
                                    }
                                });
                                ;
                            }


                        }
                    });
                }
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beginforgetactivity=new Intent(login.this,ForgetPassActivity.class);
                startActivity(beginforgetactivity);
            }
        });
    }


    private void setViewId(){
        registerbutton=(Button)findViewById(R.id.registerbtn);
        signinbutton=(Button)findViewById(R.id.loginbtn);
        email=(EditText) findViewById(R.id.emailtext);
        password=(EditText)findViewById(R.id.passwordtext);
        forgotpass=(TextView)findViewById(R.id.forgotpasstext);
    }

}
