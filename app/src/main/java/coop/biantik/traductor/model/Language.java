package coop.biantik.traductor.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Language implements Parcelable {


	private String name;

	private String code;


    public Language(){

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
    }

    protected Language(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
    }

    public static final Creator<Language> CREATOR = new Creator<Language>() {
        public Language createFromParcel(Parcel source) {
            return new Language(source);
        }

        public Language[] newArray(int size) {
            return new Language[size];
        }
    };
}
