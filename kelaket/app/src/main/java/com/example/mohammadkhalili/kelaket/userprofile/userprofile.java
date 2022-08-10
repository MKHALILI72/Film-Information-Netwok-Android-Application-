package com.example.mohammadkhalili.kelaket.userprofile;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mohammadkhalili.kelaket.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.user;


public class userprofile extends FragmentActivity {


    public BottomBar bottombar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setitem();

           bottombar.setOnTabSelectListener(new OnTabSelectListener() {
               @Override
               public void onTabSelected(int tabId) {
                   if(R.id.tab_favorites==tabId){
                       FavoriteFragment favorite=new FavoriteFragment();
                       FragmentTransaction fragment=getSupportFragmentManager().beginTransaction();
                       fragment.replace(R.id.contentContainer,favorite);
                       Bundle b=new Bundle();
                       b.putInt("userid",user.getId());
                       favorite.setArguments(b);
                       fragment.commit();

                   }
                   else{
                       UserInfoFragment favorite=new UserInfoFragment();
                       FragmentTransaction fragmenttwo=getSupportFragmentManager().beginTransaction();
                       fragmenttwo.replace(R.id.contentContainer,favorite);
                       Bundle b=new Bundle();
                       b.putInt("userid",user.getId());
                       favorite.setArguments(b);
                       fragmenttwo.commit();

                   }
               }
           });





    }
    public void setitem(){
       bottombar=(BottomBar) findViewById(R.id.bottomBar);
    }
}

