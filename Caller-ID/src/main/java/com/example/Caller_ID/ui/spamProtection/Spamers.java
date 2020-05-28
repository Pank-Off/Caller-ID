package com.example.Caller_ID.ui.spamProtection;

public class Spamers {
    private String comment;
    private String number;

    Spamers(String comment, String number) {
        this.comment = comment;
        this.number = number;
    }

    String getComment() {
        return comment;
    }

    String getNumber() {
        return number;
    }

    public void setName(String comment) {
        this.comment = comment;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
