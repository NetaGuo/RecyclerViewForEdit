package adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guoying.editrecyclerview.CheckAndDeletePicturesActivity;
import com.example.guoying.editrecyclerview.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import models.BBSContent;
import models.CommentPictureBeen;
import utils.CGlobalUtil;
import utils.FoundImageHelper;
import utils.IgetOneInt;
import utils.IgetOneStringTwoInt;
import utils.ImageLoadUtil;
import view.MonitorSelectionEditText;
import viewholder.BaseViewHolder;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class BBSContentAdapter extends BaseViewAdapter<BBSContent>{

    private boolean editOrShow = false;  //true为展示详情页
    private ImageLoadUtil imageloaderUtil;
    private IgetOneStringTwoInt igetOneStringTwoInt;
    private IgetOneInt firstListener;


    public BBSContentAdapter(Activity activity, boolean type, IgetOneStringTwoInt oneStringTwoInt) {
        super(activity);
        this.editOrShow = type;
        imageloaderUtil = ImageLoadUtil.create(activity);
        this.igetOneStringTwoInt = oneStringTwoInt;
    }

    public void setFirstListener(IgetOneInt firstListener) {
        this.firstListener = firstListener;
    }

    public void addItem(BBSContent bean, int position) {
        data.add(position, bean);
        notifyItemInserted(position);
    }

    public void addAndNotify(BBSContent bean, int position) {
        data.add(position, bean);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }


    private List<CommentPictureBeen> getImagesList(){

        int imagePosition=0;
        List<CommentPictureBeen> imagesList=new ArrayList<CommentPictureBeen>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getTextOrImage()==2){        //本地和网络图片均要设置
                data.get(i).setPosition(imagePosition);
                imagesList.add(new CommentPictureBeen(data.get(i).getContent()));
                imagePosition++;
            }
        }
        return imagesList;
    }

    @Override
    public RecyclerView.ViewHolder onNewCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ThemeEditTextViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.theme_edit_text_item, parent, false));
        }
        return new BBSContentViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.edit_text_image_item, parent, false));
    }

    @Override
    public void onNewBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BBSContentViewHolder) {
            BBSContentViewHolder holder1 = (BBSContentViewHolder) holder;
            holder1.setDataAndEvent(data.get(position), position);
        } else {
            ThemeEditTextViewHolder holder2 = (ThemeEditTextViewHolder) holder;
            holder2.setDataAndEvent(data.get(position));
        }
    }

    @Override
    public int getNewItemCount() {
        return data.size();
    }

    @Override
    public int getNewItemViewType(int position) {
        if (!editOrShow){
            if (position == 0) {
                return 0;
            }
        }
        return 1;
    }

    class ThemeEditTextViewHolder extends BaseViewHolder {

        EditText bbs_theme;

        public ThemeEditTextViewHolder(View itemView) {
            super(itemView);
            bbs_theme = getView(R.id.bbs_theme);
        }

        public void setDataAndEvent(final BBSContent bean) {

            if (bbs_theme.getTag() instanceof TextWatcher) {
                bbs_theme.removeTextChangedListener((TextWatcher) bbs_theme.getTag());
            }
            String content=bean.getContent();
            if (TextUtils.isEmpty(content)){
                bbs_theme.requestFocus();
            }
            bbs_theme.setText(content);
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    data.get(0).setContent(s.toString());
                }
            };
            bbs_theme.addTextChangedListener(textWatcher);
            bbs_theme.setTag(textWatcher);
        }
    }

    class BBSContentViewHolder extends BaseViewHolder {

        EditText bbs_edit_image_describe;
        MonitorSelectionEditText bbs_editText;
        TextView bbs_Text, bbs_image_describe;
        FrameLayout edit_image_frameLayout;
        ImageView bbs_editImageView, bbs_deleteImg;


        public BBSContentViewHolder(View itemView) {
            super(itemView);
            edit_image_frameLayout = getView(R.id.edit_image_frameLayout);
            bbs_editImageView = getView(R.id.bbs_editImageView);
            if (editOrShow) {  // 显示
                bbs_Text = getView(R.id.bbs_Text);
                bbs_image_describe = getView(R.id.bbs_image_describe);
            } else {
                bbs_editText = getView(R.id.bbs_editText);
                bbs_edit_image_describe = getView(R.id.bbs_edit_image_describe);
                bbs_deleteImg = getView(R.id.bbs_deleteImg);
            }

        }

        public void setDataAndEvent(final BBSContent bean, final int position) {
            if (bean.getTextOrImage() == 2) {
                bbs_editText.setVisibility(View.GONE);
                String path=bean.getContent();
                if (!TextUtils.isEmpty(path) && path.endsWith(".gif")){
                    imageloaderUtil.withGif(path,bbs_editImageView);
                }else{
                    FoundImageHelper.getInstance().display(path, bbs_editImageView, CGlobalUtil.CGlobalWithd(mActivity), 0); //glide显示体验效果不好
                }
                bbs_editImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击查看大图 左右滑动查看
                        Intent intent = new Intent(context, CheckAndDeletePicturesActivity.class);
                        intent.putExtra("pictureList", (Serializable) getImagesList());
                        intent.putExtra("position", data.get(position).getPosition());
                        intent.putExtra("intoType", "FOUND");
                        getActivity().startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });
                if (bbs_edit_image_describe.getTag() instanceof TextWatcher) {
                    bbs_edit_image_describe.removeTextChangedListener((TextWatcher) bbs_edit_image_describe.getTag());
                }
                bbs_edit_image_describe.setText(bean.getImageDescribe());
                TextWatcher imageDescribeWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        data.get(position).setImageDescribe(s.toString());
                    }
                };
                bbs_edit_image_describe.addTextChangedListener(imageDescribeWatcher);
                bbs_edit_image_describe.setTag(imageDescribeWatcher);
                bbs_deleteImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeItem(position);
                        if (TextUtils.isEmpty(data.get(position).getContent())) {
                            if (position > 1 && data.get(position-1).getTextOrImage()==1){
                                removeItem(position);
                            }
                        }
                    }
                });
                edit_image_frameLayout.setVisibility(View.VISIBLE);
                bbs_deleteImg.setVisibility(View.VISIBLE);
                bbs_edit_image_describe.setVisibility(View.VISIBLE);
            } else if (bean.getTextOrImage() == 1) {
                edit_image_frameLayout.setVisibility(View.GONE);
                bbs_editText.setVisibility(View.VISIBLE);
                if (position == 1) {
                    bbs_editText.setHint("内容");
                } else {
                    bbs_editText.setHint(null);
                }
                if (bbs_editText.getTag() instanceof TextWatcher) {
                    bbs_editText.removeTextChangedListener((TextWatcher) bbs_editText.getTag());
                }
                String text=bean.getContent();
                if (TextUtils.isEmpty(text)){
                    if (position!=1){
                        bbs_editText.requestFocus();
                    }
                }
                bbs_editText.setText(text);
                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String content=s.toString();
                        if (position==1 && firstListener!=null){ //监听点亮或暗淡发布按钮
                            if (!TextUtils.isEmpty(content)){
                                firstListener.getOneInt(1);
                            }else{
                                firstListener.getOneInt(-1);
                            }
                        }
                        data.get(position).setContent(content);
                        if (TextUtils.isEmpty(content)){
                            if (position > 1){
                                if (data.get(position-1).getTextOrImage() == 1 ||
                                        (data.size() > position+1 && data.get(position+1).getTextOrImage() == 1)){
                                    removeItem(position);
                                }
                            }else{
                                if (data.size() > position+1 && data.get(position+1).getTextOrImage() == 1){
                                    removeItem(position);
                                }
                            }
                        }

                    }
                };
                bbs_editText.addTextChangedListener(textWatcher);
                bbs_editText.setTag(textWatcher);
                bbs_editText.setOnTouchListener(null);
                View.OnTouchListener onTouchListener=new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                int length=bbs_editText.getText().length();
                                if (length > 0 && igetOneStringTwoInt != null) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            igetOneStringTwoInt.getOneStringOneInt(bbs_editText.getText().toString(), bbs_editText.getSelection(), position);
                                        }
                                    },300);
                                }
                                break;
                        }
                        return false;
                    }
                };
                bbs_editText.setOnTouchListener(onTouchListener);
            }
            if (position==1){
                if (firstListener!=null){
                    if (!TextUtils.isEmpty(data.get(1).getContent())){
                        firstListener.getOneInt(1);
                    }else {
                        firstListener.getOneInt(-1);
                    }
                }
            }
            if (position==2){
                if (firstListener!=null){
                    firstListener.getOneInt(1);
                }
            }
        }
    }
}
