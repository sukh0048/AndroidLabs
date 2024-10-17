package com.example.androidlabs;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ToDoItem> todoList = new ArrayList<>();
    private ToDoAdapter adapter;
    private ToDoDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database helper
        dbHelper = new ToDoDatabaseHelper(this);

        // Find views by their IDs
        ListView listView = findViewById(R.id.listView);
        EditText editText = findViewById(R.id.editText);
        Switch switchUrgent = findViewById(R.id.switchUrgent);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        // Initialize the adapter and ListView
        adapter = new ToDoAdapter(this, todoList);
        listView.setAdapter(adapter);

        // Load tasks from the database
        loadTasksFromDatabase();

        // Add button click event to add new todo items
        buttonAdd.setOnClickListener(v -> {
            String taskText = editText.getText().toString();
            boolean isUrgent = switchUrgent.isChecked();

            if (!taskText.isEmpty()) {
                // Add a new item to the list and database
                ToDoItem newItem = new ToDoItem(taskText, isUrgent);
                todoList.add(newItem);
                dbHelper.insertToDoItem(taskText, isUrgent); // Insert into DB
                adapter.notifyDataSetChanged();

                // Clear the EditText and Switch
                editText.setText("");
                switchUrgent.setChecked(false);
            }
        });

        // Long click listener for deleting items from the list and database
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete task")
                    .setMessage("Do you want to delete this task?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ToDoItem item = todoList.get(position);
                        dbHelper.deleteToDoItem(item.getId()); // Delete from DB
                        todoList.remove(position); // Remove from list
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    // Load tasks from the database
    private void loadTasksFromDatabase() {
        Cursor cursor = dbHelper.getAllToDoItems();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(ToDoDatabaseHelper.COLUMN_ID));
                String task = cursor.getString(cursor.getColumnIndex(ToDoDatabaseHelper.COLUMN_TASK));
                boolean urgent = cursor.getInt(cursor.getColumnIndex(ToDoDatabaseHelper.COLUMN_URGENT)) == 1;
                todoList.add(new ToDoItem(id, task, urgent));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
