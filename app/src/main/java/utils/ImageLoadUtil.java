package utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class ImageLoadUtil {

    private static ImageLoadUtil imageloaderUtil;
    private Context mContext;
    private static int networkType = 2;
    private static int executeTime=0;

    public ImageLoadUtil(Context mContext) {
        this.mContext = mContext;
    }

    public static ImageLoadUtil init(Context mContext) {
        ImageLoadUtil mImageloaderUtil=new ImageLoadUtil(mContext.getApplicationContext());
        return mImageloaderUtil;
    }

    public static synchronized ImageLoadUtil create(Context ctx) {
        if (null == imageloaderUtil)
            imageloaderUtil = new ImageLoadUtil(ctx.getApplicationContext());
        return imageloaderUtil;
    }

    public void with(String imageUrl, ImageView imageView) {
        Glide.with(this.mContext).load(getUrlForWebp(imageUrl, imageView)).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(imageView);
    }

    public void with(int erroResId,String imageUrl, ImageView imageView) {
        Glide.with(this.mContext).load(getUrlForWebp(imageUrl, imageView)).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(erroResId).into(imageView);
    }

    public void with(int loadResId,int erroResId,String imageUrl, ImageView imageView) {
        Glide.with(this.mContext).load(getUrlForWebp(imageUrl, imageView)).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().placeholder(loadResId).error(erroResId).into(imageView);
    }

    public void withGif(String url,ImageView iv){
        Glide.with(this.mContext).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(iv);
    }

    public void withGif(int errorResId,String url,ImageView iv){
        Glide.with(this.mContext).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().error(errorResId).into(iv);
    }

    public void with(String imageUrl, ImageView imageView, int w, int h) {
        if(TextUtils.isEmpty(imageUrl)){
            return;
        }
        String url=imageUrl+getsize(imageUrl,w,h);
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().override(w,h).into(imageView);
    }
    private String getUrlForWebp(String imageUrl, ImageView imageView) {
        int w = imageView.getLayoutParams() == null ? imageView.getWidth() : imageView.getLayoutParams().width;
        int h = imageView.getLayoutParams() == null ? imageView.getHeight() : imageView.getLayoutParams().height;

        w = w < 50 ? 0 : w;
        h = h < 50 ? 0 : h;
        imageUrl = imageUrl + getsize(imageUrl, w, h);
        return imageUrl;
    }

    private String getsize(String uri, int w, int h) {

        StringBuffer size = new StringBuffer();
        if (uri.contains("@")) {
            size.append(".webp");
        } else if (uri.contains(".qiniucdn.com")) {
            if (networkType == 1 && w != 0 && h != 0) {
                w = w / 2;
                h = w / 2;
                size.append("?imageView2/2/w/");
                size.append(w);
                size.append("/h/");
                size.append(h);
                size.append("/q/100/format/webp");
                //size = "?imageView2/2/w/" + w + "/h/" + h + "/q/100/format/webp";
            } else {
                size.append("?imageView2/2/q/100/format/webp");
                //size = "?imageView2/2/q/100/format/webp";
            }
        } else if (uri.contains("image.tuhu.cn") || uri.contains(".alikunlun.com") || uri.matches(".*img\\d\\.tuhu\\.(org|cn).*")) {
            switch (networkType) {
                case 1://2g,3g
                    if (w == 0 || h == 0) {
                        size.append("@.webp");
                        //size = "@.webp";
                    } else {
                        size.append("@");
                        size.append(w / 2);
                        size.append("w_");
                        size.append(h / 2);
                        size.append("h");
                        size.append("_100q.webp");
                        //  size = "@" + w / 2 + "w" + "_" + h / 2 + "h" + "_100q.webp";
                    }
                    break;
                case 2://wifi
                    if (w == 0 || h == 0) {
                        size.append("@.webp");
                        // size = "@.webp";
                    } else {
                        size.append("@");
                        size.append(w);
                        size.append("w_");
                        size.append(h);
                        size.append("h");
                        size.append("_100q.webp");
                        // size = "@" + w + "w" + "_" + h + "h" + "_100q.webp";
                    }
                    break;
            }
        } else {
            size.append("");
            // size = "";
        }
        return size.toString();
    }
}
