package rbt.shodowrabbitshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class AccountEntity implements Parcelable {
    private String Id;//唯一识别
    private String num;//编号
    private List<String> ships;//船只
    private String price;//价格
    private String tag;//备注
    private String server;//服务器

    public AccountEntity() {
    }

    public AccountEntity(String num, List<String> ships, String price, String tag, String server) {
        this.num = num;
        this.ships = ships;
        this.price = price;
        this.tag = tag;
        this.server = server;
    }

    protected AccountEntity(Parcel in) {
        Id = in.readString();
        num = in.readString();
        ships = in.createStringArrayList();
        price = in.readString();
        tag = in.readString();
        server = in.readString();
    }

    public static final Creator<AccountEntity> CREATOR = new Creator<AccountEntity>() {
        @Override
        public AccountEntity createFromParcel(Parcel in) {
            return new AccountEntity(in);
        }

        @Override
        public AccountEntity[] newArray(int size) {
            return new AccountEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(num);
        dest.writeStringList(ships);
        dest.writeString(price);
        dest.writeString(tag);
        dest.writeString(server);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<String> getShips() {
        return ships;
    }

    public void setShips(List<String> ships) {
        this.ships = ships;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
    public String toString() {
        String accountData = this.getServer() + "," + this.getNum() + ",";
        String ship = "";
        for (int i = 0; i < this.ships.size(); i++) {
            ship = ship + ships.get(i) + "|";
        }
        accountData = accountData + ship + "," + this.getPrice();
        return accountData;
    }
}
