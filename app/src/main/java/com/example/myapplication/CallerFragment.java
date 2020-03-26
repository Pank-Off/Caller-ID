package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CallerFragment extends Fragment {

    List<PhoneBook> contacts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_caller,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        ListView listView = view.findViewById(R.id.contacts_list);
        PhoneAdapter adapter = new PhoneAdapter(getContext(), R.layout.item_third_activity, contacts);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {

            // получаем выбранный пункт
            PhoneBook selectedContact = (PhoneBook) parent.getItemAtPosition(position);
            Toast.makeText(getContext(), "Был выбран пункт " + selectedContact.getName(),
                    Toast.LENGTH_SHORT).show();

            String toCall = "tel:" + selectedContact.getNumber();

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(toCall));
            startActivity(intent);
        };
        listView.setOnItemClickListener(itemListener);


    }

    private void initData() {
        contacts.add(new PhoneBook(R.drawable.phone, "Мама", "+79263391837"));
        contacts.add(new PhoneBook(R.drawable.phone, "Папа", "+79325125265"));
        contacts.add(new PhoneBook(R.drawable.phone, "Дедушка", "+79123259865"));
        contacts.add(new PhoneBook(R.drawable.phone, "Работа", "+79125362362"));
        contacts.add(new PhoneBook(R.drawable.phone, "Миша", "+79326463344"));
    }
}
