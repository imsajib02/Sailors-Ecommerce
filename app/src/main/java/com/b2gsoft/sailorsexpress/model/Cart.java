package com.b2gsoft.sailorsexpress.model;

public class Cart {

    private int position;
    private Product product;
    private boolean checked;

    public Cart(int position, Product product, boolean checked) {
        this.position = position;
        this.product = product;
        this.checked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
