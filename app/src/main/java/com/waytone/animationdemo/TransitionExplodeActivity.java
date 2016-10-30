package com.waytone.animationdemo;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class TransitionExplodeActivity extends AppCompatActivity {

    private RecyclerView mRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_explode);

        mRV = (RecyclerView) findViewById(R.id.rv_explode);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRV.setLayoutManager(gridLayoutManager);
        mRV.setAdapter(new RVAdapter());

        mRV.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 10);
            }
        });


    }
}
