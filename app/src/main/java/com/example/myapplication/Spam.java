package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Spam extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spam);

        Button addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, New_Spamer.class);
            startActivity(intent);
        });
    }
}
