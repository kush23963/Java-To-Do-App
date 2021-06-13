package com.codewithkush.todoapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Tester";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "Task";
    private static final String DB_COLULMN = "TaskName";

    public Dbhelper(Context context){
        super(context,DB_NAME,null,DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL)",DB_TABLE,DB_COLULMN);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = String.format("DELETE TABLE IF ANY %s ",DB_TABLE);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
    public void insertNewTask(String task){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLULMN,task);
        sqLiteDatabase.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }
    public void deleteTask(String Task){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE,DB_COLULMN + "= ?",new String[]{Task});
        sqLiteDatabase.close();
    }
    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DB_TABLE,new String[]{DB_COLULMN},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLULMN);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        sqLiteDatabase.close();
        return taskList;
    }
}

