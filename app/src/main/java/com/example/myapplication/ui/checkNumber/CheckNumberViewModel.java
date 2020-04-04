package com.example.myapplication.ui.checkNumber;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckNumberViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CheckNumberViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}