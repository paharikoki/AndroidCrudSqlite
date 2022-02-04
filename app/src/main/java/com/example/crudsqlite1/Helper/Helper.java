package com.example.crudsqlite1.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import com.example.crudsqlite1.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class Helper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "rental.db";
    private static final int DATABASE_VERSION = 1;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "Create table tb_rental (id integer primary key autoincrement,nama text not null,warna text not null,image blob default null)";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists tb_rental");
        onCreate(sqLiteDatabase);
    }

    public List<Model> getAll() {
        List<Model> list = new ArrayList<>();
        String QUERY = "SELECT * FROM tb_rental";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                Model m = new Model();
                m.setId(cursor.getString(0));
                m.setNama(cursor.getString(1));
                m.setWarna(cursor.getString(2));
                m.setImage(Base64.decode(cursor.getBlob(3), Base64.DEFAULT));
                list.add(m);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void Insert(String nama, String warna, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Insert into tb_rental (nama,warna,image) VALUES('" + nama + "','" + warna + "','" + image + "')";
        db.execSQL(Query);
    }

    public void Update(int id, String nama, String warna, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Update tb_rental set nama='" + nama + "',warna='" + warna + "',image='" + image + "' where id='" + id + "'";
        db.execSQL(Query);
    }

    public void Delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Delete From tb_rental where id='" + id + "'";
        db.execSQL(Query);
    }
}
