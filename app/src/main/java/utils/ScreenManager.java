package utils;

import android.app.Application;
import android.content.Context;

import com.example.guoying.editrecyclerview.R;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class ScreenManager extends Application {

    private static Context context;
    private static ScreenManager instance;

    public static Context getContext() {
        return context;
    }

    public static ScreenManager getInstance() {
        if (null == instance) {
            instance = new ScreenManager();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this.getApplicationContext();
    }

    @Override
    public void setTheme(int resid) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            super.setTheme(R.style.AppBaseTheme2);
        } else {
            super.setTheme(resid);
        }
    }
}
