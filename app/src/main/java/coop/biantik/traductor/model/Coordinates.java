package coop.biantik.traductor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

@Data
public class Coordinates implements Serializable, Parcelable {

    private static final long serialVersionUID = -5091722806178583696L;

    private double x;
    private double y;

    public Coordinates(double y, double x) {
        super();
        this.x = x;
        this.y = y;
    }


    protected Coordinates(Parcel in) {
        x = in.readDouble();
        y = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(x);
        dest.writeDouble(y);
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

}