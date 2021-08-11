package com.b2gsoft.sailorsexpress.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.contract.Connectivity;
import com.b2gsoft.sailorsexpress.contract.OTP;
import com.b2gsoft.sailorsexpress.presenter.OtpPresenter;
import com.b2gsoft.sailorsexpress.utils.MySnack;

import static com.b2gsoft.sailorsexpress.utils.Constants.PhoneNumber;
import static com.b2gsoft.sailorsexpress.utils.Constants.VerificationID;

public class RegistrationActivityOne extends AppCompatActivity implements OTP, Connectivity {

    private EditText phone;
    private TextView btnSendOTP;
    private RelativeLayout rootView;

    private OtpPresenter otpPresenter;
    private OTP otp;
    private Connectivity connectivity;

    private Context context;

    private String strPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_one);

        context = this;
        otp = this;
        connectivity = this;
        otpPresenter = new OtpPresenter(this, otp, connectivity);

        rootView = (RelativeLayout) findViewById(R.id.root_view);
        phone = (EditText) findViewById(R.id.et_phone);
        btnSendOTP = (TextView) findViewById(R.id.txt_send_otp);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();
            }
        });
    }


    private void validate() {

        if(TextUtils.isEmpty(phone.getText().toString())) {

            Toast.makeText(this, getResources().getString(R.string.enter_phone), Toast.LENGTH_SHORT).show();
        }
        else if(phone.getText().toString().length() != 11) {

            Toast.makeText(this, getResources().getString(R.string.invalid_phone), Toast.LENGTH_SHORT).show();
        }
        else {

            strPhone = phone.getText().toString();
            showDialog();
        }
    }


    @Override
    public void onSent(String verificationId) {

        Intent intent = new Intent(RegistrationActivityOne.this, OtpVerificationActivity.class);
        intent.putExtra(VerificationID, verificationId);
        intent.putExtra(PhoneNumber, strPhone);
        startActivity(intent);
    }


    @Override
    public void onVerified() {

        Intent intent = new Intent(RegistrationActivityOne.this, RegistrationActivityTwo.class);
        intent.putExtra(PhoneNumber, strPhone);
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

        MySnack.showGeneralSnackBar(rootView, getResources().getString(R.string.timed_out), 1500);
    }


    private void showDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        MaterialButton btnClose = dialog.findViewById(R.id.btn_close);
        MaterialButton btnSubmit = dialog.findViewById(R.id.btn_submit);

        TextView txtTitle = dialog.findViewById(R.id.txt_title);
        TextView txtDesc = dialog.findViewById(R.id.txt_desc);

        txtTitle.setText(getString(R.string.confirmation));
        txtDesc.setText(strPhone + " " + getString(R.string.is_this_your_phone));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                otpPresenter.sendOTP(strPhone);
            }
        });
    }
}
