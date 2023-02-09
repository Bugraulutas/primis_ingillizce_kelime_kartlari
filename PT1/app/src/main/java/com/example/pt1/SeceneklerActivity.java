package com.example.pt1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SeceneklerActivity extends AppCompatActivity {
    private CardView cardViewSeceneklerNormal, cardViewSeceneklerNight;
    SharedPreferences sharedPreferences=null;
    Animation animation_from_left,animation_from_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_secenekler);
        cardViewSeceneklerNormal=findViewById(R.id.cardViewSecenekler_normal);
        cardViewSeceneklerNight =findViewById(R.id.cardViewSeceneklerNight);

        animation_from_left=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_left);
        animation_from_right=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_right);
        cardViewSeceneklerNormal.setAnimation(animation_from_left);
        cardViewSeceneklerNight.setAnimation(animation_from_right);

        cardViewSeceneklerNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isDarkModeOn", false);
                editor.commit();

             }

        });

        cardViewSeceneklerNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isDarkModeOn", true);
                editor.commit();


            }
        });





}


    }
