package com.example.androidlabs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoAdapter extends BaseAdapter {
    private List<ToDoItem> todoList;
    private LayoutInflater inflater;

    public ToDoAdapter(Context context, List<ToDoItem> todoList) {
        this.todoList = todoList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.todo_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textViewItem);
        ToDoItem item = todoList.get(position);

        textView.setText(item.getText());

        if (item.isUrgent()) {
            convertView.setBackgroundColor(Color.RED);
            textView.setTextColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.BLACK);
        }

        return convertView;
    }
}

