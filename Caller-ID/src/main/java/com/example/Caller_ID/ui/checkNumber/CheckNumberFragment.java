package com.example.Caller_ID.ui.checkNumber;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.Caller_ID.App;
import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.FireBaseWorker;
import com.example.Caller_ID.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class CheckNumberFragment extends Fragment {

    private DatabaseHelper mDatabaseHelper = App.getInstance().getDataBase();
    private CheckNumberViewModel checkNumberViewModel;
    private MaterialButton addBtn;
    private TextInputEditText numberOfPhoneEditText;
    private TextView isSpamTextfield;
    private Context context;
    private PhoneNumberUtil util;
    private String correctPhone = null;
    private final Handler handler = new Handler();
    private MaterialButton updateDB;
    private FireBaseWorker fireBaseWorker;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        context = getContext();
        checkNumberViewModel = new ViewModelProvider(requireActivity()).get(CheckNumberViewModel.class);
        checkNumberViewModel.getValid().observe(getViewLifecycleOwner(), bool -> {
            String toast;
            if (bool) {
                toast = "Valid input";
            } else {
                toast = "Invalid input";
            }
            Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
        });

        addBtn.setText(R.string.title_check_number);
        setOnAddBtnClickListener();

        setOnUpdateDBClickListener();
    }

    private void setOnUpdateDBClickListener() {
        updateDB.setOnClickListener(v -> {
            fireBaseWorker = new FireBaseWorker(context);
            new Thread(() -> {
                handler.post(() -> Toast.makeText(context, "DataBase are updating...", Toast.LENGTH_LONG).show());
                boolean areUpdate = fireBaseWorker.download();
                handler.post(() -> {
                    Log.e("areUpdate", areUpdate + "");
                    if (areUpdate) {
                        Toast.makeText(context, "DataBase is updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "DataBase is not updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();

        });
    }

    private void setOnAddBtnClickListener() {
        addBtn.setOnClickListener(v -> {
            String number = Objects.requireNonNull(numberOfPhoneEditText.getText()).toString();
            if (checkValidNumber(number)) {
                new Thread(() -> {
                    String isSpam = mDatabaseHelper.getSingleUserInfo(Objects.requireNonNull(correctPhone));
                    handler.post(() -> isSpamTextfield.setText(isSpam));
                }).start();
            }
        });
    }

    private void initViews(View view) {
        addBtn = view.findViewById(R.id.addBtn);
        numberOfPhoneEditText = view.findViewById(R.id.numberOfPhone);
        isSpamTextfield = view.findViewById(R.id.isSpamTextView);
        updateDB = view.findViewById(R.id.updateDB);
    }

    private boolean checkValidNumber(String number) {
        if (util == null) {
            util = PhoneNumberUtil.createInstance(context);
        }
        try {
            final Phonenumber.PhoneNumber phoneNumber = util.parse(number, "RU");
            correctPhone = util.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            if (util.isPossibleNumber(phoneNumber)) {
                hideError(numberOfPhoneEditText);
                Toast.makeText(context, correctPhone, Toast.LENGTH_LONG).show();
                return true;
            }
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        showError(numberOfPhoneEditText);
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