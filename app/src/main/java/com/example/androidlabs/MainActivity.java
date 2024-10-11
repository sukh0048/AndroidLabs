package com.example.androidlabs; // Ensure this matches your package name

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure your layout is named activity_main.xml

        // Find views by their IDs
        ListView listView = findViewById(R.id.listView);
        EditText editText = findViewById(R.id.editText);
        Switch switchUrgent = findViewById(R.id.switchUrgent);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        // Initialize the adapter
        adapter = new ToDoAdapter(this, todoList);
        listView.setAdapter(adapter); // Set the adapter to the ListView

        // Add button click event to add new todo items
        buttonAdd.setOnClickListener(v -> {
            String taskText = editText.getText().toString();
            boolean isUrgent = switchUrgent.isChecked();

            if (!taskText.isEmpty()) {
                // Add a new item to the list
                todoList.add(new ToDoItem(taskText, isUrgent));
                adapter.notifyDataSetChanged(); // Refresh the ListView
                editText.setText(""); // Clear the EditText
                switchUrgent.setChecked(false); // Uncheck the switch
            }
        });

        // Long click listener for deleting items from the list
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Do you want to delete this?")
                    .setMessage("The selected row is: " + position)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Remove the item from the list and refresh
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }
}
