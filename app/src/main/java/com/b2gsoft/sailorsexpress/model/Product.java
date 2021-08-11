package com.b2gsoft.sailorsexpress.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Product implements Comparable<Product>, Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    @Nullable
    private String name;

    @SerializedName("thumbnail")
    @Nullable
    private String thumbnail;

    @SerializedName("slug")
    @Nullable
    private String slug;

    @SerializedName("sku")
    @Nullable
    private String sku;

    @SerializedName("price")
    @Nullable
    private JsonElement price;

    @SerializedName("vat")
    @Nullable
    private JsonElement vat;

    @SerializedName("freeDelivery")
    @Nullable
    private boolean freeDelivery;

    @SerializedName("status")
    @Nullable
    private boolean status;

    @SerializedName("forOnline")
    @Nullable
    private boolean forOnline;

    @SerializedName("featured")
    @Nullable
    private boolean featured;

    @SerializedName("newArrival")
    @Nullable
    private boolean newArrival;

    @SerializedName("active")
    @Nullable
    private boolean active;

    @SerializedName("topSelling")
    @Nullable
    private boolean topSelling;

    @SerializedName("description")
    @Nullable
    private String description;

    @SerializedName("vendorId")
    @Nullable
    private String vendorId;

    @SerializedName("vendorName")
    @Nullable
    private String vendorName;

    @SerializedName("barcode")
    @Nullable
    private String barcode;

    @SerializedName("sizes")
    @Nullable
    private List<Size> sizes;

    @SerializedName("expireDate")
    @Nullable
    private String expireDate;

    @SerializedName("currentStock")
    @Nullable
    private int currentStock;

    @SerializedName("images")
    @Nullable
    private List<String> images;

    @SerializedName("discount")
    @Nullable
    private Discount discount;

    @SerializedName("category")
    @Nullable
    private Category category;

    @SerializedName("subcategory")
    @Nullable
    private SubCategory subCategory;

    @SerializedName("brand")
    @Nullable
    private Brand brand;

    @SerializedName("quantity")
    @Nullable
    private int quantity;

    @SerializedName("size")
    @Nullable
    private Size size;

    @SerializedName("currentPrice")
    @Nullable
    private double currentPrice;

    public Product(String id, @Nullable String name, @Nullable String thumbnail, @Nullable String slug, @Nullable String sku, @Nullable JsonElement price,
                   @Nullable JsonElement vat, boolean freeDelivery, boolean status, boolean forOnline, boolean featured, boolean newArrival, boolean active,
                   boolean topSelling, @Nullable String description, @Nullable String vendorId, @Nullable String vendorName, @Nullable String barcode,
                   @Nullable List<Size> sizes, @Nullable String expireDate, int currentStock, @Nullable List<String> images, @Nullable Discount discount,
                   @Nullable Category category, @Nullable SubCategory subCategory, @Nullable Brand brand, @Nullable int quantity, @Nullable Size size,
                   @Nullable double currentPrice) {

        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.slug = slug;
        this.sku = sku;
        this.price = price;
        this.vat = vat;
        this.freeDelivery = freeDelivery;
        this.status = status;
        this.forOnline = forOnline;
        this.featured = featured;
        this.newArrival = newArrival;
        this.active = active;
        this.topSelling = topSelling;
        this.description = description;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.barcode = barcode;
        this.sizes = sizes;
        this.expireDate = expireDate;
        this.currentStock = currentStock;
        this.images = images;
        this.discount = discount;
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.quantity = quantity;
        this.size = size;
        this.currentPrice = currentPrice;
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
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@Nullable String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Nullable
    public String getSlug() {
        return slug;
    }

    public void setSlug(@Nullable String slug) {
        this.slug = slug;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public void setSku(@Nullable String sku) {
        this.sku = sku;
    }

    @Nullable
    public JsonElement getPrice() {
        return price;
    }

    public void setPrice(@Nullable JsonElement price) {
        this.price = price;
    }

    @Nullable
    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(@Nullable int currentStock) {
        this.currentStock = currentStock;
    }

    @Nullable
    public List<String> getImages() {
        return images;
    }

    public void setImages(@Nullable List<String> images) {
        this.images = images;
    }

    @Nullable
    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(@Nullable Discount discount) {
        this.discount = discount;
    }

    @Nullable
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(@Nullable Brand brand) {
        this.brand = brand;
    }

    @Nullable
    public JsonElement getVat() {
        return vat;
    }

    public void setVat(@Nullable JsonElement vat) {
        this.vat = vat;
    }

    @Nullable
    public boolean isFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(@Nullable boolean freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    @Nullable
    public boolean isStatus() {
        return status;
    }

    public void setStatus(@Nullable boolean status) {
        this.status = status;
    }

    @Nullable
    public boolean isForOnline() {
        return forOnline;
    }

    public void setForOnline(@Nullable boolean forOnline) {
        this.forOnline = forOnline;
    }

    @Nullable
    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(@Nullable boolean featured) {
        this.featured = featured;
    }

    @Nullable
    public boolean isNewArrival() {
        return newArrival;
    }

    public void setNewArrival(@Nullable boolean newArrival) {
        this.newArrival = newArrival;
    }

    @Nullable
    public boolean isActive() {
        return active;
    }

    public void setActive(@Nullable boolean active) {
        this.active = active;
    }

    @Nullable
    public boolean isTopSelling() {
        return topSelling;
    }

    public void setTopSelling(@Nullable boolean topSelling) {
        this.topSelling = topSelling;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(@Nullable String vendorId) {
        this.vendorId = vendorId;
    }

    @Nullable
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(@Nullable String vendorName) {
        this.vendorName = vendorName;
    }

    @Nullable
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(@Nullable String barcode) {
        this.barcode = barcode;
    }

    @Nullable
    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(@Nullable List<Size> sizes) {
        this.sizes = sizes;
    }

    @Nullable
    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(@Nullable String expireDate) {
        this.expireDate = expireDate;
    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    public void setCategory(@Nullable Category category) {
        this.category = category;
    }

    @Nullable
    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(@Nullable SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    @Nullable
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Nullable int quantity) {
        this.quantity = quantity;
    }

    @Nullable
    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(@Nullable double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Nullable
    public Size getSize() {
        return size;
    }

    public void setSize(@Nullable Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Product product) {

        return Double.valueOf(currentPrice).compareTo(product.currentPrice);
    }
}
