package com.b2gsoft.sailorsexpress.contract;

public interface Connectivity {

    public void notConnected();
    public void noActiveConnection();
    public void onConnectionTimeOut();
}
