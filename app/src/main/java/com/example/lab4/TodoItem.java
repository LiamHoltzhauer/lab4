package com.example.lab4;

public class TodoItem {
    private int id; // Add ID for the database
    private String text;
    private boolean isUrgent;

    // Constructor without ID (for new items)
    public TodoItem(String text, boolean isUrgent) {
        this.text = text;
        this.isUrgent = isUrgent;
    }

    // Constructor with ID (for items retrieved from the database)
    public TodoItem(int id, String text, boolean isUrgent) {
        this.id = id;
        this.text = text;
        this.isUrgent = isUrgent;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isUrgent() {
        return isUrgent;
    }
}