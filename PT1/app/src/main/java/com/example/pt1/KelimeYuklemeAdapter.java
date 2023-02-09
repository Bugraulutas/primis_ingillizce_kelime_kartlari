package com.example.pt1;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class KelimeYuklemeAdapter extends RecyclerView.Adapter<KelimeYuklemeAdapter.KelimeYuklemeNesneTutucu>{
    private Context context;
    private List<Kelimeler> kelimelerList;
    private VeriTabani vt;
    private Dialog dialog;
    private View view;


    public KelimeYuklemeAdapter(Context context, List<Kelimeler> kelimelerList, VeriTabani vt) {
        this.context = context;
        this.kelimelerList = kelimelerList;
        this.vt = vt;
    }

    @NonNull
    @Override
    public KelimeYuklemeNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_kelimeyukleme,parent,false);
        return new KelimeYuklemeNesneTutucu(view);

    }




    public class KelimeYuklemeNesneTutucu extends RecyclerView.ViewHolder {
        public TextView textViewid_kelimeyukleme,textViewing_kelimeyukleme,textViewtr_kelimeyukleme;
        public CardView cardView_kelimeyukleme;


        public KelimeYuklemeNesneTutucu(@NonNull View itemView) {
            super(itemView);


            textViewing_kelimeyukleme=itemView.findViewById(R.id.textViewing_kelimeyukleme);
            cardView_kelimeyukleme=itemView.findViewById(R.id.cardview_kelimeyukleme);

            textViewing_kelimeyukleme.setMarqueeRepeatLimit(1);
            textViewing_kelimeyukleme.setHorizontallyScrolling(true);
            textViewing_kelimeyukleme.setSingleLine();
            textViewing_kelimeyukleme.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textViewing_kelimeyukleme.setSelected(true);



        }
    }
    @Override
    public void onBindViewHolder(@NonNull KelimeYuklemeNesneTutucu holder, int position) {


            Kelimeler k=kelimelerList.get(position);
            holder.textViewing_kelimeyukleme.setText(k.getKelime_ing());


       // Animation anim_Translate = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.item_animasyon);
       // holder.cardView_kelimeyukleme.startAnimation(anim_Translate);

            holder.cardView_kelimeyukleme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   alertDialogDuzenle(v,k);

                }
            });




    }



    @Override
    public int getItemCount() {
        return kelimelerList.size();
    }

    public void removeItem(int position, ConstraintLayout c) {
        final Kelimeler k =kelimelerList.get(position);
        new KelimelerDao().kelimeSil(vt,k.getKelime_id());

        Snackbar mySnacbar= Snackbar.make(c,"'"+k.getKelime_ing()+"'"+" Kelimelerimden Silindi",Snackbar.LENGTH_SHORT);
        mySnacbar.getView().setBackgroundColor(Color.parseColor("#30396C"));
        View snackbarView=mySnacbar.getView();
        TextView textView=snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        mySnacbar.show();


        kelimelerList = new KelimelerDao().tumKelimeler(vt);

        notifyDataSetChanged();
    }

    



    public void alertDialogDuzenle(View view1,Kelimeler k){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.alertdialog_duzenle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
            EditText editText_ingilizce_duzenle=dialog.findViewById(R.id.editText_ingilizce_duzenle);
            EditText editText_turkce_duzenle=dialog.findViewById(R.id.editText_turkce_duzenle);
            Button button_iptal_duzenle=dialog.findViewById(R.id.button1);
            Button button_duzenle=dialog.findViewById(R.id.button2);

            editText_ingilizce_duzenle.setText(k.getKelime_ing());
            editText_turkce_duzenle.setText(k.getKelime_tr());

            button_duzenle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String  kelime_ing=editText_ingilizce_duzenle.getText().toString().trim();
                    String  kelime_tr=editText_turkce_duzenle.getText().toString().trim();
                            if(kelime_ing.equals(k.getKelime_ing())&&kelime_tr.equals(k.getKelime_tr())){
                                Snackbar mySnacbar= Snackbar.make(view1,"Kelimeyi Düzenlediğinize Emin Olun",Snackbar.LENGTH_SHORT);
                                mySnacbar.getView().setBackgroundColor(Color.parseColor("#30396C"));
                                View snackbarView=mySnacbar.getView();
                                TextView textView=snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                textView.setTextColor(Color.WHITE);
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                mySnacbar.show();

                            }else if(kelime_ing.isEmpty()){
                        Snackbar mySnacbar= Snackbar.make(view1," Lütfen Bir Kelime Girin",Snackbar.LENGTH_SHORT);
                        mySnacbar.getView().setBackgroundColor(Color.parseColor("#30396C"));
                        View snackbarView=mySnacbar.getView();
                        TextView textView=snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        mySnacbar.show();
                    }
                                else
                    {
                                Snackbar mySnacbar= Snackbar.make(view1,"Kelime Düzenlendi",Snackbar.LENGTH_SHORT);
                                mySnacbar.getView().setBackgroundColor(Color.parseColor("#30396C"));
                                View snackbarView=mySnacbar.getView();
                                TextView textView=snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                textView.setTextColor(Color.WHITE);
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                mySnacbar.show();
                            }


                    new KelimelerDao().kelimeGuncelle(vt,k.getKelime_id(),kelime_ing,kelime_tr);
                    kelimelerList=new KelimelerDao().tumKelimeler(vt);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            button_iptal_duzenle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
             dialog.getWindow().getAttributes().windowAnimations=R.style.DS;
            dialog.show();
    }



}
