package com.example.Caller_ID.ui.callLog;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Caller_ID.R;
import com.example.Caller_ID.ui.spamProtection.AddNumberActivity;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import static com.example.Caller_ID.ui.callLog.CallLogFragment.EXTRA_NAME;
import static com.example.Caller_ID.ui.callLog.CallLogFragment.EXTRA_NUMBER;


public class Details extends AppCompatActivity {
    TextView nameView;
    TextView numberView;
    ImageView call;
    MaterialButton thisIsSpamBtn;
    public final static String EXTRA = "EXTRA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        setText();
        setOnImageClickListener();
        setOnSpamBtnClickListener();
    }

    private void setOnSpamBtnClickListener() {
        thisIsSpamBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddNumberActivity.class);
            intent.putExtra(EXTRA, numberView.getText());
            startActivity(intent);
        });
    }

    private void setOnImageClickListener() {
        call.setOnClickListener(v -> {
            String toCall = "tel:" + numberView.getText();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(toCall));
            startActivity(intent);
        });
    }

    private void setText() {
        String name = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA_NAME);
        String number = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA_NUMBER);
        nameView.setText(name);
        nameView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        numberView.setText(number);
    }

    private void initViews() {
        nameView = findViewById(R.id.name);
        numberView = findViewById(R.id.number);
        call = findViewById(R.id.call);
        thisIsSpamBtn = findViewById(R.id.thisIsSpamBtn);
    }
}
