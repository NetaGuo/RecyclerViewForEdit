package utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Display;

import java.util.List;

/**
 * Created by chang on 2016/6/6.
 */
public class CGlobalUtil {
    public static Display getDefaultDisplay(Context Activity) {
        return ((Activity) Activity).getWindowManager().getDefaultDisplay();
    }

    public static int CGlobalWithd(Context Activity) {
        if (CGlobal.withd <= 0 && Activity != null) {
            CGlobal.withd = getDefaultDisplay(Activity).getWidth();
        }

        return CGlobal.withd;
    }

    public static int CGlobalHeight(Context Activity) {
        if (CGlobal.height <= 0 && Activity != null) {
            CGlobal.height = getDefaultDisplay(Activity).getHeight();
        }
        return CGlobal.height;
    }


    public static boolean isHasWeixinApp(Context context){

        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

}

