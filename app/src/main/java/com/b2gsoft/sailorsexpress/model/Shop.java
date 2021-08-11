package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Shop implements Comparable<Shop>, Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    @Nullable
    private String name;

    @SerializedName("image")
    @Nullable
    private String image;

    @SerializedName("vendorName")
    @Nullable
    private String vendorName;

    @SerializedName("thumbnail")
    @Nullable
    private String thumbnail;

    @SerializedName("address")
    @Nullable
    private Address address;

    public Shop(String id, @Nullable String name, @Nullable String image, @Nullable String vendorName, @Nullable String thumbnail, @Nullable Address address) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.vendorName = vendorName;
        this.thumbnail = thumbnail;
        this.address = address;
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
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(@Nullable String vendorName) {
        this.vendorName = vendorName;
    }

    @Nullable
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@Nullable String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Nullable
    public Address getAddress() {
        return address;
    }

    public void setAddress(@Nullable Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Shop shop) {

        return name.toLowerCase().compareTo(shop.name.toLowerCase());
    }
}
