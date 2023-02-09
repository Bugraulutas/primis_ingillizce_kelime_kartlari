package com.example.pt1;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class KartTasarimAdapter extends RecyclerView.Adapter<KartTasarimAdapter.NesneTutucu>implements TextToSpeech.OnInitListener {
    private Context context;
    private List<Kelimeler> kelimelerlist;
    private VeriTabani vt;
    private ArrayList<Integer> arrayListShuffle;
    TextToSpeech textToSpeech;



    public KartTasarimAdapter(Context context, List<Kelimeler> kelimelerlist, VeriTabani vt, ArrayList<Integer> arrayListShuffle) {
        this.context = context;
        this.kelimelerlist = kelimelerlist;
        this.vt = vt;
        this.arrayListShuffle = arrayListShuffle;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            textToSpeech.setPitch(1);
            textToSpeech.setSpeechRate(1);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Bu dil desteklenmemektedir");
            }

        } else {
            Log.e("TTS", "Initilizationda sorunla karşılaşıldı!");
        }
    }




    public class NesneTutucu extends RecyclerView.ViewHolder{
        public TextView textViewing,textViewtr;
        public ImageView imageView_ses;

        public NesneTutucu(@NonNull View itemView) {
            super(itemView);

            textViewing=itemView.findViewById(R.id.textViewing);
            textViewtr=itemView.findViewById(R.id.textViewtr);
            imageView_ses=itemView.findViewById(R.id.imageview_ses);

        }
    }

    @NonNull
    @Override
    public NesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_kart_tasarim,parent,false);
        return new NesneTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NesneTutucu holder, int position) {

        textToSpeech = new TextToSpeech(context, (TextToSpeech.OnInitListener) this, "com.google.android.tts");

        final  Integer sayi=arrayListShuffle.get(position);

        final Kelimeler kelime=kelimelerlist.get(sayi);

            holder.textViewing.setText(kelime.getKelime_ing());

            holder.textViewtr.setText(kelime.getKelime_tr());
            holder.imageView_ses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = kelime.getKelime_ing().toString();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, bundle, null);
                    } else {
                        HashMap param = new HashMap<>();
                        param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                        textToSpeech.speak(text , TextToSpeech.QUEUE_FLUSH, param);
                    }                }
            });





            holder.textViewing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewing.setVisibility(View.INVISIBLE);
                holder.imageView_ses.setVisibility(View.INVISIBLE);

                holder.textViewtr.setVisibility(View.VISIBLE);
            }
        });
        holder.textViewtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewtr.setVisibility(View.INVISIBLE);
                holder.textViewing.setVisibility(View.VISIBLE);
                holder.imageView_ses.setVisibility(View.VISIBLE);

            }
        });

    }







    @Override
    public int getItemCount() {
        return kelimelerlist.size();
    }


}
