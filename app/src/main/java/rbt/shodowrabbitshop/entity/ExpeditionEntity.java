package rbt.shodowrabbitshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/19.
 */
public class ExpeditionEntity implements Parcelable{
    private String id;//唯一识别
    private String account;//账号
    private String targetTime;//到期时间
    private String name;//买家id
    private String tag;//备注
    private String server;//服务器

    public ExpeditionEntity() {
    }

    public ExpeditionEntity(String id, String account, String targetTime, String name, String tag, String server) {
        this.id = id;
        this.account = account;
        this.targetTime = targetTime;
        this.name = name;
        this.tag = tag;
        this.server = server;
    }

    protected ExpeditionEntity(Parcel in) {
        id = in.readString();
        account = in.readString();
        targetTime = in.readString();
        name = in.readString();
        tag = in.readString();
        server = in.readString();
    }

    public static final Creator<ExpeditionEntity> CREATOR = new Creator<ExpeditionEntity>() {
        @Override
        public ExpeditionEntity createFromParcel(Parcel in) {
            return new ExpeditionEntity(in);
        }

        @Override
        public ExpeditionEntity[] newArray(int size) {
            return new ExpeditionEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(account);
        dest.writeString(targetTime);
        dest.writeString(name);
        dest.writeString(tag);
        dest.writeString(server);
    }
}
