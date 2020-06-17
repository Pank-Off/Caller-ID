package com.example.Caller_ID.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.Caller_ID.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private Button shareBtn;
    private Switch darkMode;
    private TextView text;
    public static final String KEY = "isChecked";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        initViews(view);
        setOnShareBtnClickListener();
        setOnSwitchCheckedChangeListener();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
        darkMode.setChecked(sharedPreferences.getBoolean(KEY, false));
    }

    private void setOnSwitchCheckedChangeListener() {
        darkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                text.setText(R.string.dark_mode_is_enabled);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE).edit();
                editor.putBoolean(KEY, true);
                editor.apply();
            } else {
                text.setText(R.string.light_mode_is_enabled);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE).edit();
                editor.putBoolean(KEY, false);
                editor.apply();
            }
        });
    }

    private void setOnShareBtnClickListener() {
        shareBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Hi! I'm using Caller ID!";
            String shareSub = "Share sub";
            intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(intent, "Share using"));
        });
    }

    private void initViews(View view) {
        shareBtn = view.findViewById(R.id.shareButton);
        darkMode = view.findViewById(R.id.dark_mode);
        text = view.findViewById(R.id.settingsText);
    }
}
