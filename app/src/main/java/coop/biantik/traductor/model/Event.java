package coop.biantik.traductor.model;

import android.os.Parcel;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Sergio on 7/7/15.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Event extends Base {

    private String name;
    private String description;

    private List<Track> languages;

    public Event(){
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeTypedList(languages);
    }

    protected Event(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.languages = in.createTypedArrayList(Track.CREATOR);
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
