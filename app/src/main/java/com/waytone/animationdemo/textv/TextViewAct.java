package com.waytone.animationdemo.textv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.waytone.animationdemo.R;
import com.waytone.animationdemo.indicate.StatusIndicateView;

public class TextViewAct extends AppCompatActivity {

    private CircleView mViewById;
    private StatusIndicateView mStatusIndicateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);


        mViewById = (CircleView) findViewById(R.id.circleView);


        mStatusIndicateView = (StatusIndicateView) findViewById(R.id.status_indicate_view);




    }

    public void start(View view) {

        mViewById.start();
//        mViewById.repeatStart(ValueAnimator.REVERSE);

        mStatusIndicateView.setStatus(StatusIndicateView.LoadingViewStatus.LOADING);
    }

    public void stop(View view) {
//        mViewById.stop();
        mViewById.end();


        mStatusIndicateView.setStatus(StatusIndicateView.LoadingViewStatus.IDLE);
    }

    public void pause(View view){
        mStatusIndicateView.setStatus(StatusIndicateView.LoadingViewStatus.SELECTED);
    }

    public void ok(View view) {
        mStatusIndicateView.setStatus(StatusIndicateView.LoadingViewStatus.OK);
    }
}
