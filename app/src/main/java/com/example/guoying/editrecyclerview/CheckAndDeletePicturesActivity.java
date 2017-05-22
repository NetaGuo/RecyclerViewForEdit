package com.example.guoying.editrecyclerview;

import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import adapter.PhotoPagerAdapter;
import models.CommentPictureBeen;

/**
 * Created  on 2016/8/3 10:01
 * Author guoying
 * Description:提问 回答 传图过程查看可删除 问题详情页 回答详情页 查看多图
 */
public class CheckAndDeletePicturesActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{

    private ViewPager viewpager_activity_check_photo;
    private TextView activity_check_photo_position,activity_check_photo_pager_num;//当前照片位置 总图片数
    private RelativeLayout activity_check_photo_header;
    private List<CommentPictureBeen> picturesList;
    private ImageView delete_btn;
    private int imgPosition,imgTotalNum;
    private String intoType;
    private PhotoPagerAdapter adapter;
    private boolean canDeleteOrNot=false,isShareImage=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_and_delete_pictures);
        initIntentParams();
        initView();
        setViewPager();
    }

    private void initIntentParams() {
        picturesList= (List<CommentPictureBeen>) getIntent().getSerializableExtra("pictureList");
        imgPosition=getIntent().getIntExtra("position",0);
        imgTotalNum=picturesList!=null?picturesList.size():0;
        intoType=getIntent().getStringExtra("intoType");
        canDeleteOrNot=getIntent().getBooleanExtra("canDeleteOrNot",false);
        isShareImage=getIntent().getBooleanExtra("isShareImage",false);
    }

    private void initView() {
        viewpager_activity_check_photo= (ViewPager) findViewById(R.id.viewpager_activity_check_photo);
        viewpager_activity_check_photo.addOnPageChangeListener(this);
        activity_check_photo_header= (RelativeLayout) findViewById(R.id.activity_check_photo_header);
        findViewById(R.id.activity_check_photo_back).setOnClickListener(this);
        activity_check_photo_position= (TextView) findViewById(R.id.activity_check_photo_position);
        activity_check_photo_position.setText(imgPosition+1+"");
        activity_check_photo_pager_num= (TextView) findViewById(R.id.activity_check_photo_pager_num);
        activity_check_photo_pager_num.setText(imgTotalNum+"");
        delete_btn= (ImageView) findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(this);
        if (canDeleteOrNot){
            delete_btn.setVisibility(View.VISIBLE);
        }else {
            delete_btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTheme(@StyleRes int resid) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            super.setTheme(R.style.AppBaseTheme2);
        } else {
            super.setTheme(resid);
        }
    }

    private void setViewPager() {
        if (picturesList == null) {
            return;
        }
        adapter = new PhotoPagerAdapter(CheckAndDeletePicturesActivity.this, picturesList, intoType);
        viewpager_activity_check_photo.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewpager_activity_check_photo.setCurrentItem(imgPosition);
        adapter.setOnPhotoPagerTouchListener(new PhotoPagerAdapter.OnPhotoPagerTouchListener() {
            @Override
            public void onSingleTap() {
                activity_check_photo_header.setVisibility(activity_check_photo_header.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        adapter.setClickToFinish(new PhotoPagerAdapter.ClickToFinish() {
            @Override
            public void finishThis() {
                finish();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        imgPosition=position;
        activity_check_photo_position.setText((position+1)+"");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.activity_check_photo_back:

                break;
            case R.id.delete_btn:

                break;
            default:
                break;
        }
    }

}
