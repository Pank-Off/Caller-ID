package com.example.Caller_ID.ui.callLog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CallLogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CallLogViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CallLog fragment");
    }

    LiveData<String> getText() {
        return mText;
    }

     Intent setOnClickAllowBtnListener(Context context) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            return intent;
    }
}