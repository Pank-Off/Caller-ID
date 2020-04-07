package com.example.Caller_ID.ui.callLog;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Caller_ID.R;

import java.util.ArrayList;
import java.util.List;

import me.everything.providers.android.calllog.Call;
import me.everything.providers.android.calllog.CallsProvider;

public class CallLogFragment extends Fragment {

    //private CallLogViewModel callLogViewModel;
    private RecyclerView contactsList;
    private Context context;
    // Request code for READ_CALL_LOG. It can be any number > 0.

    private static final int PERMISSIONS_REQUEST_READ_CALL_LOG = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        callLogViewModel =
//                ViewModelProviders.of(this).get(CallLogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_caller, container, false);
        /*final TextView textView = root.findViewById(R.id.text_notifications);
        callLogViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        context = getContext();
        contactsList = view.findViewById(R.id.contacts_list);
        // Read and show the contacts
        showContacts();
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_REQUEST_READ_CALL_LOG);
            //requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_REQUEST_READ_CALL_LOG);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<PhoneBook> contacts = getContactNames();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            contactsList.setLayoutManager(linearLayoutManager);

            PhoneAdapter adapter = new PhoneAdapter(contacts, positions -> {
                // получаем выбранный пункт
                PhoneBook selectedContact = contacts.get(positions);
                Toast.makeText(getContext(), "Был выбран пункт " + selectedContact.getName(),
                        Toast.LENGTH_SHORT).show();

                String toCall = "tel:" + selectedContact.getNumber();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(toCall));
                startActivity(intent);
            });

            contactsList.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CALL_LOG) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(context, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
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
        List<PhoneBook> phoneBooks = new ArrayList<>();
        CallsProvider callsProvider = new CallsProvider(context);
        List<Call> number = callsProvider.getCalls().getList();
        for (int i = 0; i < number.size(); i++) {
            contacts.add(number.get(i).number);
            names.add(number.get(i).name);
            phoneBooks.add(new PhoneBook(names.get(i) == null ? R.drawable.bancircle : R.drawable.phone, names.get(i) == null ? "Unknown Number" : names.get(i), contacts.get(i)));
        }
        return phoneBooks;
    }


}
