package com.example.mohammadkhalili.kelaket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mohammadkhalili.kelaket.loginpackage.login;
import com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity;
import com.google.gson.Gson;
import Model.User;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mohammad khalili on 8/10/2018.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences prefs = getSharedPreferences("saveuserinfo", MODE_PRIVATE);
        final String users=prefs.getString("user",null);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(users==null){
                    Intent intent=new Intent(Splash.this,login.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    MainListFilmActivity.user=new Gson().fromJson(users,User.class);
                    Intent intent=new Intent(Splash.this,MainListFilmActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);




    }


}
