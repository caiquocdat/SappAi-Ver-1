package com.sapai_4.ai.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sapai_4.ai.model.RecentModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBRecentManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Recent3";
    private static final int VERSION = 1;

    public DBRecentManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLQuery = "CREATE TABLE recent (ID INTEGER primary key AUTOINCREMENT,TIME INTEGER,CONTENT VARCHAR(255))";
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public ArrayList<RecentModel> getAllFavourites() {
        ArrayList<RecentModel> recentModel = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from recent", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            long myDateSeconds = cursor.getLong(1);
            Date myDate = new Date(myDateSeconds * 1000);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dateString = format.format(myDate);
            String content = cursor.getString(2);
            recentModel.add(new RecentModel(id, myDate,content));
            cursor.moveToNext();
        }
        cursor.close();
        return recentModel;
    }
    public void insertRecent(RecentModel recentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TIME", recentModel.getDueDateAsInteger());
        values.put("CONTENT", recentModel.getContent());
        db.insert("recent", null, values);
        db.close();
    }
}
