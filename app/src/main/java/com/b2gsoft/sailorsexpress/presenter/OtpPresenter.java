package com.b2gsoft.sailorsexpress.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.contract.Connectivity;
import com.b2gsoft.sailorsexpress.contract.CountDown;
import com.b2gsoft.sailorsexpress.contract.OTP;
import com.b2gsoft.sailorsexpress.utils.InternetCheck;
import com.b2gsoft.sailorsexpress.utils.NetworkUtils;
import com.b2gsoft.sailorsexpress.utils.ProgressDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OtpPresenter {

    private Context context;
    private OTP otp;
    private Connectivity connectivity;
    private CountDown countDown;

    private static PhoneAuthProvider.ForceResendingToken resendingToken;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private Timer timer;
    private int time = 60;

    public OtpPresenter(Context context, OTP otp, Connectivity connectivity) {
        this.context = context;
        this.otp = otp;
        this.connectivity = connectivity;
    }

    public OtpPresenter(Context context, OTP otp, CountDown countDown, Connectivity connectivity) {
        this.context = context;
        this.otp = otp;
        this.countDown = countDown;
        this.connectivity = connectivity;
    }


    public void sendOTP(final String phone) {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        ProgressDialog.show(context);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+88" + phone, 60,
                                TimeUnit.SECONDS,
                                TaskExecutors.MAIN_THREAD,
                                mCallbacks);
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void resendOTP(final String phone) {

        if(resendingToken != null && time == 0) {

            if(NetworkUtils.isConnected(context)) {

                new InternetCheck(new InternetCheck.Consumer() {
                    @Override
                    public void accept(Boolean active) {

                        if(active) {

                            ProgressDialog.show(context);

                            PhoneAuthProvider.getInstance().verifyPhoneNumber("+88" + phone, 60,
                                    TimeUnit.SECONDS,
                                    TaskExecutors.MAIN_THREAD,
                                    mCallbacks,
                                    resendingToken);
                        }
                        else {

                            connectivity.noActiveConnection();
                        }
                    }
                });
            }
            else {

                connectivity.notConnected();
            }
        }
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if(code != null) {

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Log.e("FirebaseException ", e.getMessage());

            ProgressDialog.dismiss();

            if(e instanceof FirebaseAuthInvalidCredentialsException) {

                otp.onFailed(context.getString(R.string.check_phone));
            }
            else if (e instanceof FirebaseTooManyRequestsException) {

                otp.onFailed(context.getString(R.string.limit_exceed));
            }
            else {

                otp.onFailed(context.getString(R.string.phone_verification_failed));
            }
        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            super.onCodeSent(verificationId, forceResendingToken);

            ProgressDialog.dismiss();
            resendingToken = forceResendingToken;
            otp.onSent(verificationId);
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String verificationId) {
            super.onCodeAutoRetrievalTimeOut(verificationId);
        }
    };


    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                ProgressDialog.dismiss();

                                if(task.isSuccessful()) {

                                    otp.onVerified();
                                }
                                else {

                                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                        otp.onFailed(context.getString(R.string.invalid_code));
                                    }
                                    else {

                                        otp.onFailed(context.getString(R.string.phone_verification_failed));
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {

                                ProgressDialog.dismiss();
                                otp.onFailed(context.getString(R.string.phone_verification_failed));

                                Log.e("PhoneVerification ", ""+e.getMessage());
                            }
                        });
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }


    public void startTimer() {

        timer = new Timer();
        time = 60;

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(time > 0) {

                            time = time - 1;
                            countDown.showRemainingTime(time);
                        }
                        else {

                            timer.cancel();
                            countDown.enableResend();
                        }
                    }
                });
            }
        },0, 1000);
    }


    public void stopCountDown() {

        try {

            if(timer != null) {

                timer.cancel();
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }


    public void verifyCode(final String verificationId, final String otp) {

        if(NetworkUtils.isConnected(context)) {

            new InternetCheck(new InternetCheck.Consumer() {
                @Override
                public void accept(Boolean active) {

                    if(active) {

                        ProgressDialog.show(context);

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                        signInWithPhoneAuthCredential(credential);
                    }
                    else {

                        connectivity.noActiveConnection();
                    }
                }
            });
        }
        else {

            connectivity.notConnected();
        }
    }
}
