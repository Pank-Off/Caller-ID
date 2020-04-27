package com.example.Caller_ID.ui.spamProtection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.Caller_ID.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class SpamProtectionFragment extends Fragment {

    private SpamProtectionViewModel spamProtectionViewModel;
    private MaterialButton addBtn;
    private TextView textHome;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_spam_protection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        spamProtectionViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(SpamProtectionViewModel.class);
        spamProtectionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textHome.setText(s);
            }
        });

        setOnAddBtnClickListener();
    }

    private void initViews(View view) {
        addBtn = view.findViewById(R.id.addBtn);
        textHome = view.findViewById(R.id.text_home);
    }

    private void setOnAddBtnClickListener() {
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNumberActivity.class);
            intent.putExtra(EXTRA, "");
            startActivity(intent);
        });
    }
}
