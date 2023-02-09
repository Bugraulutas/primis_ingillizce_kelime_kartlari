package com.example.pt1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DictionaryVeriTabani extends SQLiteOpenHelper {
    public DictionaryVeriTabani(@Nullable Context context) {
        super(context, "dictionary.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  IF NOT EXISTS words (word_id PRIMARY KEY AUTOINCREMENT,word_eng TEXT,word_tr TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS words");
        onCreate(db);
    }
}
