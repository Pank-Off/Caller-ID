package com.example.myapplication.ui.callLog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CallLogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CallLogViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is spam fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}