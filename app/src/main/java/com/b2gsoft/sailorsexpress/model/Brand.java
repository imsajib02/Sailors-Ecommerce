package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Brand implements Comparable<Brand>, Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    @Nullable
    private String name;

    @SerializedName("image")
    @Nullable
    private String image;

    @SerializedName("image_large")
    @Nullable
    private String imageLarge;

    @SerializedName("description")
    @Nullable
    private String description;

    @SerializedName("active")
    @Nullable
    private boolean active;

    @SerializedName("slug")
    @Nullable
    private String slug;

    public Brand(String id, @Nullable String name, @Nullable String image, boolean active, @Nullable String slug, @Nullable String imageLarge, @Nullable String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageLarge = imageLarge;
        this.description = description;
        this.active = active;
        this.slug = slug;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    @Nullable
    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(@Nullable String imageLarge) {
        this.imageLarge = imageLarge;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public boolean isActive() {
        return active;
    }

    public void setActive(@Nullable boolean active) {
        this.active = active;
    }

    @Nullable
    public String getSlug() {
        return slug;
    }

    public void setSlug(@Nullable String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Brand brand) {

        return name.toLowerCase().compareTo(brand.name.toLowerCase());
    }
}
