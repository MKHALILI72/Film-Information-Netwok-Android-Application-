package com.example.mohammadkhalili.kelaket.userprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mohammadkhalili.kelaket.R;
import com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import Model.Base64request;
import Model.EditeUserRequest;
import Model.User;
import Server.UrlReq;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.user;

public class EditeProfile extends AppCompatActivity {
    public ImageView backic;
    public TextView okic;
    public EditText username;
    public EditText password;
    public EditText repassword;
    public EditText description;
    public ImageView addpic;
    public ImageView propic;
    public byte[] bytearr=null;
    public boolean proflag=false;
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editeprofile);
        setitems();
        setviewpage();

        /////////////////////////////////////////////
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        okic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(bytearr!=null) {
                   Log.i("mohammad_khalili222",Boolean.toString(proflag));
                   sendServerProPic(bytearr);
                   proflag=true;
                   Log.i("mohammad_khalili222",Boolean.toString(proflag));
               }
               if(username.getText().toString().equals("")||password.getText().toString().equals("")||repassword.getText().toString().equals(""))
               {
                   Toast.makeText(EditeProfile.this,"یکی از فیلدها خالی است",Toast.LENGTH_LONG).show();
               }else {
                   if(repassword.getText().toString().equals(password.getText().toString())) {
                       String url = new UrlReq().callServerFunction(13);
                       final EditeUserRequest editeuserreq=new EditeUserRequest();
                       editeuserreq.setIslogin(true);
                       editeuserreq.setId(user.getId());
                       editeuserreq.setUsername(username.getText().toString());
                       editeuserreq.setEmail(user.getEmail());
                       editeuserreq.setDescription(description.getText().toString());
                       editeuserreq.setPassword(user.getPassword());
                       editeuserreq.setNpassword(password.getText().toString());
                       if(user.getProfilepic().equals("")) {
                           editeuserreq.setProfilepic("");
                       }else{
                           editeuserreq.setProfilepic(user.getProfilepic());
                       }
                       if(proflag==true)
                       {
                           editeuserreq.setProfilepic("http://172.17.100.2/proimg/"+editeuserreq.getEmail()+".jpg");
                           proflag=false;
                       }

                       String json =new Gson().toJson(editeuserreq) ;
                       Log.i("mohammad_khalili",json);
                       RequestBody requestbody = RequestBody.create(MediaType.parse("application/json"), json);
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
                                       Toast.makeText(EditeProfile.this,"اشکال در ارتباط با سرور",Toast.LENGTH_LONG).show();
                                   }
                               });
                           }

                           @Override
                           public void onResponse(Call call, Response response) throws IOException {
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       user.setProfilepic(editeuserreq.getProfilepic());
                                       user.setPassword(editeuserreq.getNpassword());
                                       user.setUsername(editeuserreq.getUsername());
                                       user.setDescription(editeuserreq.getDescription());
                                       SharedPreferences.Editor editor = getSharedPreferences("saveuserinfo", MODE_PRIVATE).edit();
                                       String userstr=new Gson().toJson(user);
                                       editor.putString("user",userstr);
                                       editor.commit();
                                       Log.i("mohammad_khalili",user.getProfilepic());
                                       Toast.makeText(EditeProfile.this,"اطلاعات ویرایش شد",Toast.LENGTH_SHORT).show();

                                   }
                               });
                           }
                       });
                   }
                   else{
                       Toast.makeText(EditeProfile.this,"رمز عبور و تکرار آن مشابهت ندارد",Toast.LENGTH_LONG).show();
                   }
               }
           }
       });
       addpic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              selectImage();

           }
           private void selectImage() {
               final CharSequence[] items = { "دوربین","گالری",
                       "انصراف" };
               AlertDialog.Builder builder = new AlertDialog.Builder(EditeProfile.this);
               builder.setTitle("انتخاب عکس");
               builder.setItems(items, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int item) {
                     if(item==0){
                         Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                         if (intent.resolveActivity(getPackageManager()) != null) {
                             startActivityForResult(intent, 0);
                         }
                     }
                     if (item==1){
                         Intent intent=new Intent(Intent.ACTION_PICK,
                                 android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         startActivityForResult(intent,1);
                     }

                   }
               });
               builder.show();
           }

       });


    }
    ////////////////////////////////////////////////////////////

    public void setitems(){
        backic=(ImageView)findViewById(R.id.back);
        okic=(TextView) findViewById(R.id.ok);;
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.repassword);
        description=(EditText) findViewById(R.id.description);
        addpic=(ImageView) findViewById(R.id.addpic);
        propic=(ImageView) findViewById(R.id.profilepic);
    }
    public void setviewpage(){
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        repassword.setText(user.getPassword());
        description.setText(user.getDescription());

        if(user.getProfilepic().equals("")){

        }else{
            Log.i("mohammmad_khalilipic","fff"+user.getProfilepic());
            Glide.with(this).load(user.getProfilepic())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(propic);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:if(resultCode == RESULT_OK) {
                Log.i("mohammad_khalili","varedshod22");
                Bitmap image=(Bitmap)imageReturnedIntent.getExtras().get("data");
                propic.setImageBitmap(image);
                ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytearray);
                bytearr=bytearray.toByteArray();




            }break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri uri=(Uri)imageReturnedIntent.getData();
                    Glide.with(this).load(uri).centerCrop().into(propic);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        ByteArrayOutputStream bytearray=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytearray);
                        bytearr=bytearray.toByteArray();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
        }
    }

    public void sendServerProPic(byte[] arraybyte){
        String myimagestr=Base64.encodeToString(arraybyte,Base64.DEFAULT);
        Log.i("mohammad_khalili",myimagestr);
        Base64request bas64re=new Base64request();
        bas64re.setBase64str(myimagestr);
        bas64re.setEmail(user.getEmail());
        String json=new Gson().toJson(bas64re);
        RequestBody request=RequestBody.create(MediaType.parse("application/json"),json);
        Log.i("mohammad_khalili",json);
        Request req=new Request.Builder()
                .url(new UrlReq().callServerFunction(11))
                .post(request)
                .build();
        OkHttpClient ok=new OkHttpClient();
        ok.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Toast.makeText(EditeProfile.this,"اشکال در ارتباط با سرور",Toast.LENGTH_LONG).show();
                     }
                 });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        });

    }
}