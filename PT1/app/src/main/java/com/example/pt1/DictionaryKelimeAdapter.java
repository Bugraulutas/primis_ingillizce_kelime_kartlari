package com.example.pt1;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class DictionaryKelimeAdapter extends RecyclerView.Adapter<DictionaryKelimeAdapter.CardViewNesneleriTutucu> {

    private Context mContext;
    private List<DictionaryWords> dictionaryWordsList;
    private VeriTabani vt;
    private Toast mToastToShow;
    private Snackbar mySnacbar,mySnacbar2;
    private List<Kelimeler> kelimelerList;
    private Dialog dialog;


    public DictionaryKelimeAdapter(Context mContext, List<DictionaryWords> dictionaryWordsList) {
        this.mContext = mContext;
        this.dictionaryWordsList = dictionaryWordsList;
    }
    public class CardViewNesneleriTutucu extends RecyclerView.ViewHolder {
        private TextView textView_wordEng,textView_wordTr;
        public CardViewNesneleriTutucu(@NonNull View itemView) {
            super(itemView);
            textView_wordEng=itemView.findViewById(R.id.textView_wordEng);


            textView_wordEng.setMarqueeRepeatLimit(-1);
            textView_wordEng.setHorizontallyScrolling(true);
            textView_wordEng.setSingleLine();;
            textView_wordEng.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView_wordEng.setSelected(true);



        }
    }

    @NonNull
    @Override
    public CardViewNesneleriTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_kelime,parent,false);
        return new CardViewNesneleriTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewNesneleriTutucu holder, int position) {
            DictionaryWords k=dictionaryWordsList.get(position);

            holder.textView_wordEng.setText(k.getWord_eng());

    }

    @Override
    public int getItemCount() {
        return dictionaryWordsList.size();
    }

    public  void swipeToAdd(int position, ConstraintLayout constraintLayout){
        DictionaryWords words=dictionaryWordsList.get(position);
        vt=new VeriTabani(mContext);
        dialog=new Dialog(mContext);
        kelimelerList=new KelimelerDao().tumKelimeler(vt);
        Kelimeler k= null;

        if(kelimelerList.isEmpty()||!kelimelerList.isEmpty()) {

            new KelimelerDao().kelimeEkle(vt, words.getWord_eng(), words.getWord_tr());
        }
        for(int i=0;i<kelimelerList.size();i++){
            k=kelimelerList.get(i);

            if(words.getWord_eng().equals(k.getKelime_ing())) {
            alert(constraintLayout, words);

            }

        }
                ShowSnackBar(constraintLayout, words);
                notifyDataSetChanged();

    }


    public void ShowSnackBar(ConstraintLayout constraintLayout, DictionaryWords words){

        mySnacbar= Snackbar.make(constraintLayout,"'"+words.getWord_eng()+"'"+" Kelimelerime Eklendi",Snackbar.LENGTH_SHORT);
        mySnacbar.getView().setBackgroundColor(Color.parseColor("#30396C"));
        View snackbarView=mySnacbar.getView();

        TextView textView=snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        if(dialog.isShowing()){
            mySnacbar.dismiss();

        }
        if(!dialog.isShowing()){
            mySnacbar.show();

        }

    }

    public void ShowToastForAdd(DictionaryWords words) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 700;
        mToastToShow = Toast.makeText(mContext, words.getWord_eng()+" Eklendi", Toast.LENGTH_SHORT);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 650 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }

    public void alert(ConstraintLayout constraintLayout, DictionaryWords words){

        dialog.setContentView(R.layout.alertdialog_ing_kelimeler_kontrol);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText editText_ingilizce=dialog.findViewById(R.id.editText_ingilizce_duzenle);
        EditText editText_turkce=dialog.findViewById(R.id.editText_turkce_duzenle);

        Button button_iptal=dialog.findViewById(R.id.button1);
        Button button_ekle=dialog.findViewById(R.id.button2);

        button_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySnacbar2= Snackbar.make(constraintLayout,"'"+words.getWord_eng()+"'"+" Kelimelerime Tekrar Eklendi",Snackbar.LENGTH_SHORT);
                mySnacbar2.getView().setBackgroundColor(Color.parseColor("#30396C"));
                View snackbarView=mySnacbar2.getView();

                TextView textView=snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                mySnacbar2.show();
                dialog.dismiss();
            }
        });
        button_iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kelimelerList=new KelimelerDao().tumKelimeler(vt);
                Kelimeler k=new Kelimeler();

                for(int i=0;i<kelimelerList.size();i++){
                    k=kelimelerList.get(i);
                }
                new KelimelerDao().kelimeSil(vt,k.getKelime_id());
                dialog.dismiss();

            }
        });
        dialog.getWindow().getAttributes().windowAnimations=R.style.DS;
        dialog.show();
    }

}
