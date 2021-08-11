package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("details")
    @Nullable
    private String details;

    @SerializedName("district")
    @Nullable
    private String district;

    public Address(String details, String district) {
        this.details = details;
        this.district = district;
    }

    @Nullable
    public String getDetails() {
        return details;
    }

    public void setDetails(@Nullable String details) {
        this.details = details;
    }

    @Nullable
    public String getDistrict() {
        return district;
    }

    public void setDistrict(@Nullable String district) {
        this.district = district;
    }
}
