package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class AddNumberActivity extends AppCompatActivity {

    TextInputEditText numberField;
    DatabaseHelper mDatabaseHelper;
    Context context;
    private Pattern correctNumber = Pattern.compile("^[0-9]{1,10}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_and_newspamer);

        numberField = findViewById(R.id.numberOfPhone);
        String numberString = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA);
        numberField.setText(numberString);

        context = getApplicationContext();
        mDatabaseHelper = new DatabaseHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_spam_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.action_add == item.getItemId()) {
            if (!Objects.requireNonNull(numberField.getText()).toString().equals("")) {
                String number = numberField.getText().toString();
                if (checkValid()) {
                    mDatabaseHelper.addRecord(number, true);
                    finish();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Failed to save an entry").
                        setMessage("Please input number of phone").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    Boolean checkValid() {
        String numberOfPhoneValue = Objects.requireNonNull(numberField.getText()).toString();
        if (correctNumber.matcher(numberOfPhoneValue).matches()) {
            hideError(numberField);
            return true;
        } else {
            showError(numberField);
            return false;
        }
    }

    // Показать ошибку
    private void showError(TextView view) {
        view.setError("Не более 10 цифр!");
    }

    // спрятать ошибку
    private void hideError(TextView view) {
        view.setError(null);
    }
}
