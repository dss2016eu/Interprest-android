
package coop.biantik.traductor.network.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class Self_ implements Serializable, Parcelable {

    @Expose
    private String href;

    /**
     * @return The href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href The href
     */
    public void setHref(String href) {
        this.href = href;
    }

    protected Self_(Parcel in) {
        href = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
    }

    @SuppressWarnings("unused")
    public static final Creator<Self_> CREATOR = new Creator<Self_>() {
        @Override
        public Self_ createFromParcel(Parcel in) {
            return new Self_(in);
        }

        @Override
        public Self_[] newArray(int size) {
            return new Self_[size];
        }
    };

}
