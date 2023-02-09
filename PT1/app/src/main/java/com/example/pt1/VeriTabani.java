package com.example.pt1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VeriTabani extends SQLiteOpenHelper {
    public VeriTabani(@Nullable Context context) {
        super(context, "carmio.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE kelimeler (kelime_id INTEGER PRIMARY KEY AUTOINCREMENT,kelime_ing TEXT,kelime_tr TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS kelimeler");
            onCreate(db);
    }
}
