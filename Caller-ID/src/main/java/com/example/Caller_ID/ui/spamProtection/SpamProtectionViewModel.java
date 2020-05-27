package com.example.Caller_ID.ui.spamProtection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpamProtectionViewModel extends ViewModel {

    private MutableLiveData<Boolean> mBool;

    public SpamProtectionViewModel() {
        mBool = new MutableLiveData<>();
    }

    LiveData<Boolean> getValid() {
        return mBool;
    }
}
