package com.example.pt1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;


public class KartTasarimActivity extends AppCompatActivity  {
    private ViewPager2 vp2;
    private VeriTabani vt;
    private KartTasarimAdapter adapter;
    private ArrayList<Kelimeler> kelimelerArrayList;
    private FloatingActionButton fab_kelimeKart;
    private ArrayList<Integer> arrayListShuffle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kart_tasarim);
        fab_kelimeKart=findViewById(R.id.fab_kelimeKart);



        vt=new VeriTabani(this);
        vp2=findViewById(R.id.vp2);

        arrayListShuffle=new ArrayList<>();
        kelimelerArrayList = new KelimelerDao().tumKelimeler(vt);
        for(int i=0;i<kelimelerArrayList.size();i++) {
            arrayListShuffle.add(i);
        }


        fab_kelimeKart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(arrayListShuffle);
                vibrate();
                adapter = new KartTasarimAdapter(KartTasarimActivity.this,kelimelerArrayList,vt,arrayListShuffle);
                vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                vp2.setAdapter(adapter);
                vp2.setOffscreenPageLimit(10000);
            }
        });




        adapter = new KartTasarimAdapter(this,kelimelerArrayList,vt,arrayListShuffle);
        vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp2.setAdapter(adapter);
        vp2.setOffscreenPageLimit(10000);


    }

    public void vibrate(){
        Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }


}

