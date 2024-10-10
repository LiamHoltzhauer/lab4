package com.example.lab4;

public class TodoItem {
    public String text;
    public boolean isUrgent;

    public TodoItem(String text, boolean isUrgent) {
        this.text = text;
        this.isUrgent = isUrgent;
    }

    public String getText() {
        return text;
    }

    public boolean isUrgent() {
        return isUrgent;
    }
}