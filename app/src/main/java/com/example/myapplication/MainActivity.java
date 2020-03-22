package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String EXTRA_INT = "Extra Int";

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text1);
        if (savedInstanceState != null)
            text.setText(savedInstanceState.getString(EXTRA_INT));
        else
            text.setText("1");
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(v -> {
            int currentNumber = getCurrentNumber(text);
            currentNumber++;
            text.setText(currentNumber + "");
        });

        Button next = findViewById(R.id.btnNext);
        Context c = getApplicationContext();
        next.setOnClickListener(v -> {
            int i = getCurrentNumber(text);
            Intent intent = new Intent(c, SecondActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra(EXTRA_INT, i);
            startActivity(intent);
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_INT, text.getText().toString());
        super.onSaveInstanceState(outState);
    }

    int getCurrentNumber(TextView t) {
        return Integer.parseInt((String) t.getText());
    }
}
