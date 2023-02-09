package com.example.pt1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class KelimelerDao {
    
    public ArrayList<Kelimeler> tumKelimeler(VeriTabani vt){

        ArrayList<Kelimeler> kelimelerArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM kelimeler",null);

        while (c.moveToNext()){
            Kelimeler k = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id"))
                    ,c.getString(c.getColumnIndex("kelime_ing"))
                    ,c.getString(c.getColumnIndex("kelime_tr")));
            kelimelerArrayList.add(k);


        }

        db.close();

        return kelimelerArrayList;
    }



    public ArrayList<Kelimeler> kelimeAraTr(VeriTabani vt, String aramaKelime){

        ArrayList<Kelimeler> kelimelerArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM kelimeler WHERE  kelime_ing LIKE'%"+aramaKelime+"%' OR kelime_tr LIKE '%"+aramaKelime+"%'  ",null);
        while (c.moveToNext()){
            Kelimeler k = new Kelimeler(c.getInt(c.getColumnIndex("kelime_id"))
                    ,c.getString(c.getColumnIndex("kelime_ing"))
                    ,c.getString(c.getColumnIndex("kelime_tr")));

            kelimelerArrayList.add(k);

        }

        db.close();

        return kelimelerArrayList;

    }


    public void kelimeSil(VeriTabani vt,int kelime_id){
        SQLiteDatabase db = vt.getWritableDatabase();
        db.delete("kelimeler","kelime_id=?",new String[]{String.valueOf(kelime_id)});
        db.close();
    }

    public void kelimeEkle(VeriTabani vt,String kelime_ing,String kelime_tr){
        SQLiteDatabase db = vt.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("kelime_ing",kelime_ing);
        values.put("kelime_tr",kelime_tr);

        db.insertOrThrow("kelimeler",null,values);

        db.close();

    }

    public void kelimeGuncelle(VeriTabani vt,int kelime_id,String kelime_ing,String kelime_tr){
        SQLiteDatabase db = vt.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("kelime_ing",kelime_ing);
        values.put("kelime_tr",kelime_tr);

        db.update("kelimeler",values,"kelime_id=?",new String[]{String.valueOf(kelime_id)});
        db.close();

    }


}
