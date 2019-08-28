package com.example.mehndi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mehndiimg {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("thumb_image")
    @Expose
    private String thumbImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

}


