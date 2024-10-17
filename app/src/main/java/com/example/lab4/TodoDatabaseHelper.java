package com.example.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    // Database and table details
    private static final String DATABASE_NAME = "TodoDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Todos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TODO = "todo";
    public static final String COLUMN_URGENCY = "urgency";

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO + " TEXT, " +
                COLUMN_URGENCY + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a new todo item
    public void addTodo(String todo, boolean isUrgent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO, todo);
        values.put(COLUMN_URGENCY, isUrgent ? 1 : 0);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Delete a todo item by ID
    public void deleteTodo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Get all todo items from the database
    public Cursor getAllTodos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}