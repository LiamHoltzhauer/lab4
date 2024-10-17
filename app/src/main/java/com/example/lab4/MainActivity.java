package com.example.lab4;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<TodoItem> todoList;
    public TodoAdapter adapter;
    public TodoDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TodoDatabaseHelper(this);
        todoList = new ArrayList<>();
        adapter = new TodoAdapter(this, todoList);

        ListView todoListView = findViewById(R.id.todoListView);
        todoListView.setAdapter(adapter);

        EditText todoEditText = findViewById(R.id.todoEditText);
        Switch urgentSwitch = findViewById(R.id.urgentSwitch);
        Button addButton = findViewById(R.id.addButton);

        // Load saved todos from the database
        loadTodosFromDatabase();

        // Add button click event
        addButton.setOnClickListener(v -> {
            String todoText = todoEditText.getText().toString().trim();
            boolean isUrgent = urgentSwitch.isChecked();

            if (!todoText.isEmpty()) {
                dbHelper.addTodo(todoText, isUrgent); // Add to database
                loadTodosFromDatabase();  // Refresh the list
                todoEditText.setText("");  // Clear the input field
            }
        });

        // Set up the long click listener for deleting items
        todoListView.setOnItemLongClickListener((parent, view, position, id) -> {
            TodoItem todoItem = todoList.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Do you want to delete this?")
                    .setMessage("The selected row is: " + position)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteTodo(todoItem.getId()); // Remove from database
                        loadTodosFromDatabase();  // Refresh the list
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void loadTodosFromDatabase() {
        todoList.clear();
        Cursor cursor = dbHelper.getAllTodos();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_ID));
            String todoText = cursor.getString(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_TODO));
            boolean isUrgent = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_URGENCY)) == 1;
            todoList.add(new TodoItem(id, todoText, isUrgent));
        }
        adapter.notifyDataSetChanged();
    }
}