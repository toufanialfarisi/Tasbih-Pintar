package com.makeitation.tasbihpintar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "statistikdb.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "history";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "frekuensi";
    private static final String TABLE_NAME_2 = "date";
    private SQLiteDatabase db;
    public ArrayList<Integer> list;
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE history (id INTEGER PRIMARY KEY AUTOINCREMENT,frekuensi INTEGER)";
        db.execSQL(query);
        String query2 = "CREATE TABLE datetime (id INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT)";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  " + TABLE_NAME);
        db.execSQL("DROP TABLE datetime");
        onCreate(db);
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM history");
        Log.i("DELETE", "BERHASIL MENGHAPUS");
    }

    public void insertRecord(int frekuensi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, frekuensi);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insertDate(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, date);
        db.insert("datetime", null, values);
        db.close();
        Log.i("DATE RECORD", "BERHASIL MEMASUKAN DATE");
    }

    public void deleteRecord(){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Integer> getRecord() {
        list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int val = cursor.getInt(cursor.getColumnIndex(NAME_COL));
                list.add(val);
                cursor.moveToNext();
            }
        }
        Log.i("QUERY DB", String.valueOf(list));
        return list;
    }

    public ArrayList<Integer> getDateRecord() {
        list = new ArrayList<>();
        String query = "SELECT * FROM datetime";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int val = cursor.getInt(cursor.getColumnIndex(NAME_COL));
                list.add(val);
                cursor.moveToNext();
            }
        }
        Log.i("QUERY DB", String.valueOf(list));
        return list;
    }

//        cursor.moveToFirst();
//        while (cursor.isAfterLast() == false){
//            result = cursor.getInt(0);
//
//            cursor.moveToNext();
//        }
//        Log.i("KURSOR", String.valueOf(cursor.getCount()));
//        db.close();
//        Log.i("RESULT", String.valueOf(result));


//    public long getRecordDate(){
//
//        String query = "SELECT * FROM " + TABLE_NAME;
//        long  result = 0;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//        while (cursor.isAfterLast() == false){
//            result = cursor.getInt(1);
//            cursor.moveToNext();
//        }
//        db.close();
//        Log.i("RESULT", String.valueOf(result));
//        return result;
//    }

}
