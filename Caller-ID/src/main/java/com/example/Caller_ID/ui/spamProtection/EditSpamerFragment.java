package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.Caller_ID.App;
import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class EditSpamerFragment extends Fragment {

    private TextInputEditText numberOfPhone;
    private MaterialButton thisIsNotSpamBtn;
    private Context context;
    private DatabaseHelper mDatabaseHelper = App.getInstance().getDataBase();
    private Handler handler = new Handler();
    private MenuItem itemEdit;
    private MenuItem itemSave;
    private MenuItem itemAdd;
    private AddSpamerFragment addSpamerFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        initViews(view);
        setOnClickSpamBtnListener();
        setTextOnEditText();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_spam_menu, menu);
        itemSave = menu.getItem(2);
        itemAdd = menu.getItem(1);
        itemEdit = menu.getItem(0);
        itemSave.setVisible(false);
        itemAdd.setVisible(false);
        itemEdit.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        addSpamerFragment = new AddSpamerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA, Objects.requireNonNull(requireActivity().getIntent().getExtras()).getString(EXTRA));
        addSpamerFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, addSpamerFragment);
        fragmentTransaction.commit();
        return true;
    }

    private void setTextOnEditText() {
        String number = requireArguments().getString(EXTRA);
        numberOfPhone.setText(number);
    }

    private void setOnClickSpamBtnListener() {
        thisIsNotSpamBtn.setOnClickListener(v -> callAlertDialog());
    }

    private void callAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.
                setMessage("Do not consider this number spam?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(() -> {
                    boolean successDeleted = mDatabaseHelper.removeRecord(Objects.requireNonNull(numberOfPhone.getText()).toString());
                    handler.post(() -> {
                        if (successDeleted) {
                            Toast.makeText(context, "Spamer is deleted", Toast.LENGTH_LONG).show();
                            requireActivity().finish();
                        }
                    });
                }).start();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initViews(View view) {
        numberOfPhone = view.findViewById(R.id.numberOfPhone);
        thisIsNotSpamBtn = view.findViewById(R.id.thisIsNotSpamBtn);
    }
}
