package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.guoying.editrecyclerview.R;

import java.util.List;

import models.CommentPictureBeen;
import utils.CGlobalUtil;
import utils.FoundImageHelper;
import utils.ImageLoadUtil;
import view.TouchImageView;

/**
 * Created by Jin on 2016/4/13.
 * Edited By Young On 2016/06/20
 */
public class PhotoPagerAdapter extends PagerAdapter {

    private List<CommentPictureBeen> mData;
    private LayoutInflater mLayoutInflater;
    private String intoType;
    private ClickToFinish clickToFinish;
    private Context mContext;
    private ImageLoadUtil imageloaderUtil;

    public PhotoPagerAdapter(@NonNull Context context, @NonNull List<CommentPictureBeen> data, String intoType) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mData = data;
        this.intoType = intoType;
        mContext = context;
        imageloaderUtil=ImageLoadUtil.create(context);
    }

    public void setClickToFinish(ClickToFinish clickToFinish) {
        this.clickToFinish = clickToFinish;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        CommentPictureBeen data = mData.get(position);
        String path=data.getPicture();
        if (path.endsWith(".gif")){
            ImageView view = (ImageView) mLayoutInflater.inflate(R.layout.bbs_gif_image_view, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickToFinish!=null){
                        clickToFinish.finishThis();
                    }
                }
            });
            imageloaderUtil.withGif(path,view);
            container.addView(view);
            return view;
        }
        View view = mLayoutInflater.inflate(R.layout.view_zoom_photo, null);
        TouchImageView photoView = (TouchImageView) view.findViewById(R.id.photo_view_zoom);
        if (!TextUtils.isEmpty(path)) {
            if ("FOUND".equals(intoType) ){
                if (path.contains("http")){
                    imageloaderUtil.with(R.drawable.lable_zhanwei,R.drawable.lable_zhanwei,path,photoView);
                }else{
                    FoundImageHelper.getInstance().display(path, photoView, CGlobalUtil.CGlobalWithd(mContext), 0);
                }
            }else{
                imageloaderUtil.with(R.drawable.lable_zhanwei,R.drawable.lable_zhanwei,data.getPicture(),photoView);
            }
        }
        photoView.setOnSingleTapListener(new TouchImageView.OnSingleTapListener() {
            @Override
            public void onSingleTap(boolean singleTap) {
                if (singleTap) {
                    mOnPhotoPagerTouchListener.onSingleTap();
                }
            }
        });
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickToFinish!=null){
                    clickToFinish.finishThis();
                }
            }
        });
        container.addView(view);
        return view;
    }

    private OnPhotoPagerTouchListener mOnPhotoPagerTouchListener;

    public void setOnPhotoPagerTouchListener(OnPhotoPagerTouchListener listener) {
        this.mOnPhotoPagerTouchListener = listener;
    }

    public interface OnPhotoPagerTouchListener {
        void onSingleTap();
    }

    public interface  ClickToFinish{
        void finishThis();
    }
}
