package com.example.mohammadkhalili.kelaket.userprofile;

import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mohammadkhalili.kelaket.R;
import com.example.mohammadkhalili.kelaket.mainfilmpage.MainFilmAdapter;
import com.google.gson.Gson;

import java.io.IOException;

import Model.UserInfoReq;
import Model.UserInfoResponse;
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

import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.user;

public class UserInfoFragment extends Fragment {
    public ImageView back;
    public CircleImageView profilepic;
    public TextView username;
    public TextView description;
    public ResponseBody responsebody;
    public UserInfoResponse userinfo;
    public String bodyresp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view= inflater.inflate(R.layout.userinfofargment,container,false);
        back=(ImageView) view.findViewById(R.id.back);
        profilepic=(CircleImageView) view.findViewById(R.id.profilepic);
        username=(TextView) view.findViewById(R.id.username);
        description=(TextView) view.findViewById(R.id.description);

        /////check anotheruser or I
        String url= UrlReq.callServerFunction(9);
        UserInfoReq userprofileinfo=new UserInfoReq();
        userprofileinfo.setEmail(user.getEmail());
        userprofileinfo.setU_id(getArguments().getInt("userid"));
        userprofileinfo.setPassword(user.getPassword());
        String json=new Gson().toJson(userprofileinfo);
        Log.i("mohammad_khalili",json);
        RequestBody requestbody=RequestBody.create(MediaType.parse("application/json"),json);
        Request request=new Request.Builder()
                .url(url)
                .post(requestbody)
                .build();
        OkHttpClient okhttp=new OkHttpClient();
        okhttp.newCall(request).enqueue(new Callback() {
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
                     userinfo=new Gson().fromJson(bodyresp,UserInfoResponse.class);
                     if(userinfo.getStatus()){
                         Log.i("mohammad_khalili",userinfo.getProfilepic());
                         if(userinfo.getProfilepic().equals("")){

                         }else{
                             Glide.with(view.getContext())
                                     .load(Uri.parse(userinfo.getProfilepic()))
                                     .diskCacheStrategy(DiskCacheStrategy.NONE)
                                     .skipMemoryCache(true)
                                     .into(profilepic);
                         }
                         username.setText(userinfo.getUsername());
                         description.setText(userinfo.getDescription());

                     }else{
                         Toast.makeText(view.getContext(),"کاربر وجود ندارد",Toast.LENGTH_LONG).show();
                     }
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;

    }
}
