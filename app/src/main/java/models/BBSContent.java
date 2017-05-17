package models;

import utils.ListItem;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class BBSContent implements ListItem{

    private String content;
    private int position;        //图片的顺序
    private String imageDescribe;
    private int textOrImage;     //1 text   2 Image

    public BBSContent(){}

    public BBSContent(int type){
        this.textOrImage=type;
    }

    public BBSContent(int type,String text){
        this.textOrImage=type;
        this.content=text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImageDescribe() {
        return imageDescribe;
    }

    public void setImageDescribe(String imageDecribe) {
        this.imageDescribe = imageDecribe;
    }

    public void setTextOrImage(int textOrImage) {
        this.textOrImage = textOrImage;
    }

    public int getTextOrImage() {
        return textOrImage;
    }


    @Override
    public BBSContent newObject() {
        return new BBSContent();
    }
}
