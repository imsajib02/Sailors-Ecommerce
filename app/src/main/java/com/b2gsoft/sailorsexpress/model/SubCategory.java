package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubCategory implements Comparable<SubCategory>, Serializable {

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

    @SerializedName("slug")
    @Nullable
    private String slug;

    @SerializedName("active")
    @Nullable
    private boolean active;

    @SerializedName("category")
    @Nullable
    private Category category;

    public SubCategory(String id, @Nullable String name, @Nullable String image, @Nullable String imageLarge, @Nullable String slug, boolean active, @Nullable Category category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageLarge = imageLarge;
        this.slug = slug;
        this.active = active;
        this.category = category;
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
    public String getSlug() {
        return slug;
    }

    public void setSlug(@Nullable String slug) {
        this.slug = slug;
    }

    @Nullable
    public boolean isActive() {
        return active;
    }

    public void setActive(@Nullable boolean active) {
        this.active = active;
    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    public void setCategory(@Nullable Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull SubCategory subCategory) {

        return name.toLowerCase().compareTo(subCategory.name.toLowerCase());
    }
}