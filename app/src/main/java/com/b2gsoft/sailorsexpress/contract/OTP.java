package com.b2gsoft.sailorsexpress.contract;

public interface OTP {

    public void onSent(String verificationId);
    public void onVerified();
    public void onFailed(String message);
}
