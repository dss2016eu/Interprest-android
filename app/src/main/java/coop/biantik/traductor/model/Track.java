package coop.biantik.traductor.model;

import android.os.Parcel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Sergio on 7/7/15.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Track extends Base {

    private String code;
    private String name;
    private String url;
    private String stream;

    public Track(){
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.stream);
    }

    protected Track(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.url = in.readString();
        this.stream = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
