package com.example.pt1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class KelimeYuklemeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
private Toolbar toolbar_yukleme;
private VeriTabani vt;
private RecyclerView rv_kelimeyukleme;
private KelimeYuklemeAdapter adapter;
private ArrayList<Kelimeler> kelimelerArrayList;
private Dialog dialog;
private FloatingActionButton fab_kartYukleme;
private CardView cardViewKelimeYukleme;
private Animation dialog_open_animation;
private Snackbar mySnacbar,mySnacbar2;
private static  ConstraintLayout constraintLayoutKelimeYukleme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kelime_yukleme);
        toolbar_yukleme=findViewById(R.id.toolbar_yukleme);
        fab_kartYukleme=findViewById(R.id.fab_kartYukleme);
        cardViewKelimeYukleme=findViewById(R.id.cardview_kelimeyukleme);
        constraintLayoutKelimeYukleme=findViewById(R.id.constraintLayoutKelimeYukleme);
        rv_kelimeyukleme=findViewById(R.id.rv_kelime_yukleme);
        
        fab_kartYukleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KelimeYuklemeActivity.this,KartTasarimActivity.class);
                startActivity(intent);
                vibrate();
            }
        });




        dialog=new Dialog(this);



        toolbar_yukleme.setTitle(" KELİMELERİM");
        toolbar_yukleme.setLogo(R.drawable.logooo);
        toolbar_yukleme.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar_yukleme);


        enableSwipeToDelete();

        vt = new VeriTabani(this);

        rv_kelimeyukleme.setHasFixedSize(true);
        rv_kelimeyukleme.setLayoutManager(new LinearLayoutManager(this));

        LayoutAnimationController animationController=AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animator_translate);
        rv_kelimeyukleme.setLayoutAnimation(animationController);

        kelimelerArrayList=new KelimelerDao().tumKelimeler(vt);

        adapter=new KelimeYuklemeAdapter(this,kelimelerArrayList,vt);
        rv_kelimeyukleme.setAdapter(adapter);



 }



    private void enableSwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                Kelimeler k=kelimelerArrayList.get(position);
                adapter.removeItem(position,constraintLayoutKelimeYukleme);

                vibrate();
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv_kelimeyukleme);

    }

    public void vibrate(){
        Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }

    public void alertDialogEkle(){


        dialog.setContentView(R.layout.alertdialog_ekle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);


        EditText editText_ingilizce=dialog.findViewById(R.id.editText_ingilizce_duzenle);
        EditText editText_turkce=dialog.findViewById(R.id.editText_turkce_duzenle);
        Button button_iptal=dialog.findViewById(R.id.button1);
        Button button_ekle=dialog.findViewById(R.id.button2);

        button_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kelime_ing = editText_ingilizce.getText().toString().trim();
                String kelime_tr = editText_turkce.getText().toString().trim();
                new KelimelerDao().kelimeEkle(vt,kelime_ing,kelime_tr);


                mySnacbar= Snackbar.make(constraintLayoutKelimeYukleme,"'"+kelime_ing+"'"+" Kelimelerime Eklendi",Snackbar.LENGTH_SHORT);
                mySnacbar.getView().setBackgroundColor(Color.parseColor("#30396C"));
                View snackbarView=mySnacbar.getView();

                TextView textView=snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                mySnacbar.show();


                if(kelime_ing.isEmpty()){
                    mySnacbar2= Snackbar.make(constraintLayoutKelimeYukleme," Lütfen Bir Kelime Girin",Snackbar.LENGTH_SHORT);
                    mySnacbar2.getView().setBackgroundColor(Color.parseColor("#30396C"));
                    View snackbarView2=mySnacbar2.getView();

                    TextView textView2=snackbarView2.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView2.setTextColor(Color.WHITE);
                    textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    mySnacbar2.show();
                }

                kelimelerArrayList=new KelimelerDao().tumKelimeler(vt);
                adapter=new KelimeYuklemeAdapter(KelimeYuklemeActivity.this,kelimelerArrayList,vt);
                rv_kelimeyukleme.setAdapter(adapter);
                dialog.dismiss();
            }
        });
        button_iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations=R.style.DS;
        dialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_yukleme,menu);
        MenuItem menuItem_search=menu.findItem(R.id.action_search_yukleme);
        MenuItem menuItem_add=menu.findItem(R.id.action_add_yukleme);


        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem_search);
        searchView.setOnQueryTextListener(this);
      return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_yukleme:
                alertDialogEkle();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
       kelimelerArrayList=new KelimelerDao().kelimeAraTr(vt,newText);

        adapter=new KelimeYuklemeAdapter(this,kelimelerArrayList,vt);
        rv_kelimeyukleme.setAdapter(adapter);
        return true;
    }
}