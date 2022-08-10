package customfont;

import android.app.Application;

import com.example.mohammadkhalili.kelaket.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Calligraphy extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

}