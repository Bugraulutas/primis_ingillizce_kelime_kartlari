package com.example.pt1;

public class DictionaryWords {
    private int word_id;
    private String word_eng;
    private String word_tr;

    public DictionaryWords() {
    }

    public DictionaryWords(int word_id, String word_eng, String word_tr) {
        this.word_id = word_id;
        this.word_eng = word_eng;
        this.word_tr = word_tr;
    }

    public int getWord_id() {
        return word_id;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public String getWord_eng() {
        return word_eng;
    }

    public void setWord_eng(String word_eng) {
        this.word_eng = word_eng;
    }

    public String getWord_tr() {
        return word_tr;
    }

    public void setWord_tr(String word_tr) {
        this.word_tr = word_tr;
    }
}
