package com.waytone.animationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button mBtnOne = (Button) findViewById(R.id.btn_one);
        mBtnOne.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, TransitionActivity.class)));

        Button mBtnExplode = (Button) findViewById(R.id.btn_explode);
        mBtnExplode.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, TransitionExplodeActivity.class)));


    }
}
