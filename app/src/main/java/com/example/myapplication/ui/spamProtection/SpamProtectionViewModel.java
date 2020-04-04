package com.example.myapplication.ui.spamProtection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpamProtectionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpamProtectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}