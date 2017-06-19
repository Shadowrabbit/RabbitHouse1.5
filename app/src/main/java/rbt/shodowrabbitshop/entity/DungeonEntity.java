package rbt.shodowrabbitshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/19.
 */
public class DungeonEntity implements Parcelable {
    private String id;//唯一识别
    private String name;//买家id
    private String account;//账号
    private String dungeonType;//战役类型
    private String difficulty;//难度
    private String targetTime;//到期时间
    private String server;//服务器
    private String tag;//备注

    public DungeonEntity(String id, String account, String dungeonType, String difficulty, String targetTime, String name, String server, String tag) {
        this.id = id;
        this.account = account;
        this.dungeonType = dungeonType;
        this.difficulty = difficulty;
        this.targetTime = targetTime;
        this.name = name;
        this.server = server;
        this.tag = tag;
    }

    public DungeonEntity() {
    }

    protected DungeonEntity(Parcel in) {
        id = in.readString();
        account = in.readString();
        dungeonType = in.readString();
        difficulty = in.readString();
        targetTime = in.readString();
        name = in.readString();
        server = in.readString();
        tag = in.readString();
    }

    public static final Creator<DungeonEntity> CREATOR = new Creator<DungeonEntity>() {
        @Override
        public DungeonEntity createFromParcel(Parcel in) {
            return new DungeonEntity(in);
        }

        @Override
        public DungeonEntity[] newArray(int size) {
            return new DungeonEntity[size];
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

    public String getDungeonType() {
        return dungeonType;
    }

    public void setDungeonType(String dungeonType) {
        this.dungeonType = dungeonType;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(account);
        dest.writeString(dungeonType);
        dest.writeString(difficulty);
        dest.writeString(targetTime);
        dest.writeString(name);
        dest.writeString(server);
        dest.writeString(tag);
    }

    @Override
    public String toString() {
        return account+","+dungeonType+","+difficulty;
    }
}
