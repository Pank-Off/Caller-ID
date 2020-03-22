package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Caller extends AppCompatActivity {


    List<PhoneBook> contacts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller);

        initData();
        ListView listView = findViewById(R.id.contacts_list);
        PhoneAdapter adapter = new PhoneAdapter(this, R.layout.item_third_activity, contacts);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener itemListener = (parent, v, position, id) -> {

            // получаем выбранный пункт
            PhoneBook selectedContact = (PhoneBook) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), "Был выбран пункт " + selectedContact.getName(),
                    Toast.LENGTH_SHORT).show();

            String toCall = "tel:" + selectedContact.getNumber();
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toCall)));
        };
        listView.setOnItemClickListener(itemListener);
    }

//    public void call(View v) {
//        EditText number = findViewById(R.id.number);
//
//        String num = number.getText().toString();
//        try {
//            Integer.parseInt(num);
//        } catch (NumberFormatException e) {
//            TextView textView = findViewById(R.id.text);
//            textView.setText("Введите корректный номер");
//        }
//
//        String toCall = "tel:" + num;
//
//        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toCall)));
//    }

    private void initData() {
        contacts.add(new PhoneBook(R.drawable.phone, "Мама", "+79263391837"));
        contacts.add(new PhoneBook(R.drawable.phone, "Папа", "+79325125265"));
        contacts.add(new PhoneBook(R.drawable.phone, "Дедушка", "+79123259865"));
        contacts.add(new PhoneBook(R.drawable.phone, "Работа", "+79125362362"));
        contacts.add(new PhoneBook(R.drawable.phone, "Миша", "+79326463344"));
    }
}
