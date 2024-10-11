package com.example.androidlabs; // Match your package

public class ToDoItem {
    private String text;
    private boolean isUrgent;

    public ToDoItem(String text, boolean isUrgent) {
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
