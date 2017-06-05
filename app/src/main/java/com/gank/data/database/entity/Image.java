package com.gank.data.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
@Entity
public class Image {
    @Id
    Long id;
    String imageUrl;
    String imageId;
    @Generated(hash = 63671312)
    public Image(Long id, String imageUrl, String imageId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
    }

    public Image(String imageId) {
        this.imageId = imageId;
    }
    @Generated(hash = 1590301345)
    public Image() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageId() {
        return this.imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
  
}
