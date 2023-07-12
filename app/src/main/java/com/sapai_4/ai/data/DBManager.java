package com.sapai_4.ai.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sapai_4.ai.model.Favourites;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Favourite1";
    private static final int VERSION = 1;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLQuery = "CREATE TABLE favourites (ID INTEGER primary key,TITLE VARCHAR(255),DESCRIPTION VARCHAR(255),ICON BLOB)";
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<Favourites> getAllFavourites() {
        ArrayList<Favourites> favourites = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from favourites", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String descrip = cursor.getString(2);
            byte[] img = cursor.getBlob(3);
            favourites.add(new Favourites(id, title, descrip, img));
            cursor.moveToNext();
        }
        cursor.close();
        return favourites;
    }

    public void insertFavourites(Favourites favourites) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO favourites (ID,TITLE,DESCRIPTION,ICON) VALUES (?,?,?,?)",
                new String[]{favourites.getId() + "", favourites.getTitle() + "", favourites.getDescrip() + "", favourites.getImage() + ""});
        db.close();
    }

    public int getLastItemId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM favourites ORDER BY id DESC LIMIT 1", null);
        int lastId = -1;
        if (cursor.moveToFirst()) {
            lastId = cursor.getInt(0);
        }
        cursor.close();
        return lastId;
    }

    public void insertFavourite(Favourites favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", favourite.getId());
        values.put("TITLE", favourite.getTitle());
        values.put("DESCRIPTION", favourite.getDescrip());
        values.put("ICON", favourite.getImage());
        db.insert("favourites", null, values);
        db.close();
    }

    public void deleteFavouriteById(String favouritesId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM favourites where title=?", new String[]{favouritesId});
    }

    public Favourites getCurrenFavourite(String tl) {
//        ArrayList<Favourites> favourites = new ArrayList<>();
        Favourites fv=null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from favourites where TITLE= ?", new String[]{tl + ""});
//        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String descrip = cursor.getString(2);
            byte[] img = cursor.getBlob(3);
            fv=new Favourites(id,title,descrip,img);
        }
        cursor.close();
        return fv;
    }

}
