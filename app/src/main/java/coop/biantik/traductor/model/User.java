package coop.biantik.traductor.model;

import android.os.Parcel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends Base {

    private static final long serialVersionUID = 3427492949750895641L;

    private String username;

    private String role;

    private boolean isAdmin;

    private boolean isTranslator;

    private String translationLanguage;

    private String ip;

    private int port;


    public User(){
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.role);
        dest.writeByte(isAdmin ? (byte) 1 : (byte) 0);
        dest.writeByte(isTranslator ? (byte) 1 : (byte) 0);
        dest.writeString(this.translationLanguage);
        dest.writeString(this.ip);
        dest.writeInt(this.port);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.role = in.readString();
        this.isAdmin = in.readByte() != 0;
        this.isTranslator = in.readByte() != 0;
        this.translationLanguage = in.readString();
        this.ip = in.readString();
        this.port = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}