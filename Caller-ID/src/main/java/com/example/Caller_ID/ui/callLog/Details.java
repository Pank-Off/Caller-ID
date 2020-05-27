package com.example.Caller_ID.ui.callLog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Caller_ID.App;
import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import static com.example.Caller_ID.ui.callLog.CallLogFragment.EXTRA_ICON;
import static com.example.Caller_ID.ui.callLog.CallLogFragment.EXTRA_NAME;
import static com.example.Caller_ID.ui.callLog.CallLogFragment.EXTRA_NUMBER;


public class Details extends AppCompatActivity {
    TextView nameView;
    TextView numberView;
    ImageView call;
    MaterialButton thisIsSpamBtn;
    Context context;
    DatabaseHelper mDatabaseHelper;
    public final static String EXTRA = "EXTRA";
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = getApplicationContext();
        mDatabaseHelper = App.getInstance().getDataBase();
        initViews();
        setText();
        setOnImageClickListener();
        setOnSpamBtnClickListener();
    }

    private void setOnSpamBtnClickListener() {
        if (Objects.requireNonNull(getIntent().getExtras()).getInt(EXTRA_ICON) == R.drawable.bancircle) {
            thisIsSpamBtn.setText(R.string.this_is_not_spam);
        } else {
            thisIsSpamBtn.setText(R.string.this_is_spam);
        }
        thisIsSpamBtn.setOnClickListener(v -> {
            if (thisIsSpamBtn.getText().equals(getResources().getString(R.string.this_is_spam))) {
                Toast.makeText(context, "Spamer is added", Toast.LENGTH_LONG).show();
                new Thread(() -> {
                    mDatabaseHelper.addRecord(numberView.getText().toString(), true, "From CallLog");
                    handler.post(() -> thisIsSpamBtn.setText(R.string.this_is_not_spam));
                }
                ).start();

            } else {
                Toast.makeText(context, "Spamer is deleted", Toast.LENGTH_LONG).show();
                new Thread(() -> {
                    mDatabaseHelper.removeRecord(numberView.getText().toString());
                    handler.post(() -> thisIsSpamBtn.setText(R.string.this_is_spam));
                }
                ).start();
            }
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
