package com.example.pt1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.ArrayList;

public class KelimeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView rv_kelime;
    private DictionaryKelimeAdapter dictionaryKelimeAdapter;
    private DictionaryVeriTabani dictionaryVeriTabani;
    private ArrayList<DictionaryWords> dictionaryWordsArrayList;
    private Toolbar toolbar_kelime;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton fab_backToTop, fab_goToBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime);
        rv_kelime=findViewById(R.id.rv_kelime);
        toolbar_kelime=findViewById(R.id.toolbar_kelime);
        constraintLayout=findViewById(R.id.constraintLayoutKelime);
        fab_backToTop=findViewById(R.id.fab_backToTop);
        fab_goToBottom=findViewById(R.id.fab_goToBottom);
        enableSwipeToAdd();
        toolbar_kelime.setTitle(" İNGİLİZCE KELİMELER");
        toolbar_kelime.setLogo(R.drawable.logooo);
        toolbar_kelime.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar_kelime);
        fab_backToTop.setVisibility(View.GONE);
        fab_goToBottom.setVisibility(View.GONE);
        rv_kelime.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull  RecyclerView recyclerView, int dx, int dy) {
                if(dy>0){
                    fab_backToTop.setVisibility(View.GONE);
                }else if(dy<0){
                    fab_backToTop.setVisibility(View.VISIBLE);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        fab_backToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_kelime.scrollToPosition(0);
                fab_backToTop.setVisibility(View.GONE);
            }
        });
        rv_kelime.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull  RecyclerView recyclerView, int dx, int dy) {
                if(dy<0){
                    fab_goToBottom.setVisibility(View.GONE);
                }else if(dy>0){
                    fab_goToBottom.setVisibility(View.VISIBLE);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        fab_goToBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rv_kelime.scrollToPosition(dictionaryKelimeAdapter.getItemCount()-1);
                fab_goToBottom.setVisibility(View.GONE);
            }
        });
        //fastscroll size control
        StateListDrawable verticalThumbDrawable = (StateListDrawable) getResources().getDrawable(R.drawable.thumb_drawable);
        Drawable verticalTrackDrawable = getResources().getDrawable(R.drawable.line_drawable);
        StateListDrawable horizontalThumbDrawable = (StateListDrawable)getResources().getDrawable(R.drawable.thumb_drawable);
        Drawable horizontalTrackDrawable = getResources().getDrawable(R.drawable.line_drawable);

        Resources resources = getBaseContext().getResources();
        new FastScroller(rv_kelime, verticalThumbDrawable, verticalTrackDrawable,
                horizontalThumbDrawable, horizontalTrackDrawable,
                resources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness),
                resources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range),
                resources.getDimensionPixelOffset(R.dimen.fastscroll_margin));

        dictionaryVeriTabani=new DictionaryVeriTabani(this);
        DictionaryVeriTabaniKopyala();

        rv_kelime.setHasFixedSize(true);
        rv_kelime.setLayoutManager(new LinearLayoutManager(this));

        LayoutAnimationController animationController= AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animator_translate);
        rv_kelime.setLayoutAnimation(animationController);

        dictionaryWordsArrayList=new ArrayList<>();
       
        dictionaryWordsArrayList=new DictionaryDao().DictionaryTumKelimeler(dictionaryVeriTabani);
        dictionaryKelimeAdapter =new DictionaryKelimeAdapter(this,dictionaryWordsArrayList);
        rv_kelime.setAdapter(dictionaryKelimeAdapter);


    }



    private void enableSwipeToAdd(){
        SwipeToAddCallback swipeToAddCallback=new SwipeToAddCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                DictionaryWords dictionaryWords=dictionaryWordsArrayList.get(position);

                dictionaryKelimeAdapter.swipeToAdd(position,constraintLayout);
                vibrate();


            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToAddCallback);
        itemTouchhelper.attachToRecyclerView(rv_kelime);
    }

    public void vibrate(){
        Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(75);
    }

    public  void  DictionaryVeriTabaniKopyala(){
        DatabaseCopyHelper databaseCopyHelper=new DatabaseCopyHelper(this);
        try {
            databaseCopyHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        databaseCopyHelper.openDataBase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_kelime,menu);
        MenuItem menuItemSearch=menu.findItem(R.id.action_search_word);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItemSearch);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        dictionaryWordsArrayList=new DictionaryDao().DictionaryKelimeArama(dictionaryVeriTabani,newText);
        dictionaryKelimeAdapter=new DictionaryKelimeAdapter(KelimeActivity.this,dictionaryWordsArrayList);
        rv_kelime.setAdapter(dictionaryKelimeAdapter);
        return true;
    }


}