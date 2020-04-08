package com.example.Caller_ID.ui.spamProtection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpamProtectionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpamProtectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is SpamProtection fragment");
    }

    LiveData<String> getText() {
        return mText;
    }
}