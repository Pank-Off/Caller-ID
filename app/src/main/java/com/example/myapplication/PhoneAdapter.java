package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PhoneAdapter extends ArrayAdapter<PhoneBook> {

    private LayoutInflater inflater;
    private int layout;
    private List<PhoneBook> contacts;

    public PhoneAdapter(Context context, int resource, List<PhoneBook> contacts) {
        super(context, resource, contacts);
        this.contacts = contacts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context); //Что это?
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView flagView = (ImageView) view.findViewById(R.id.phone);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView capitalView = (TextView) view.findViewById(R.id.number);

        PhoneBook book = contacts.get(position);

        flagView.setImageResource(book.getIcon());
        nameView.setText(book.getName());
        capitalView.setText(book.getNumber());
        return view;
    }
}