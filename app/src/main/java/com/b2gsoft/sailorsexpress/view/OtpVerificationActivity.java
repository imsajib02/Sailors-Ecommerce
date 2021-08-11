package com.b2gsoft.sailorsexpress.view;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.contract.Connectivity;
import com.b2gsoft.sailorsexpress.contract.CountDown;
import com.b2gsoft.sailorsexpress.contract.OTP;
import com.b2gsoft.sailorsexpress.presenter.OtpPresenter;
import com.b2gsoft.sailorsexpress.utils.MySnack;
import com.chaos.view.PinView;

import static com.b2gsoft.sailorsexpress.utils.Constants.PhoneNumber;
import static com.b2gsoft.sailorsexpress.utils.Constants.VerificationID;

public class OtpVerificationActivity extends AppCompatActivity implements OTP, Connectivity, CountDown {

    private TextView txtDes, txtVerify, txtResend, txtTime;
    private PinView pinView;
    private RelativeLayout rootView;

    private String phone, verificationId;

    private OtpPresenter otpPresenter;
    private OTP otp;
    private CountDown countDown;
    private Connectivity connectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        verificationId = getIntent().getStringExtra(VerificationID);
        phone = getIntent().getStringExtra(PhoneNumber);

        otp = this;
        connectivity = this;
        countDown = this;
        otpPresenter = new OtpPresenter(this, otp, countDown, connectivity);

        otpPresenter.startTimer();

        rootView = (RelativeLayout) findViewById(R.id.root_view);

        pinView = (PinView) findViewById(R.id.pin_view);

        txtDes = (TextView) findViewById(R.id.txt_desc);
        txtVerify = (TextView) findViewById(R.id.txt_verify);
        txtResend = (TextView) findViewById(R.id.txt_resend);
        txtTime = (TextView) findViewById(R.id.txt_time);

        txtDes.setText(getText(R.string.code_sent_to) + phone + ". " + getText(R.string.enter_the_code));
        txtResend.setText("");

        txtVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();
            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otpPresenter.resendOTP(phone);
            }
        });
    }


    private void validate() {

        if(TextUtils.isEmpty(pinView.getText().toString())) {

            Toast.makeText(this, getResources().getString(R.string.enter_code), Toast.LENGTH_SHORT).show();
        }
        else if(pinView.getText().toString().length() != 6) {

            Toast.makeText(this, getResources().getString(R.string.must_length_6), Toast.LENGTH_SHORT).show();
        }
        else {

            otpPresenter.verifyCode(verificationId, pinView.getText().toString());
        }
    }


    @Override
    public void onSent(String verificationId) {

        this.verificationId = verificationId;
        otpPresenter.startTimer();
    }


    @Override
    public void onVerified() {

        Intent intent = new Intent(OtpVerificationActivity.this, RegistrationActivityTwo.class);
        intent.putExtra(PhoneNumber, phone);
        startActivity(intent);
    }


    @Override
    public void onFailed(String message) {

        MySnack.showGeneralSnackBar(rootView, message, 1500);
    }


    @Override
    public void notConnected() {

        MySnack.showGeneralSnackBar(rootView, getResources().getString(R.string.not_connected), 1500);
    }


    @Override
    public void noActiveConnection() {

        MySnack.showGeneralSnackBar(rootView, getResources().getString(R.string.no_active_connection), 1500);
    }


    @Override
    public void onConnectionTimeOut() {

    }


    @Override
    public void showRemainingTime(int time) {

        txtResend.setText(getText(R.string.resend_code) + " " + getText(R.string.in));

        txtTime.setVisibility(View.VISIBLE);
        txtTime.setText(":" + String.valueOf(time) + " " + getText(R.string.sec));
    }


    @Override
    public void enableResend() {

        txtResend.setText(getText(R.string.resend_code));

        if(Build.VERSION.SDK_INT < 23) {

            txtResend.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        else {

            txtResend.setTextColor(ContextCompat.getColor(OtpVerificationActivity.this, R.color.colorPrimaryDark));
        }

        txtTime.setText("");
        txtTime.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {

        otpPresenter.stopCountDown();
        super.onDestroy();
    }
}
