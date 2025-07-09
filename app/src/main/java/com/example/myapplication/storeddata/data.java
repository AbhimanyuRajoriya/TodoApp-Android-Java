package com.example.myapplication.storeddata;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.todoapp;

import java.util.ArrayList;
import java.util.List;

public class data extends SQLiteOpenHelper {

    private static final String dbsName = "tododbs";
    private static final String tablename = "todotable";
    private static final String col1 = "id";
    private static final String col2 = "task";
    private static final String col3 = "status";
    private static final int id= 1;

    public data(Context context) {
        super(context, dbsName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ tablename+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, status INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tablename);
        onCreate(db);
    }

    public void taskinsert(todoapp mod){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col2, mod.getTask());
        cv.put(col3,0);

        db.insert(tablename, null, cv);
    }

    public void taskupdate(int id, String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col2, task);

        db.update(tablename, cv, "ID=?", new String[]{String.valueOf(id)});
    }

    public void satutsupdate(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col3,status);

        db.update(tablename, cv, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deletetask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablename, "ID=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<todoapp> gettask(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        List<todoapp> modl = new ArrayList<>();

        db.beginTransaction();
        try{
            c = db.query(tablename,null, null, null, null, null, null);
            if (c!=null){
                if(c.moveToFirst()){
                    do{
                        todoapp tda = new todoapp();
                        tda.setId(c.getInt(c.getColumnIndex(col1)));
                        tda.setTask(c.getString(c.getColumnIndex(col2)));
                        tda.setStatus(c.getInt(c.getColumnIndex(col3)));
                        modl.add(tda);
                    }while (c.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            if (c != null) c.close();
        }
        return modl;
    }
}