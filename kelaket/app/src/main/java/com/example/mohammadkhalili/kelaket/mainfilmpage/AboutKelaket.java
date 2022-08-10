package com.example.mohammadkhalili.kelaket.mainfilmpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.mohammadkhalili.kelaket.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutKelaket extends AppCompatActivity {
    public ImageView backicon;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutkelaket);
        backicon=(ImageView)findViewById(R.id.back);
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
