package rbt.shodowrabbitshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/15.
 */
public class TaskEntity implements Parcelable {
    private String id;
    private String content;//内容
    private String updateTime;//修改时间
    private String title;//标题
    private String createTime;//创建时间

    public TaskEntity() {
    }

    public TaskEntity(String id, String content, String updateTime, String title,String createTime) {
        this.id = id;
        this.content = content;
        this.updateTime = updateTime;
        this.title = title;
        this.createTime=createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    protected TaskEntity(Parcel in) {
        id = in.readString();
        content = in.readString();
        updateTime = in.readString();
        title = in.readString();
        createTime=in.readString();
    }

    public static final Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel in) {
            return new TaskEntity(in);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(updateTime);
        dest.writeString(title);
        dest.writeString(createTime);
    }
}
