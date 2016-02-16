package com.example.android.equipmentbookingtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.SoftReference;

/**
 * Created by Stella on 2015-11-02.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";
    public static final String COL_5 = "STATUS";

    /*constructor: create database*/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD INTEGER,STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, password);
        contentValues.put(COL_5, "False");
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }


    public  void setUserLoggedIn(String email, String loggedIn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, loggedIn);
        db.update(TABLE_NAME, contentValues, "EMAIL =?", new String[]{email});
    }
    public boolean checkLoggedIn(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select STATUS from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String s ;
        if(cursor.moveToFirst()){
            do{
                s =  cursor.getString(0);
                if(s.equals("True")){
                    return true;
                }
            }while(cursor.moveToNext());
        }
        return false;
    }

    public String searchPassword(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select EMAIL, PASSWORD from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String e, p;
        p = "not found";
        if(cursor.moveToFirst()){
            do{
                e = cursor.getString(0);
                if(e.equals(email)){
                    p = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return p;
    }

    public String getUserEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select STATUS, EMAIL from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String s, e;
        e = "not found";
        if(cursor.moveToFirst()){
            do{
                s =  cursor.getString(0);
                if(s.equals("True")){
                    e = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return e;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select EMAIL from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id,String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, password);
        db.update(TABLE_NAME, contentValues, "EMAIL = ?",new String[]{email} );
        return true;
    }

    public Integer deleteData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"EMAIL = ?",new String[]{email});
    }
}
