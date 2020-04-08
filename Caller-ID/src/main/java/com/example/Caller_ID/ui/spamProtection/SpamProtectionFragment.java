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
import androidx.lifecycle.ViewModelProviders;

import com.example.Caller_ID.R;
import com.google.android.material.button.MaterialButton;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class SpamProtectionFragment extends Fragment {

    private SpamProtectionViewModel spamProtectionViewModel;
    private MaterialButton addBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        spamProtectionViewModel =
                ViewModelProviders.of(this).get(SpamProtectionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_spam_protection, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        spamProtectionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn = view.findViewById(R.id.addBtn);

        setOnAddBtnClickListener();
    }

    private void setOnAddBtnClickListener() {
        addBtn.setOnClickListener(v->{
                Intent intent = new Intent(getActivity(), NewSpamer.class);
                intent.putExtra(EXTRA,"");
                startActivity(intent);
            });
    }
}
