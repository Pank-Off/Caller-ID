package com.example.Caller_ID.ui.spamProtection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Caller_ID.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class AddSpamerFragment extends Fragment {

    TextInputEditText numberOfPhone;
    private TextInputEditText comment;
    String number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setTextOnEditText();
    }

    private void initViews(View view) {
        numberOfPhone = view.findViewById(R.id.numberOfPhone);
        comment = view.findViewById(R.id.comment);
    }

    String getComment() {
        if (comment != null) {
            return Objects.requireNonNull(comment.getText()).toString();
        } else {
            return "";
        }
    }

    private void setTextOnEditText() {
        try {
            number = requireArguments().getString(EXTRA);
            numberOfPhone.setText(number);
        } catch (IllegalStateException e) {
            numberOfPhone.setText("");
        }
    }
}
