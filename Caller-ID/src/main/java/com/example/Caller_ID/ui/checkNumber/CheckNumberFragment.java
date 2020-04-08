package com.example.Caller_ID.ui.checkNumber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.Caller_ID.R;
import com.google.android.material.button.MaterialButton;

public class CheckNumberFragment extends Fragment {

    private CheckNumberViewModel checkNumberViewModel;
    private MaterialButton addBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        checkNumberViewModel =
                ViewModelProviders.of(this).get(CheckNumberViewModel.class);
        View root = inflater.inflate(R.layout.fragment_check_and_newspamer, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        checkNumberViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn = view.findViewById(R.id.addBtn);
        addBtn.setText(R.string.title_check_number);
    }
}