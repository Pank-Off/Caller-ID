package com.example.myapplication;

import android.widget.ImageView;
import android.widget.TextView;

class PhoneBook {
    private int icon;
    private String name;
    private String number;

    PhoneBook(int icon, String name, String number)
    {
        this.icon = icon;
        this.name = name;
        this.number = number;
    }

    String getName() {
        return name;
    }

    int getIcon() {
        return icon;
    }

    String getNumber() {
        return number;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
