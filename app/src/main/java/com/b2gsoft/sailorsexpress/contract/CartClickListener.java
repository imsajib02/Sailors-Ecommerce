package com.b2gsoft.sailorsexpress.contract;

public interface CartClickListener {

    public void onIncrease(int index);
    public void onDecrease(int index);
    public void onChecked(int index, boolean isChecked);
}
