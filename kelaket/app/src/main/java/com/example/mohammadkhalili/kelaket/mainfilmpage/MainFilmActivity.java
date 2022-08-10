package com.example.mohammadkhalili.kelaket.mainfilmpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mohammadkhalili.kelaket.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Model.AddCommentRequest;
import Model.CommentResp;
import Model.FilmReq;
import Model.FilmResp;
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
 * Created by mohammad khalili on 7/27/2018.
 */

public class MainFilmActivity extends AppCompatActivity {
    public RecyclerView recycler;
    public FilmResp filmresp;
    public FilmReq filmreq;
    public static List<CommentResp> comment;
    public ProgressBar progress;
    public String respbodys;
    public String commentresp;
    public MainFilmAdapter adapter;
    public FloatingActionButton floatingaction;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filmview);
        setitems();
        progress.setVisibility(View.VISIBLE);
        filmreq=new FilmReq();
//        filmreq.setF_id(getIntent().getIntExtra("f_id",0));
        filmreq.setF_id(getIntent().getIntExtra("f_id",0));
        String json=new Gson().toJson(filmreq);
        Log.i("mohammad_khalili",json);
        RequestBody requestb=RequestBody.create(MediaType.parse("application/json"),json);
        Request request=new Request.Builder()
                .url(new UrlReq().callServerFunction(4))
                .post(requestb)
                .build();
        OkHttpClient okhttp=new OkHttpClient();
        okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("erroronresp", e.getMessage());
                Toast.makeText(MainFilmActivity.this, "متاسفانه در ازتباط با سرور مشکلی پیش آمده است", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody respbody=response.body();
                respbodys=respbody.string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        filmresp=new Gson().fromJson(respbodys,FilmResp.class);
                        ////
                        Log.i("mohamamd_khalili",filmresp.f_budget);
                        /////////////>>>>>>
                        String json=new Gson().toJson(filmreq);
                        RequestBody requestb=RequestBody.create(MediaType.parse("application/json"),json);
                        Request request=new Request.Builder()
                                .url(new UrlReq().callServerFunction(5))
                                .post(requestb)
                                .build();
                        OkHttpClient okhttp=new OkHttpClient();
                        okhttp.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("erroronresp", e.getMessage());
                                Toast.makeText(MainFilmActivity.this, "متاسفانه در ازتباط با سرور مشکلی پیش آمده است", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final ResponseBody respbody=response.body();
                                respbodys=respbody.string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i("mohammad_khaliliresp",respbodys);
                                        Type listType = new TypeToken<ArrayList<CommentResp>>(){}.getType();
                                        comment=new Gson().fromJson(respbodys,listType);
                                        if(comment.get(0).status==true)

                                            adapter = new MainFilmAdapter(comment, filmresp, MainFilmActivity.this);
                                        else {
                                            Log.i("mohammad_khalili", "omadinja22");
                                            adapter = new MainFilmAdapter(null,filmresp, MainFilmActivity.this);
                                        }
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        recycler.setLayoutManager(mLayoutManager);
                                        recycler.setItemAnimator(new DefaultItemAnimator());
                                        recycler.setAdapter(adapter);
                                        progress.setVisibility(View.INVISIBLE);
                                    }
                                });


                            }
                        });

                    }
                });

                /////////////
                ///>>>>>>>>>

            }
        });

        floatingaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder commentdialog=new AlertDialog.Builder(MainFilmActivity.this);
                View mview= getLayoutInflater().inflate(R.layout.commentdialog,null);
                final EditText editText=(EditText) mview.findViewById(R.id.commenttext);
                Button btn=(Button) mview.findViewById(R.id.button);
                commentdialog.setView(mview);
                final AlertDialog alert=commentdialog.show();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String comment=editText.getText().toString();
                        AddCommentRequest addcomment=new AddCommentRequest();
                        addcomment.setComment(editText.getText().toString());
                        addcomment.setF_id(filmreq.getF_id());
                        String json=new Gson().toJson(addcomment);
                        alert.cancel();
                        Log.i("mohammad_khalili",json);
                        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);
                        Request myreq=new Request.Builder()
                                .url(new UrlReq().callServerFunction(8))
                                .post(requestBody)
                                .build();

                        OkHttpClient okhttp=new OkHttpClient();
                        okhttp.newCall(myreq).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call,final IOException e) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i("erroronresp", e.getMessage());
                                        Toast.makeText(MainFilmActivity.this, "متاسفانه در ازتباط با سرور مشکلی پیش آمده است", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call,final Response response) throws IOException {
                                ResponseBody rebody2=response.body();
                                commentresp=rebody2.string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainFilmActivity.this, "نظر شما ثبت شد", Toast.LENGTH_LONG).show();
                                        CommentResp commentresp=new CommentResp();
                                        commentresp.setComment(editText.getText().toString());
                                        commentresp.setUsername(user.getUsername());
                                        commentresp.setPropic(user.getProfilepic());
                                        commentresp.status=true;
                                        if(adapter.getComment()==null) {
                                           MainFilmActivity.comment=new ArrayList<>();
                                           MainFilmActivity.comment.add(commentresp);
                                           MainFilmAdapter.comment=MainFilmActivity.comment;
                                        }
                                        else{
                                            adapter.getComment().add(commentresp);
                                        }
                                        adapter.notifyDataSetChanged();
                                        alert.dismiss();









                                    }
                                });
                            }
                        });

                    }
                });

            }

        });



    }

    public void setitems(){
        recycler=(RecyclerView) findViewById(R.id.recyclerfilm);
        progress=(ProgressBar)findViewById(R.id.progressbar);
        floatingaction=(FloatingActionButton)findViewById(R.id.floatingaction);

    }
}
