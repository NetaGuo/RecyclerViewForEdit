package utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Photo;
import models.PhotoFloder;

/**
 * Created  on 2016/7/18 15:26
 * Author guoying
 * Description:图片获取帮助类
 */
public class PhotoUtils {

    public static Map<String, PhotoFloder> getPhotos(Context context) {
        Map<String, PhotoFloder> floderMap = new HashMap<String, PhotoFloder>();

        String allPhotosKey = "所有图片";
        PhotoFloder allFloder = new PhotoFloder();
        allFloder.setName(allPhotosKey);
        allFloder.setDirPath(allPhotosKey);
        allFloder.setPhotoList(new ArrayList<Photo>());
        floderMap.put(allPhotosKey, allFloder);

        Uri imageUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();
//        String columns[] = new String[] { Media._ID, Media.BUCKET_ID,
//               Media.PICASA_ID, Media.DATA,Media.DISPLAY_NAME,Media.TITLE,
//              Media.SIZE,Media.BUCKET_DISPLAY_NAME };
        Cursor mCursor = mContentResolver.query(imageUri,null, null,null,Media.DATE_ADDED+" desc");
        if (mCursor!=null){
            int pathIndex = mCursor
                    .getColumnIndex(Media.DATA);
            mCursor.moveToFirst();
            if (!mCursor.moveToFirst()){
                mCursor.close();
                return floderMap;
            }
            do {
                // 获取图片的路径
                String path = mCursor.getString(pathIndex);


                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                String dirPath = parentFile.getAbsolutePath();

                if (floderMap.containsKey(dirPath)) {
                    Photo photo = new Photo(path);
                    PhotoFloder photoFloder = floderMap.get(dirPath);
                    photoFloder.getPhotoList().add(photo);
                    floderMap.get(allPhotosKey).getPhotoList().add(photo);
                    continue;
                } else {
                    // 初始化imageFloder
                    PhotoFloder photoFloder = new PhotoFloder();
                    List<Photo> photoList = new ArrayList<Photo>();
                    Photo photo = new Photo(path);
                    photoList.add(photo);
                    photoFloder.setPhotoList(photoList);
                    photoFloder.setDirPath(dirPath);
                    photoFloder.setName(dirPath.substring(dirPath.lastIndexOf(File.separator) + 1, dirPath.length()));
                    floderMap.put(dirPath, photoFloder);
                    floderMap.get(allPhotosKey).getPhotoList().add(photo);
                }
            }while (mCursor.moveToNext());
            mCursor.close();
        }
        return floderMap;
    }
}
