package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Verify extends AppCompatActivity {

    private Button callerBtn;
    private Button verifyBtn;
    private Button spamBtn;
    private Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        initViews();
       // setOnCallerBtnClickListener();
//        setOnVerifyBtnClickListener();
//        setOnSpamBtnClickListener();
//        setOnSettingsBtnClickListener();

    }
        private void initViews () {
            callerBtn = findViewById(R.id.callerBtn);
            verifyBtn = findViewById(R.id.verifyBtn);
            spamBtn = findViewById(R.id.spamBtn);
            settingsBtn = findViewById(R.id.settingsBtn);
        }
        private void setOnSettingsBtnClickListener () {
        }

        private void setOnSpamBtnClickListener () {
        }

        private void setOnCallerBtnClickListener () {
            callerBtn.setOnClickListener(v ->
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
    }



