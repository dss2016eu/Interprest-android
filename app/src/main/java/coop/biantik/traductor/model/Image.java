package coop.biantik.traductor.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class Image implements Parcelable {

	private String name;

	private String thumbnail;

	private String blurred;

	private double heightRatio;

    public Image(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.thumbnail);
        dest.writeString(this.blurred);
        dest.writeDouble(this.heightRatio);
    }

    protected Image(Parcel in) {
        this.name = in.readString();
        this.thumbnail = in.readString();
        this.blurred = in.readString();
        this.heightRatio = in.readDouble();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
