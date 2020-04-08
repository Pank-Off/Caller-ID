package com.example.Caller_ID.ui.spamProtection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Caller_ID.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class AddNumberActivity extends AppCompatActivity {

    TextInputEditText numberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_and_newspamer);
        numberField = findViewById(R.id.numberOfPhone);
        String numberString = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA);
        numberField.setText(numberString);
    }
}
