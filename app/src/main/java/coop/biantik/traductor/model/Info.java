package coop.biantik.traductor.model;

import android.os.Parcel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Sergio on 7/7/15.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Info extends Base {

    private String api;

    public Info(){
    }

    public boolean isPrivate(){
        return api != null && api.equals("private");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.api);
    }

    protected Info(Parcel in) {
        this.api = in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        public Info createFromParcel(Parcel source) {
            return new Info(source);
        }

        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
}
