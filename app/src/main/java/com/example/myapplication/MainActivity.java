package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    final static String EXTRA_INT = "Extra Int";

    private Button callerBtn;
    private Button verifyBtn;
    private Button spamBtn;
    private Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setOnCallerBtnClickListener();
        setOnVerifyBtnClickListener();
        setOnSpamBtnClickListener();
        setOnSettingsBtnClickListener();

    }



    private void initViews() {
        callerBtn = findViewById(R.id.callerBtn);
        verifyBtn = findViewById(R.id.verifyBtn);
        spamBtn = findViewById(R.id.spamBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
    }





    private void setOnCallerBtnClickListener() {
        callerBtn.setOnClickListener(v->
        {
            Intent intent = new Intent(this, Caller.class);
            startActivity(intent);
        });

    }
    private void setOnVerifyBtnClickListener() {
        verifyBtn.setOnClickListener(v->
        {
            Intent intent = new Intent(this, Verify.class);
            startActivity(intent);
        });
    }

    private void setOnSpamBtnClickListener() {
        spamBtn.setOnClickListener(v->
        {
            Intent intent = new Intent(this, Spam.class);
            startActivity(intent);
        });
    }

    private void setOnSettingsBtnClickListener() {
        settingsBtn.setOnClickListener(v->
        {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
