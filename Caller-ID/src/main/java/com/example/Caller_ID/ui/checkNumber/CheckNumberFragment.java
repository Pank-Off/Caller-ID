package com.example.Caller_ID.ui.checkNumber;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CheckNumberFragment extends Fragment {

    DatabaseHelper mDatabaseHelper;
    private CheckNumberViewModel checkNumberViewModel;
    private MaterialButton addBtn;
    private TextInputEditText numberOfPhoneEditText;
    private TextView isSpamTextfield;
    private Context context;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_check_and_newspamer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        checkNumberViewModel = new ViewModelProvider(requireActivity()).get(CheckNumberViewModel.class);
        checkNumberViewModel.getValid().observe(getViewLifecycleOwner(), bool -> {
            String toast;
            if (bool) {
                toast = "Valid input";
            } else {
                toast = "Invalid input";
            }
            Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
        });

        addBtn.setText(R.string.title_check_number);
        addBtn.setOnClickListener(v -> {
            context = getContext();
            mDatabaseHelper = new DatabaseHelper(context);
            String isSpam = mDatabaseHelper.getSingleUserInfo(Objects.requireNonNull(numberOfPhoneEditText.getText()).toString());
            isSpamTextfield.setText(isSpam);
        });
    }

    private void initViews(View view) {
        addBtn = view.findViewById(R.id.addBtn);
        numberOfPhoneEditText = view.findViewById(R.id.numberOfPhone);
        isSpamTextfield = view.findViewById(R.id.isSpamTextView);
    }
}