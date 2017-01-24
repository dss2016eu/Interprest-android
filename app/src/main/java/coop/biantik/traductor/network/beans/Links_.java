
package coop.biantik.traductor.network.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Links_ implements Serializable, Parcelable {

    @Expose
    private Self_ self;

    /**
     * 
     * @return
     *     The self
     */
    public Self_ getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self_ self) {
        this.self = self;
    }


    protected Links_(Parcel in) {
        self = (Self_) in.readValue(Self_.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(self);
    }

    @SuppressWarnings("unused")
    public static final Creator<Links_> CREATOR = new Creator<Links_>() {
        @Override
        public Links_ createFromParcel(Parcel in) {
            return new Links_(in);
        }

        @Override
        public Links_[] newArray(int size) {
            return new Links_[size];
        }
    };

}
