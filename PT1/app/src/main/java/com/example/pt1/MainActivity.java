package com.example.pt1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends AppCompatActivity {
    CardView kelimelerim_view,kelime_view,secenekler_view,cikis_view;
    SharedPreferences sharedPreferences;
    Boolean isDarkModeOn;
    Animation animation_from_left,animation_from_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if(isDarkModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

            kelimelerim_view=findViewById(R.id.kelimelerim_view);
            cikis_view=findViewById(R.id.cikis_view);
            kelime_view=findViewById(R.id.kelime_view);
            secenekler_view=findViewById(R.id.secenekler_view);

            animation_from_left=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_left);
            animation_from_right=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_right);

            kelimelerim_view.setAnimation(animation_from_left);
            secenekler_view.setAnimation(animation_from_left);
            kelime_view.setAnimation(animation_from_right);
            cikis_view.setAnimation(animation_from_right);


        kelimelerim_view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Intent intent=new Intent(MainActivity.this,KelimeYuklemeActivity.class);
                    startActivity(intent);


                }
            });




            kelime_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     Intent intent=new Intent(MainActivity.this,KelimeActivity.class);
                     startActivity(intent);


                }
            });

            secenekler_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent=new Intent(MainActivity.this,SeceneklerActivity.class);
                     startActivity(intent);




                }
            });

            cikis_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    finish();


                }
            });



    }
}