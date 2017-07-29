package com.example.juhi.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by juhi on 7/28/17.
 */


//Task 3 ->  Subclass SQLiteOpenHelper COMPLETED
public class DBHelper extends SQLiteOpenHelper{
    //creation of static final variable named DB_VER for declaring database version
    private static final int DB_VER = 1;
    //creation of static final variable named DB_NAME for assigning database name
    private static final String DB_NAME = "news.db";

    //logging
    private static final String TAG = "dbhelper";

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        //create SQL_CREATE_NEWSITEM_TABLE building a contract with NewsItem
        final String SQL_CREATE_NEWSITEM_TABLE = "CREATE TABLE " + Contract.NewsItem.TABLE_NAME + " (" +
                Contract.NewsItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.NewsItem.COLUMN_SOURCE + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_DESC + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_URL + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                Contract.NewsItem.COLUMN_PUBLISHED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        Log.d(TAG, "Create SQL: " + SQL_CREATE_NEWSITEM_TABLE);
        db.execSQL(SQL_CREATE_NEWSITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){

        //this is to drop table if DB_VER changes
        db.execSQL("DROP TABLE IF EXISTS "+Contract.NewsItem.TABLE_NAME);
        onCreate(db);
    }
}
