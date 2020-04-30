package com.example.Caller_ID.ui.spamProtection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class SpamProtectionFragment extends Fragment {

    private SpamProtectionViewModel spamProtectionViewModel;
    private DividerItemDecoration mDividerItemDecoration;
    private Context context;

    private FloatingActionButton floatingButton;
    private RecyclerView spamList;
    private ArrayList<String> spamer;
    DatabaseHelper mDatabaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spam_protection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        spamProtectionViewModel = new ViewModelProvider(requireActivity()).get(SpamProtectionViewModel.class);
        spamProtectionViewModel.getValid().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {

            }
        });
        context = getContext();
        spamer = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(context);

        spamer = mDatabaseHelper.getDataFromDB();

        setOnFloatingBtnClick();
        showSpam();

    }

    private void showSpam() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        spamList.setLayoutManager(linearLayoutManager);
        mDividerItemDecoration = new DividerItemDecoration(spamList.getContext(),
                DividerItemDecoration.VERTICAL);
        spamList.addItemDecoration(mDividerItemDecoration);

        SpamAdapter adapter = new SpamAdapter(spamer, positions -> {
            // получаем выбранный пункт
            String selectedSpamer = spamer.get(positions);
            Toast.makeText(getContext(), "Был выбран пункт " + selectedSpamer,
                    Toast.LENGTH_SHORT).show();

        });
        spamList.setAdapter(adapter);
    }

    private void initViews(View view) {
        floatingButton = view.findViewById(R.id.floatingBtn);
        spamList = view.findViewById(R.id.spam_list);
    }

    private void setOnFloatingBtnClick() {
        floatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNumberActivity.class);
            intent.putExtra(EXTRA, "");
            startActivity(intent);
        });
    }
}
