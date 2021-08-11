package com.b2gsoft.sailorsexpress.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.b2gsoft.sailorsexpress.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Launcher launcher = new Launcher();
        launcher.start();
    }

    private class Launcher extends Thread {

        public void run() {

            try{
                sleep(getResources().getInteger(R.integer.splashTime));
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
