package com.example.Caller_ID.ui.callLog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import me.everything.providers.android.calllog.Call;
import me.everything.providers.android.calllog.CallsProvider;

public class CallLogFragment extends Fragment {

    private CallLogViewModel callLogViewModel;
    private RecyclerView contactsList;
    private DividerItemDecoration mDividerItemDecoration;
    private TextView oops;
    private ImageView sad_emotion;
    private Button allowBtn;
    private Context context;
    private List<PhoneBook> contacts;
    private DatabaseHelper mDatabaseHelper = App.getInstance().getDataBase();
    final private Handler handler = new Handler();
    final static String EXTRA_NUMBER = "EXTRA_NUMBER";
    final static String EXTRA_NAME = "EXTRA_NAME";
    final static String EXTRA_ICON = "EXTRA_ICON";

    // Request code for READ_CALL_LOG. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CALL_LOG = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_caller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callLogViewModel = new ViewModelProvider(requireActivity()).get(CallLogViewModel.class);
        callLogViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        context = getContext();
        initViews(view);
        allowBtn.setOnClickListener(v -> {
            Intent intent = callLogViewModel.setOnClickAllowBtnListener(context);
            startActivity(intent);
        });

        // Read and show the contacts
        showContacts();
    }

    private void initViews(View view) {
        contactsList = view.findViewById(R.id.contacts_list);
        oops = view.findViewById(R.id.notAllowPermission);
        sad_emotion = view.findViewById(R.id.sad_emotion);
        allowBtn = view.findViewById(R.id.allowBtn);
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_REQUEST_READ_CALL_LOG);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.

            new Thread(() -> {
                contacts = getContactNames();
                handler.post(() -> {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    contactsList.setLayoutManager(linearLayoutManager);
                    mDividerItemDecoration = new DividerItemDecoration(contactsList.getContext(),
                            DividerItemDecoration.VERTICAL);
                    contactsList.addItemDecoration(mDividerItemDecoration);

                    PhoneAdapter adapter = new PhoneAdapter(contacts, positions -> {
                        // получаем выбранный пункт
                        PhoneBook selectedContact = contacts.get(positions);
                        Toast.makeText(getContext(), "Был выбран пункт " + selectedContact.getName(),
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), Details.class);
                        intent.putExtra(EXTRA_NAME, selectedContact.getName());
                        intent.putExtra(EXTRA_NUMBER, selectedContact.getNumber());
                        intent.putExtra(EXTRA_ICON, selectedContact.getIcon());
                        startActivity(intent);
                    });
                    contactsList.setAdapter(adapter);
                });
            }).start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CALL_LOG) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                allowBtn.setVisibility(View.INVISIBLE);
                sad_emotion.setVisibility(View.INVISIBLE);
                oops.setVisibility(View.INVISIBLE);
                showContacts();
            } else {
                oops.setText("Oops...You did not allow CallerID to access your phone call logs(((\nPlease do it!");
                sad_emotion.setImageResource(R.drawable.sad_emotion);
                allowBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List<PhoneBook> getContactNames() {
        List<String> contacts = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Call.CallType> types = new ArrayList<>();
        List<PhoneBook> phoneBooks = new ArrayList<>();
        CallsProvider callsProvider = new CallsProvider(context);
        List<Call> number = callsProvider.getCalls().getList();
        int number_size = 25;
        for (int i = 0; i < number_size; i++) {
            contacts.add(number.get(i).number);
            names.add(number.get(i).name);
            types.add(number.get(i).type);
            phoneBooks.add(new PhoneBook((determineType(types.get(i), contacts.get(i))), names.get(i) == null ? "Unknown Number" : names.get(i), contacts.get(i)));
        }
        return phoneBooks;
    }

    private int determineType(Call.CallType type, String number) {
        String isSpam = mDatabaseHelper.getSingleUserInfo(number);
        if (isSpam.equals("Is spam")) {
            return R.drawable.bancircle;
        }
        if (type == Call.CallType.INCOMING) {
            return R.drawable.incomming;
        } else if (type == Call.CallType.OUTGOING) {
            return R.drawable.outgoing;
        }
        return R.drawable.missing;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)) {
            showContacts();
        }
    }
}
