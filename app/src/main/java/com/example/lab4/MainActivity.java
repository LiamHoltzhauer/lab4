package com.example.lab4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public List<TodoItem> todoList;
    public TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = new ArrayList<>();
        adapter = new TodoAdapter(this, todoList);

        ListView todoListView = findViewById(R.id.todoListView);
        todoListView.setAdapter(adapter);

        EditText todoEditText = findViewById(R.id.todoEditText);
        Switch urgentSwitch = findViewById(R.id.urgentSwitch);
        Button addButton = findViewById(R.id.addButton);

        // Add button click event
        addButton.setOnClickListener(v -> {
            String todoText = todoEditText.getText().toString().trim();
            boolean isUrgent = urgentSwitch.isChecked();

            if (!todoText.isEmpty()) {
                todoList.add(new TodoItem(todoText, isUrgent));
                adapter.notifyDataSetChanged();  // Refresh the list
                todoEditText.setText("");  // Clear the input field
            }
        });

        // Set up the long click listener for deleting items
        todoListView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Do you want to delete this?")
                    .setMessage("The selected row is: " + position)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        todoList.remove(position);  // Remove the item
                        adapter.notifyDataSetChanged();  // Refresh the list
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }
}