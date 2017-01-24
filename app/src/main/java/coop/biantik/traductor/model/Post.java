package coop.biantik.traductor.model;

import android.os.Parcel;

import java.util.Date;

import coop.biantik.traductor.network.enums.PostStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Sergio on 7/7/15.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Post extends Base {

    private String id;

    private String title;

    private String description;

    private String image;

    private PostStatusEnum status;

    private Date createdAt;

    private Date updatedAt;

    public Post(){
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
    }

    protected Post(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : PostStatusEnum.values()[tmpStatus];
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
