package com.example.Caller_ID.ui.checkNumber;

import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Caller_ID.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.regex.Pattern;

public class CheckNumberViewModel extends ViewModel {

    DatabaseHelper mDatabaseHelper;
    private MutableLiveData<Boolean> mBool;
    private Pattern correctNumber = Pattern.compile("^[0-9]{1,10}$");

    public CheckNumberViewModel() {
        mBool = new MutableLiveData<>();
    }

    LiveData<Boolean> getValid() {
        return mBool;
    }


    void checkValid(TextInputEditText numberOfPhoneTV) {
        String numberOfPhoneValue = Objects.requireNonNull(numberOfPhoneTV.getText()).toString();
        /*if (correctNumber.matcher(numberOfPhoneValue).matches()) {
            hideError(numberOfPhoneTV);
            mBool.postValue(true);
        } else {
            showError(numberOfPhoneTV);
            mBool.postValue(false);
        }*/
        //String isSpam = mDatabaseHelper.getSingleUserInfo(numberOfPhoneValue);
        //
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