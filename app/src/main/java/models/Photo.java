package models;

import java.io.Serializable;

/**
 * Created  on 2016/7/18 15:24
 * Author guoying
 * Description:照片实体类
 */
public class Photo implements Serializable {

    private int id;
    private String path;  //路径

    public Photo(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
