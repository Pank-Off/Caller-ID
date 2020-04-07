package com.example.Caller_ID.ui.callLog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CallLogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CallLogViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CallLog fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}