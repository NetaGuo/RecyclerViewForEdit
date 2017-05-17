package utils;

import android.content.Context;
import android.widget.Toast;

public class RegexUtil {

	private static Toast mToast;
	public static void showInfo(Context context, int messageid,boolean time) {
		String msg="";
		try{
			msg=context.getString(messageid);
		}catch (Exception e){
			msg=messageid+"";
		}
		showInfo(context,msg,time);
	}
	public static void showInfo(Context context, String message,boolean time) {
		if(mToast!=null){
			mToast.cancel();
		}
		if (time) {
			mToast=Toast.makeText(context, message, Toast.LENGTH_LONG);
		}else{
			mToast=Toast.makeText(context, message, Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static boolean isValidMobiNumber(String paramString) {
		paramString = paramString.replace(" ","");
		String regex = "^1\\d{10}$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidMobiCode(String paramString) {
		String regex = "^\\d{4}$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}
	public static boolean isValidInt(String paramString) {
		String regex = "^\\d+$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}
    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){

        if(s.indexOf(".") > 0){
         s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }

        return s;
    }

}
