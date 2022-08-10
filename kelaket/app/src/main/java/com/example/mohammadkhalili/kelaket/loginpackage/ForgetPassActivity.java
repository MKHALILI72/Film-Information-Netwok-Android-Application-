package com.example.mohammadkhalili.kelaket.loginpackage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mohammadkhalili.kelaket.R;
import com.google.gson.Gson;

import java.io.IOException;

import Model.ForgetpassRequest;
import Model.Status;
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

/**
 * Created by mohammad khalili on 7/6/2018.
 */

public class ForgetPassActivity extends AppCompatActivity {
    public EditText forgettxt;
    public Button selectbtn;
    public ImageView back;
    public ResponseBody resp;
    public String body;
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpasswordpage);
        setitems();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=new UrlReq().callServerFunction(12);
                ForgetpassRequest forgetpass=new ForgetpassRequest();
                forgetpass.setEmail(forgettxt.getText().toString());
                String json=new Gson().toJson(forgetpass);
                RequestBody requestbody=RequestBody.create(MediaType.parse("application/json"),json);
                Request request=new Request.Builder()
                        .url(url)
                        .post(requestbody)
                        .build();
                OkHttpClient okhttp=new OkHttpClient();

                okhttp.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgetPassActivity.this,"قطع ارتباط با سرور",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                       ResponseBody resp=response.body();
                       body=resp.string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Status status=new Gson().fromJson(body,Status.class);
                                if(status.getStatus()){
                                    Toast.makeText(ForgetPassActivity.this,"ایمیل یادآوری ارسال شد",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(ForgetPassActivity.this,"کاربری با این ایمیل وجود ندارد",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

            }
        });

    }
    public void setitems(){
        forgettxt=(EditText) findViewById(R.id.forgettxt);
        selectbtn=(Button)findViewById(R.id.selectbtn);
        back=(ImageView) findViewById(R.id.back);
    }
}
