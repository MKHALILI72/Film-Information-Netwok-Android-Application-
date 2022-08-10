package com.example.mohammadkhalili.kelaket.userprofile;

import android.app.DownloadManager;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mohammadkhalili.kelaket.R;
import com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Model.GetListFilmRequest;
import Model.ListFilmResp;
import Server.UrlReq;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.user;

public class FavoriteFragment extends Fragment {
    /////instantiate back and recyclerview
    public ImageView back;
    public RecyclerView recyclerview;
    public ResponseBody responsebody;
    String bodyresp;
    public MainListFilmAdapter adapter;
    public ProgressBar pb;
    public static List<ListFilmResp> listfilmresp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view= inflater.inflate(R.layout.favoritefragment,container,false);
        back=(ImageView)view.findViewById(R.id.backicon);
        recyclerview=(RecyclerView)view.findViewById(R.id.favoritefilmrecycler);
        pb=(ProgressBar) view.findViewById(R.id.favoritefragmentprogress);
        /////////////////
        pb.setVisibility(View.VISIBLE);
        /////
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        /////
        GetListFilmRequest getfilmlistrequest=new GetListFilmRequest();
        getfilmlistrequest.setEmail(user.getEmail());
        getfilmlistrequest.setPassword(user.getPassword());
        getfilmlistrequest.setU_id(getArguments().getInt("userid"));
        String json=new Gson().toJson(getfilmlistrequest);
        String url= UrlReq.callServerFunction(10);
        RequestBody requestbody=RequestBody.create(MediaType.parse("application/json"),json);
        Request request=new  Request.Builder()
                .url(url)
                .post(requestbody)
                .build();
        OkHttpClient okhttpclient=new OkHttpClient();
        okhttpclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(),"متاسفانه در ارتباط با سرور مشکلی پیش آمده است",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responsebody=response.body();
                bodyresp =responsebody.string();
                Log.i("mohammad_khalili",bodyresp);
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {

                         if(bodyresp.equals("{"+'"'+"status"+'"'+":false}")) {
                             Log.i("mohammad_khalili", "inja");
                             Toast.makeText(view.getContext(), "لیست علاقمندی ها خالی است", Toast.LENGTH_LONG).show();
                             pb.setVisibility(View.INVISIBLE);
                         }else {

                             Type listType = new TypeToken<ArrayList<ListFilmResp>>() {}.getType();

                             listfilmresp = new Gson().fromJson(bodyresp, listType);

                             adapter = new MainListFilmAdapter(listfilmresp, getActivity().getApplicationContext());
                             RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                             recyclerview.setLayoutManager(mLayoutManager);
                             recyclerview.setItemAnimator(new DefaultItemAnimator());
                             recyclerview.setAdapter(adapter);
                             pb.setVisibility(View.INVISIBLE);
                         }


                     }
                 });
            }
        });

        return view;
    }
}
