package com.waytone.animationdemo.indicate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waytone.animationdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndicateAct extends AppCompatActivity {

    @BindView(R.id.tv_one)
    TextView mTvOne;
    @BindView(R.id.tv_two)
    TextView mTvTwo;
    @BindView(R.id.tv_three)
    TextView mTvThree;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.activity_indicate)
    LinearLayout mActivityIndicate;
    @BindView(R.id.status_indicate_layout)
    StatusIndicateLayout mStatusIndicateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicate);
        ButterKnife.bind(this);


        mStatusIndicateLayout.setStep(mStep, StatusIndicateView.LoadingViewStatus.SELECTED);
    }

    private int mStep = 0;
    private boolean loading = false; //  用于让第二项加载

    @OnClick(R.id.btn_submit)
    public void onClick() {

        if (mStep == 0) {

            // 步骤布局切换
            mStatusIndicateLayout.setStep(mStep, StatusIndicateView.LoadingViewStatus.SELECTED);

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> mStatusIndicateLayout.setStep(mStep++, StatusIndicateView.LoadingViewStatus.OK));
            }).start();


        } else if (mStep == 1) {
            if (loading) {
                mStatusIndicateLayout.setStep(mStep, StatusIndicateView.LoadingViewStatus.LOADING);

                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(() -> mStatusIndicateLayout.setStep(mStep++, StatusIndicateView.LoadingViewStatus.OK));
                }).start();


            } else {
                mStatusIndicateLayout.setStep(mStep, StatusIndicateView.LoadingViewStatus.SELECTED);
                loading = true;
            }

            // 中间切换
            if (mTvOne.getVisibility() == View.VISIBLE) {
                AnimateUtils.slideOutLeft(mTvOne);
            }
            if (mTvTwo.getVisibility() == View.GONE) {
                AnimateUtils.slideInRight(mTvTwo);
            }

        } else if (mStep == 2) {
            mStatusIndicateLayout.setStep(mStep, StatusIndicateView.LoadingViewStatus.SELECTED);

            // 中间切换
            if (mTvTwo.getVisibility() == View.VISIBLE) {
                AnimateUtils.slideOutLeft(mTvTwo);
            }
            if (mTvThree.getVisibility() == View.GONE) {
                AnimateUtils.slideInRight(mTvThree);
            }

        }

    }
}
