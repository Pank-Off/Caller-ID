package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.Caller_ID.R;

import java.util.Objects;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class AddNumberActivity extends AppCompatActivity {


    AddSpamerFragment addSpamerFragment;
    EditSpamerFragment editSpamerFragment;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);
        context = getApplicationContext();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (Objects.equals(Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA), "")) {
            addSpamerFragment = new AddSpamerFragment();
            fragmentTransaction.replace(R.id.fragment_container, addSpamerFragment);
        } else {
            editSpamerFragment = new EditSpamerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA, getIntent().getExtras().getString(EXTRA));
            editSpamerFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, editSpamerFragment);
        }
        fragmentTransaction.commit();
    }
}
