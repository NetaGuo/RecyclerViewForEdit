package com.example.guoying.editrecyclerview;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.BBSContentAdapter;
import models.BBSContent;
import utils.DefaultItemTouchHelper;
import utils.IgetOneInt;
import utils.IgetOneStringTwoInt;
import utils.ItemTouchHelperCallBack;
import utils.ScreenManager;
import view.XRecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IgetOneStringTwoInt {

    private TextView bbs_cancel,bbs_publish;
    private XRecyclerView bbs_content_and_image;
    private ImageView bbs_choose_pic;
    private BBSContentAdapter adapter;
    private int selection=-2,position=-1;
    private String editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenManager.getInstance();
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

        bbs_cancel= (TextView) findViewById(R.id.bbs_cancel);
        bbs_cancel.setOnClickListener(this);
        bbs_publish= (TextView) findViewById(R.id.bbs_publish);
        bbs_publish.setOnClickListener(this);
        setButtonStatus(false,R.color.gray,"您还没有输入任何内容哦~");
        bbs_content_and_image= (XRecyclerView) findViewById(R.id.bbs_content_and_image);
        DefaultItemTouchHelper defaultItemTouchHelper=new DefaultItemTouchHelper(onItemTouchCallbackListener);
        defaultItemTouchHelper.attachToRecyclerView(bbs_content_and_image);
        defaultItemTouchHelper.setDragEnable(true);
        bbs_choose_pic= (ImageView) findViewById(R.id.bbs_choose_pic);
        bbs_choose_pic.setOnClickListener(this);
        adapter=new BBSContentAdapter(MainActivity.this,false,this);
        adapter.setFirstListener(new IgetOneInt() {
            @Override
            public void getOneInt(int arg0) {
                if (arg0 < 0){
                    setButtonStatus(false,R.color.gray,"您还没有输入任何内容哦~");
                }else {
                    setButtonStatus(true,R.color.blue,null);
                }
            }
        });
        bbs_content_and_image.init(adapter);
        List<BBSContent> list=new ArrayList<BBSContent>();
        list.add(new BBSContent());
        list.add(new BBSContent(1));
        adapter.setData(list);
    }

    private ItemTouchHelperCallBack.OnItemTouchCallbackListener onItemTouchCallbackListener = new ItemTouchHelperCallBack.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if (adapter.getData() != null) {
                // 更换数据源中的数据Item的位置
                Collections.swap(adapter.getData(), srcPosition, targetPosition);
                // 更新UI中的Item的位置，主要是给用户看到交互效果
                adapter.notifyItemMoved(srcPosition, targetPosition);
                return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bbs_cancel:
                onBackPressed();
                break;
            case R.id.bbs_publish:
                if (bbs_publish.getTag()!=null){
                    Toast.makeText(MainActivity.this,bbs_publish.getTag().toString(),Toast.LENGTH_SHORT);
                    return;
                }

                break;
            case R.id.bbs_choose_pic:
                Intent intent = new Intent(MainActivity.this, PhotosPickerActivity.class);
                intent.putExtra(PhotosPickerActivity.EXTRA_SELECT_MODE, PhotosPickerActivity.MODE_SINGLE);
                intent.putExtra(PhotosPickerActivity.EXTRA_SHOW_CAMERA, true);
                startActivityForResult(intent,1);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (data!=null){
                ArrayList<String> result = data.getStringArrayListExtra(PhotosPickerActivity.KEY_RESULT);
                if (result!=null && result.size()>0){
                    final BBSContent one=new BBSContent(2,result.get(0));
                    if (selection != -2){
                        if (selection==-1){
                            adapter.addAndNotify(one,position+1);
                            selection=-2;
                            position=-1;
                        }else if (selection==0){
                            adapter.addAndNotify(one,position-1);
                            selection=-2;
                            position=-1;
                        }else{
                            String onePart=editTextContent.substring(0,selection);
                            final String twoPart=editTextContent.substring(selection,editTextContent.length());
                            adapter.getData().get(position).setContent(onePart);
                            adapter.addAndNotify(one,position+1);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.addAndNotify(new BBSContent(1,twoPart),position+2);
                                    selection=-2;
                                    position=-1;
                                }
                            },500);
                        }
                    }else{
//                        boolean isDelete=false;
//                        int location=adapter.getData().size()-1;
//                        String content = adapter.getData().get(location).getContent();
//                        if (TextUtils.isEmpty(content) || "null".equals(content)) {
//                            adapter.removeItem(location);
//                            isDelete = true;
//                        }
//                        if (isDelete){
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    adapter.addItem(one,adapter.getData().size());
//                                }
//                            },500);
//                        }else{
//                            adapter.addItem(one,adapter.getData().size());
//                        }

                        adapter.addItem(one,adapter.getData().size());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addItem(new BBSContent(1),adapter.getData().size());
                                bbs_content_and_image.scrollToPosition(adapter.getData().size()-1);
                            }
                        },500);
                    }
                }
            }
        }
    }
    private void setButtonStatus(boolean enable,int color,String tag){
        bbs_publish.setEnabled(enable);
        bbs_publish.setTextColor(getResources().getColor(color));
        bbs_publish.setTag(tag);
    }

    /**
     * @param arg1 EdieText文本
     * @param arg2 文本截取位置
     * @param arg3 EditText当前位置
     */
    @Override
    public void getOneStringOneInt(String arg1, int arg2, int arg3) {
        int length=arg1.length();
        this.editTextContent = arg1;
        this.position = arg3;
        if (length == arg2){
            if (adapter.getData().size() > position+1){
                if (adapter.getData().get(position+1).getTextOrImage()==2){
                    this.selection = -1;
                }else{
                    this.selection = -2;
                }
            }else{
                this.selection = -2;
            }
        }else{
            this.selection = arg2; //0-length之间
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
}
