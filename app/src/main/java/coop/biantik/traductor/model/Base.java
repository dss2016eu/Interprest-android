package coop.biantik.traductor.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class Base implements Serializable, Parcelable {

    @SerializedName("_links")
    @Expose
    private Links_ Links;


    public String getId(){
        return getIdHref().substring(getIdHref().lastIndexOf('/') + 1);
    }

    public String getIdHref(){
        return getLinks().getSelf().getHref();
    }



}
