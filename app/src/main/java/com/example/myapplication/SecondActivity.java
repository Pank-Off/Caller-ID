package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private List<String> contacts = new ArrayList<>(Arrays.asList("Mom","Dad","Son","Daughter", "Uncle","Aunt"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView text = findViewById(R.id.text2);
        int i = getIntent().getIntExtra(MainActivity.EXTRA_INT, -1);
        text.setText(String.valueOf(i));

        Context context = getApplicationContext();
        Button back = findViewById(R.id.btnBack);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(v -> {
            Toast toast = Toast.makeText(context, "Button1 clicked!", Toast.LENGTH_LONG);
            toast.show();
        });

        Button start = findViewById(R.id.start);
        start.setOnClickListener(v->{
            Context c = getApplicationContext();
            Intent intent = new Intent(c,ThirdActivity.class);
            startActivity(intent);
        });
    }
}

