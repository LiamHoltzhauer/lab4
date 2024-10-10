package com.example.lab4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {

    private Context context;
    private List<TodoItem> todoList;

    public TodoAdapter(Context context, List<TodoItem> todoList) {
        this.context = context;
        this.todoList = todoList;
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.todo_item, parent, false);
        }

        // Get the TodoItem at this position
        TodoItem todoItem = (TodoItem) getItem(position);

        // Set the text of the todo
        TextView todoTextView = convertView.findViewById(R.id.todoTextView);
        todoTextView.setText(todoItem.getText());

        // If it's urgent, change the background and text color
        if (todoItem.isUrgent()) {
            convertView.setBackgroundColor(Color.RED);
            todoTextView.setTextColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            todoTextView.setTextColor(Color.BLACK);
        }

        return convertView;
    }
}