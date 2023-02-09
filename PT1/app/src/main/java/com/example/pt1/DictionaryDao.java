package com.example.pt1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DictionaryDao {

    public ArrayList<DictionaryWords> DictionaryTumKelimeler(DictionaryVeriTabani vt){
        ArrayList<DictionaryWords> DictionarykelimelerArrayList=new ArrayList<>();
        SQLiteDatabase db=vt.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM words  ",null);
        while (c.moveToNext()){
            DictionaryWords k=new DictionaryWords(c.getInt(c.getColumnIndex("word_id"))
                    ,c.getString(c.getColumnIndex("word_eng"))
                    ,c.getString(c.getColumnIndex("word_tr")));

            DictionarykelimelerArrayList.add(k);
        }
        return DictionarykelimelerArrayList;
    }

    public  ArrayList<DictionaryWords> DictionaryKelimeArama(DictionaryVeriTabani vt,String arananKelime){
        ArrayList<DictionaryWords> DictionarykelimelerArrayList=new ArrayList<>();
        SQLiteDatabase db=vt.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM words WHERE word_eng like '%"+arananKelime+"%'",null);
        while (c.moveToNext()){
            DictionaryWords k=new DictionaryWords(c.getInt(c.getColumnIndex("word_id"))
                    ,c.getString(c.getColumnIndex("word_eng"))
                    ,c.getString(c.getColumnIndex("word_tr")));

            DictionarykelimelerArrayList.add(k);
        }
        return DictionarykelimelerArrayList;
    }
}
