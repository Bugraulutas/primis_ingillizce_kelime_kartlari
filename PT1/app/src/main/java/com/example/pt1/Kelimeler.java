package com.example.pt1;

public class Kelimeler {
    private int kelime_id;
    private String kelime_ing;
    private String kelime_tr;

    public Kelimeler() {
    }

    public Kelimeler(int kelime_id, String kelime_ing, String kelime_tr) {
        this.kelime_id = kelime_id;
        this.kelime_ing = kelime_ing;
        this.kelime_tr = kelime_tr;
    }

    public int getKelime_id() {
        return kelime_id;
    }

    public void setKelime_id(int kelime_id) {
        this.kelime_id = kelime_id;
    }

    public String getKelime_ing() {
        return kelime_ing;
    }

    public void setKelime_ing(String kelime_ing) {
        this.kelime_ing = kelime_ing;
    }

    public String getKelime_tr() {
        return kelime_tr;
    }

    public void setKelime_tr(String kelime_tr) {
        this.kelime_tr = kelime_tr;
    }
}
