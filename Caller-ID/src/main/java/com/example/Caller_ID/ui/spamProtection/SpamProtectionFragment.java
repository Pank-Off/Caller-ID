package com.example.Caller_ID.ui.spamProtection;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Caller_ID.App;
import com.example.Caller_ID.DatabaseHelper;
import com.example.Caller_ID.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.Caller_ID.ui.callLog.Details.EXTRA;

public class SpamProtectionFragment extends Fragment {

    private SpamProtectionViewModel spamProtectionViewModel;
    private DividerItemDecoration mDividerItemDecoration;
    private Context context;
    private SpamAdapter adapter;
    private FloatingActionButton floatingButton;

    private RecyclerView spamList;
    private HashMap<String, String> spamerMap;
    private DatabaseHelper mDatabaseHelper = App.getInstance().getDataBase();
    private Handler handler = new Handler();
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

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
        spamerMap = new HashMap<>();
        setOnFloatingBtnClick();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(requireActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    adapter.getFilter().filter(query);
                    Log.i("ItemCount()", adapter.getItemCount() + "");
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextCahnge", newText);
                    adapter.getFilter().filter(newText);
                    Log.i("ItemCount()", adapter.getItemCount() + "");
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void showSpam() {
        Log.d("ShowSpam", "spam");
        new Thread(() -> {
            spamerMap = mDatabaseHelper.getDataFromDB();
            handler.post(() -> {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                spamList.setLayoutManager(linearLayoutManager);
                mDividerItemDecoration = new DividerItemDecoration(spamList.getContext(),
                        DividerItemDecoration.VERTICAL);
                spamList.addItemDecoration(mDividerItemDecoration);
                ArrayList<String> numbers = new ArrayList<>();
                for (Map.Entry<String, String> entry : spamerMap.entrySet()) {
                    numbers.add(entry.getKey());
                }
                adapter = new SpamAdapter(spamerMap, positions -> {
                    // получаем выбранный пункт
                    String selectedSpamer = numbers.get(positions);
                    Intent intent = new Intent(getActivity(), AddNumberActivity.class);
                    intent.putExtra(EXTRA, selectedSpamer);
                    startActivity(intent);
                });
                spamList.setAdapter(adapter);
            });
        }).start();
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

    @Override
    public void onResume() {
        super.onResume();
        showSpam();
    }


}
