package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class AddNumberActivity extends AppCompatActivity {

    MenuItem itemAdd;
    MenuItem itemEdit;
    MenuItem itemSave;
    DatabaseHelper mDatabaseHelper = App.getInstance().getDataBase();
    AddSpamerFragment addSpamerFragment;
    EditSpamerFragment editSpamerFragment;
    Context context;
    private PhoneNumberUtil util = null;
    String correctPhone = null;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);

        context = getApplicationContext();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA).equals("")) {
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
        itemSave = menu.getItem(2);
        itemAdd = menu.getItem(1);
        itemEdit = menu.getItem(0);
        if (Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA).equals("")) {
            itemSave.setVisible(false);
            itemEdit.setVisible(false);
        } else {
            itemAdd.setVisible(false);
            itemSave.setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.action_add == item.getItemId()) {
            if (!(Objects.requireNonNull(addSpamerFragment.numberOfPhone.getText())).toString().equals("")) {
                String number = addSpamerFragment.numberOfPhone.getText().toString();
                if (checkValidNumber(number)) {
                    new Thread(() -> {
                        boolean successAdd = mDatabaseHelper.addRecord(correctPhone, true, addSpamerFragment.getComment());
                        handler.post(() -> {
                            if (!successAdd) {
                                Toast.makeText(context, "Sorry, duplicate", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Add", Toast.LENGTH_LONG).show();
                            }
                            finish();
                        });

                    }).start();
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
            itemSave.setVisible(true);
            itemEdit.setVisible(false);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            addSpamerFragment = new AddSpamerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA, Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA));
            addSpamerFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, addSpamerFragment);
            fragmentTransaction.commit();
        }
        if (R.id.action_save == item.getItemId()) {
            if (!(Objects.requireNonNull(addSpamerFragment.numberOfPhone.getText())).toString().equals("")) {
                String number = addSpamerFragment.numberOfPhone.getText().toString();
                if (checkValidNumber(number)) {
                    String newComment = addSpamerFragment.getComment();
                    new Thread(() -> {
                        mDatabaseHelper.replaceRecord(addSpamerFragment.number, correctPhone, newComment);
                        handler.post(() -> {
                            Toast.makeText(context, "Replace", Toast.LENGTH_LONG).show();
                            finish();
                        });
                    }).start();

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

    boolean checkValidNumber(String number) {
        if (util == null) {
            util = PhoneNumberUtil.createInstance(context);
        }
        try {
            final Phonenumber.PhoneNumber phoneNumber = util.parse(number, "RU");
            correctPhone = util.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            if (util.isPossibleNumber(phoneNumber)) {
                hideError(addSpamerFragment.numberOfPhone);
                return true;
            }
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        showError(addSpamerFragment.numberOfPhone);
        return false;
    }

    // Показать ошибку
    private void showError(TextView view) {
        view.setError("Некорректный ввод");
    }

    // спрятать ошибку
    private void hideError(TextView view) {
        view.setError(null);
    }
}
