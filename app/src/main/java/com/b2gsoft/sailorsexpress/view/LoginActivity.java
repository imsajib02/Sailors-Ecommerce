package com.b2gsoft.sailorsexpress.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;

public class LoginActivity extends AppCompatActivity {

    private EditText phone, password;
    private TextView forgotPass, login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText) findViewById(R.id.et_phone);
        password = (EditText) findViewById(R.id.et_password);

        forgotPass = (TextView) findViewById(R.id.forgot_pass);
        login = (TextView) findViewById(R.id.txt_login);
        register = (TextView) findViewById(R.id.txt_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegistrationActivityOne.class);
                startActivity(intent);
            }
        });
    }
}
