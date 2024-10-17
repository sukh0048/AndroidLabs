package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "todoDatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "todoItems";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_URGENT = "urgent";

    public ToDoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK + " TEXT, " +
                COLUMN_URGENT + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new ToDoItem
    public long insertToDoItem(String task, boolean isUrgent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task);
        values.put(COLUMN_URGENT, isUrgent ? 1 : 0); // 1 for true, 0 for false
        return db.insert(TABLE_NAME, null, values);
    }

    // Delete a ToDoItem by ID
    public void deleteToDoItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Get all ToDoItems
    public Cursor getAllToDoItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
