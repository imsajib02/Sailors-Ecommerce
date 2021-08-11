package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class Discount {

    @SerializedName("amount")
    @Nullable
    private JsonElement amount;

    @SerializedName("type")
    @Nullable
    private int type;

    public Discount(@Nullable JsonElement amount, int type) {
        this.amount = amount;
        this.type = type;
    }

    @Nullable
    public JsonElement getAmount() {
        return amount;
    }

    public void setAmount(@Nullable JsonElement amount) {
        this.amount = amount;
    }

    @Nullable
    public int getType() {
        return type;
    }

    public void setType(@Nullable int type) {
        this.type = type;
    }
}
