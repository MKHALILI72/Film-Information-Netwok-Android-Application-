package com.example.mohammadkhalili.kelaket.mainfilmpage;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mohammadkhalili.kelaket.R;
import com.example.mohammadkhalili.kelaket.loginpackage.login;
import com.example.mohammadkhalili.kelaket.userprofile.EditeProfile;
import com.example.mohammadkhalili.kelaket.userprofile.userprofile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Model.ListFilmRequest;
import Model.ListFilmResp;
import Model.Raterequest;
import Model.User;
import Server.UrlReq;
import de.hdodenhof.circleimageview.CircleImageView;
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
 * Created by mohammad khalili on 7/23/2018.
 */

public class MainListFilmActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public List<ListFilmResp> film=new ArrayList<>();
    public String resp;
    public MainListFilmAdapter adapter;
    public static Raterequest rate;
    public static User user;
    public ImageView filterbtn;
    public ImageView navdrawerbtn;
    public DrawerLayout drawer;
    public NavigationView navigationview;
    static public CircleImageView avatar;
    static public TextView username;
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlistfilmview);
        getApplicationContext();
        Log.i("kh","sssss");
        setitems();
        Log.i("mohammad_khalili2",user.getProfilepic());
        if(user.getProfilepic().equals("")){

        }else{
            Glide.with(MainListFilmActivity.this)
                    .load(user.getProfilepic())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(avatar);
        }
        Log.i("mohammad_khalili2",user.getUsername());
        username.setText(user.getUsername());
        /////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////
        ////handle navigationdrawe
        ///////////////////////////////////////////////////////////
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("mohammad_khalili",Integer.toString(item.getItemId()));
                item.setCheckable(true);
                if(R.id.nav_profile==item.getItemId()){
                    Intent intent=new Intent(MainListFilmActivity.this,userprofile.class);
                    startActivity(intent);
                    drawer.closeDrawers();
                }
                if(R.id.nav_exit==item.getItemId()){
                    SharedPreferences.Editor editor = getSharedPreferences("saveuserinfo", MODE_PRIVATE).edit();
                    editor.putString("user",null);
                    editor.commit();
                    Intent in=new Intent(MainListFilmActivity.this,login.class);
                    startActivity(in);
                    finish();
                }
                if(R.id.nav_edite==item.getItemId()){
                    Intent in=new Intent(MainListFilmActivity.this, EditeProfile.class);
                    startActivity(in);
                    drawer.closeDrawers();
                }
                if(R.id.nav_share==item.getItemId()){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "کلاکت را از بازار دانلود کنید"
                            );
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                if (R.id.nav_aboutkelaket==item.getItemId()){
                    Intent sendIntent=new Intent(MainListFilmActivity.this,AboutKelaket.class);
                    startActivity(sendIntent);
                }
                return false;
            }
        });
        navdrawerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.START);
            }
        });
        Log.i("mohammad_khalili","sakhteshod2");
        if(user.getIslogin()==true) {
            ListFilmRequest getfilm = new ListFilmRequest();
            getfilm.setEmail(user.getEmail());
            Log.i("mohammad_khalili",user.getPassword());
            getfilm.setPassword(user.getPassword());
            getfilm.setSearch("");
            getfilm.setGenre("");
            getfilm.setCountry("");
            callServer(getfilm);


        }
        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainListFilmActivity.this);
                View myview=getLayoutInflater().inflate(R.layout.filter,null);
                alertdialog.setView(myview);

                final Spinner genrespin=(Spinner) myview.findViewById(R.id.spinner_genre);
                Spinner countryspin=(Spinner) myview.findViewById(R.id.spinner_country);
                final EditText editetext=(EditText) myview.findViewById(R.id.searchtxt);
                Button btn=myview.findViewById(R.id.okbtn);
                ArrayAdapter<CharSequence> genre_adapter = ArrayAdapter.createFromResource(MainListFilmActivity.this,
                        R.array.film_genre, android.R.layout.simple_spinner_item);
                ArrayAdapter<CharSequence> country_adapter = ArrayAdapter.createFromResource(MainListFilmActivity.this,
                        R.array.film_country,android.R.layout.simple_spinner_item);
                genre_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                country_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                genrespin.setAdapter(genre_adapter);
                countryspin.setAdapter(country_adapter);
                alertdialog.create().show();
                final String[] genre = new String[1];
                final String[] country = new String[1];
                genrespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,int pos, long l) {
                                genre[0] =adapterView.getItemAtPosition(pos).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                countryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                country[0] =adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListFilmRequest filmreq=new ListFilmRequest();
                        filmreq.setCountry(country[0]);
                        filmreq.setGenre(genre[0]);
                        filmreq.setSearch(editetext.getText().toString());
                        filmreq.setEmail(user.getEmail());
                        filmreq.setPassword(user.getPassword());
                        callServer(filmreq);
                    }
                });
            }
        });
    }

    public void setitems(){

         recyclerView=(RecyclerView) findViewById(R.id.recycler);
         filterbtn=(ImageView) findViewById(R.id.filterbtn);
         navdrawerbtn=(ImageView) findViewById(R.id.navdrawerbtn);
         drawer=(DrawerLayout)findViewById(R.id.nav_view);
         navigationview=(NavigationView)findViewById(R.id.insidenavview);
         avatar=(CircleImageView) navigationview.getHeaderView(0).findViewById(R.id.avatar);
         username=(TextView) navigationview.getHeaderView(0).findViewById(R.id.username);

    }

    public void callServer(ListFilmRequest getfilm){
        UrlReq urlreq=new UrlReq();
        String functionasdress=urlreq.callServerFunction(2);
        String json = new Gson().toJson(getfilm);
        Log.i("mohammad_khalilifilter",json);
        RequestBody reqbody = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(functionasdress)
                .post(reqbody)
                .build();
        OkHttpClient okhttp = new OkHttpClient();
        Log.i("mohammad_khalili",json);
        try {
        okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("erroronresp", e.getMessage());
                Toast.makeText(MainListFilmActivity.this, "متاسفانه در ازتباط با سرور مشکلی پیش آمده است", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody reb = response.body();
                resp = reb.string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Type listType = new TypeToken<ArrayList<ListFilmResp>>(){}.getType();
                        film= new Gson().fromJson(resp,listType);
                        Log.i("mohammad_khalili",resp);
                        Log.i("mohammad_khalili",Integer.toString(film.size()));
                        adapter=new MainListFilmAdapter(film,getApplicationContext());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

        }
        catch (ExceptionInInitializerError exception){
            Toast.makeText(MainListFilmActivity.this, "متاسفانه در ازتباط با سرور مشکلی پیش آمده است", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("mohammad_khalili","onstatesaveins");
        String myuser=new Gson().toJson(user);
        outState.putString("saveuser",myuser);
    }
}

