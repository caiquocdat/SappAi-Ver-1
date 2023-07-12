package com.sapai_4.ai.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sapai_4.ai.model.MessageCopyModel;
import com.sapai_4.ai.model.RecentCopyModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBRecentCopyManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Recent8";
    private static final int VERSION = 1;

    public DBRecentCopyManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLQuery = "CREATE TABLE recent (ID INTEGER primary key AUTOINCREMENT,TIME INTEGER,LISTMESSAGE TEXT)";
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public ArrayList<RecentCopyModel> getAllFavourites() {
        ArrayList<RecentCopyModel> recentModel = new ArrayList<>();
        ArrayList<MessageCopyModel> messageModel=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from recent", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            long myDateSeconds = cursor.getLong(1);
            Date myDate = new Date(myDateSeconds * 1000);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String json=cursor.getString(2);
            Gson gson=new Gson();
            Type type=new TypeToken<ArrayList<MessageCopyModel>>(){}.getType();
            messageModel=gson.fromJson(json,type);
            recentModel.add(new RecentCopyModel(id, myDate,messageModel));
            cursor.moveToNext();
        }
        cursor.close();
        return recentModel;
    }
    public void insertRecent(RecentCopyModel recentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TIME", recentModel.getDueDateAsInteger());
        values.put("LISTMESSAGE", new Gson().toJson(recentModel.getListMessage()));
        db.insert("recent", null, values);
        db.close();
    }
    public void deleteFavouriteById(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM recent where ID=?", new Integer[]{id});
    }
    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM recent");
        db.close();
    }

}
