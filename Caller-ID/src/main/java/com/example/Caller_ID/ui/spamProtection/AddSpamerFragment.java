package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.Caller_ID.App;
import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class AddSpamerFragment extends Fragment {

    private MenuItem itemAdd;
    private MenuItem itemEdit;
    private MenuItem itemSave;
    private DatabaseHelper mDatabaseHelper = App.getInstance().getDataBase();
    private TextInputEditText numberOfPhone;
    private TextInputEditText comment;
    private PhoneNumberUtil util = null;
    private String correctPhone = null;
    private final Handler handler = new Handler();
    private String oldNumber;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setTextOnEditText();
        setHasOptionsMenu(true);
        context = getContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_spam_menu, menu);
        itemSave = menu.getItem(2);
        itemAdd = menu.getItem(1);
        itemEdit = menu.getItem(0);
        if (Objects.equals(Objects.requireNonNull(requireActivity().getIntent().getExtras()).getString(EXTRA), "")) {
            itemSave.setVisible(false);
            itemEdit.setVisible(false);
            itemAdd.setVisible(true);
        } else {
            itemAdd.setVisible(false);
            itemEdit.setVisible(false);
            itemSave.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.action_add == item.getItemId()) {
            if (!(Objects.requireNonNull(numberOfPhone.getText())).toString().equals("")) {
                String number = numberOfPhone.getText().toString();
                if (checkValidNumber(number)) {
                    new Thread(() -> {
                        boolean successAdd = mDatabaseHelper.addRecord(correctPhone, true, getComment());
                        handler.post(() -> {
                            if (!successAdd) {
                                Toast.makeText(context, "Sorry, duplicate", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Add", Toast.LENGTH_LONG).show();
                            }
                            requireActivity().finish();
                        });
                    }).start();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

        if (R.id.action_save == item.getItemId()) {
            if (!(Objects.requireNonNull(numberOfPhone.getText())).toString().equals("")) {
                assert getArguments() != null;
                String oldNumber = getArguments().getString(EXTRA);
                String newNumber = numberOfPhone.getText().toString();
                if (checkValidNumber(newNumber)) {
                    String newComment = getComment();
                    new Thread(() -> {
                        mDatabaseHelper.replaceRecord(oldNumber, correctPhone, newComment);
                        handler.post(() -> {
                            Toast.makeText(context, "Replace", Toast.LENGTH_LONG).show();
                            requireActivity().finish();
                        });
                    }).start();

                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        return true;
    }

    private boolean checkValidNumber(String number) {
        if (util == null) {
            util = PhoneNumberUtil.createInstance(context);
        }
        try {
            final Phonenumber.PhoneNumber phoneNumber = util.parse(number, "RU");
            correctPhone = util.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            if (util.isPossibleNumber(phoneNumber)) {
                hideError(numberOfPhone);
                return true;
            }
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        showError(numberOfPhone);
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

    private void initViews(View view) {
        numberOfPhone = view.findViewById(R.id.numberOfPhone);
        comment = view.findViewById(R.id.comment);
    }

    private String getComment() {
        if (comment != null) {
            return Objects.requireNonNull(comment.getText()).toString();
        } else {
            return "";
        }
    }

    private void setTextOnEditText() {
        try {
            oldNumber = requireArguments().getString(EXTRA);
            numberOfPhone.setText(oldNumber);
        } catch (IllegalStateException e) {
            numberOfPhone.setText("");
        }
    }
}
