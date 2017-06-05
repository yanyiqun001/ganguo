package com.gank.data.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.gank.data.database.entity.DaoSession;
import com.gank.data.database.entity.Image;
import com.gank.data.database.entity.ImageDao;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21 0021.
 */
@Entity
public class Result implements Parcelable{
    /**
     * _id : 58d4e454421aa93abd1fd15a
     * createdAt : 2017-03-24T17:18:12.745Z
     * desc : RecyclerView侧滑菜单
     * images : ["http://img.gank.io/99a9d510-195d-4d50-a310-13b098c0c776"]
     * publishedAt : 2017-03-29T11:48:49.343Z
     * source : web
     * type : Android
     * url : http://www.jianshu.com/p/af9f940d8d1c
     * used : true
     * who : pss
     */
    @Id
    @SerializedName("_id")
    private String id;
    @SerializedName("ganhuo_id")
    private String ganhuo_id;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("desc")
    private String desc;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("source")
    private String source;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;
    @SerializedName("used")
    private boolean used;
    @SerializedName("who")
    private String who;
    @SerializedName("images")
    @Transient
    private List<String> images;

    @ToMany(referencedJoinProperty = "imageId")
    private List<Image> img;


    public static Result objectFromData(String str) {

        return new Gson().fromJson(str, Result.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    public boolean getUsed() {
        return this.used;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1210770188)
    public List<Image> getImg() {
        if (img == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ImageDao targetDao = daoSession.getImageDao();
            List<Image> imgNew = targetDao._queryResult_Img(id);
            synchronized (this) {
                if (img == null) {
                    img = imgNew;
                }
            }
        }
        return img;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 111565079)
    public synchronized void resetImg() {
        img = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1713721960)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getResultDao() : null;
    }

    public String getGanhuo_id() {
        return this.ganhuo_id;
    }

    public void setGanhuo_id(String ganhuo_id) {
        this.ganhuo_id = ganhuo_id;
    }

    public Result() {
    }


    @Generated(hash = 954042456)
    public Result(String id, String ganhuo_id, String createdAt, String desc, String publishedAt,
            String source, String type, String url, boolean used, String who) {
        this.id = id;
        this.ganhuo_id = ganhuo_id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1557637596)
    private transient ResultDao myDao;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.ganhuo_id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
        dest.writeStringList(this.images);
        dest.writeList(this.img);
    }

    protected Result(Parcel in) {
        this.id = in.readString();
        this.ganhuo_id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
        this.images = in.createStringArrayList();
        this.img = new ArrayList<Image>();
        in.readList(this.img, Image.class.getClassLoader());
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
