package models;
import utils.ListItem;

/**
 * Created by Jin on 2016/4/12.
 */
public class CommentPictureBeen implements ListItem{

    private String picture;
    private int commentPosition;

    public CommentPictureBeen(){}

    public CommentPictureBeen(String imageUrl){
        this.picture=imageUrl;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCommentPosition() {
        return commentPosition;
    }

    public void setCommentPosition(int commentPosition) {
        this.commentPosition = commentPosition;
    }

    @Override
    public String toString() {
        return "CommentPictureBeen{" +
                "picture='" + picture + '\'' +
                ", commentPosition=" + commentPosition +
                '}';
    }

    @Override
    public CommentPictureBeen newObject() {
        return new CommentPictureBeen();
    }

}
