package coop.biantik.traductor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

@Data
public class Location implements Serializable, Parcelable {

	private static final long serialVersionUID = -5091722806178583696L;

	private Coordinates coordinates;
	private String tag;

	public Location(double y, double x) {
		super();
		this.coordinates = new Coordinates(y, x);
	}


	public double getX() {
		return coordinates.getX();
	}

	public void setX(double x) {
		this.coordinates.setX(x);
	}

	public double getY() {
		return coordinates.getY();
	}

	public void setY(double y) {
		this.coordinates.setY(y);
	}


    protected Location(Parcel in) {
        coordinates = (Coordinates) in.readValue(Coordinates.class.getClassLoader());
        tag = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(coordinates);
        dest.writeString(tag);
    }

    @SuppressWarnings("unused")
    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}