package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("_id")
    private String id;

    @SerializedName("image")
    @Nullable
    private String image;

    @SerializedName("redirectUrl")
    @Nullable
    private String redirectUrl;

    public Slider(String id, @Nullable String image, @Nullable String redirectUrl) {
        this.id = id;
        this.image = image;
        this.redirectUrl = redirectUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    @Nullable
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(@Nullable String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
