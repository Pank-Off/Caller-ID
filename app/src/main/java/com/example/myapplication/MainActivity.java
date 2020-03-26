package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    final static String EXTRA_INT = "Extra Int";

    private ImageView callerBtn;
    private ImageView verifyBtn;
    private ImageView spamBtn;
    private ImageView settingsBtn;

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
        callerBtn.setOnClickListener(v ->
        {
            CallerFragment callerFragment = new CallerFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, callerFragment);
            fragmentTransaction.commit();
        });

    }

    private void setOnVerifyBtnClickListener() {
        verifyBtn.setOnClickListener(v ->
        {
            VerifyFragment verifyFragment = new VerifyFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, verifyFragment);
            fragmentTransaction.commit();
        });
    }

    private void setOnSpamBtnClickListener() {
        spamBtn.setOnClickListener(v ->
        {
            SpamFragment spamFragment = new SpamFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, spamFragment);
            fragmentTransaction.commit();
        });
    }

    private void setOnSettingsBtnClickListener() {
        settingsBtn.setOnClickListener(v ->
        {
            SettingsFragment settingsFragment = new SettingsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, settingsFragment);
            fragmentTransaction.commit();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
