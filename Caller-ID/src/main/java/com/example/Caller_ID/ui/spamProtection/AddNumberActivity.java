package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.Caller_ID.App;
import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class AddNumberActivity extends AppCompatActivity {

    MenuItem itemAdd;
    MenuItem itemEdit;
    DatabaseHelper mDatabaseHelper = App.getInstance().getDataBase();
    AddSpamerFragment addSpamerFragment;
    EditSpamerFragment editSpamerFragment;
    Context context;
    private Pattern correctNumber = Pattern.compile("^[0-9]{1,10}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);
        context = getApplicationContext();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getIntent().getExtras().getString(EXTRA).equals("")) {
            addSpamerFragment = new AddSpamerFragment();
            fragmentTransaction.replace(R.id.fragment_container, addSpamerFragment);
        } else {
            editSpamerFragment = new EditSpamerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA, getIntent().getExtras().getString(EXTRA));
            editSpamerFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, editSpamerFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_spam_menu, menu);
        itemAdd = menu.getItem(1);
        itemEdit = menu.getItem(0);
        if (getIntent().getExtras().getString(EXTRA).equals("")) {
            itemEdit.setVisible(false);
        } else {
            itemAdd.setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.action_add == item.getItemId()) {
            if (!(Objects.requireNonNull(addSpamerFragment.numberOfPhone.getText())).toString().equals("")) {
                String number = addSpamerFragment.numberOfPhone.getText().toString();
                if (checkValid()) {
                    if (!mDatabaseHelper.addRecord(number, true)) {
                        Toast.makeText(context, "Sorry, duplicate", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Add", Toast.LENGTH_LONG).show();
                    }
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

        if (R.id.action_edit == item.getItemId()) {
            itemAdd.setVisible(true);
            itemEdit.setVisible(false);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            addSpamerFragment = new AddSpamerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA, Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA));
            addSpamerFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, addSpamerFragment);
            fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    Boolean checkValid() {
        String numberOfPhoneValue = Objects.requireNonNull(addSpamerFragment.numberOfPhone.getText()).toString();
        if (correctNumber.matcher(numberOfPhoneValue).matches()) {
            hideError(addSpamerFragment.numberOfPhone);
            return true;
        } else {
            showError(addSpamerFragment.numberOfPhone);
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
