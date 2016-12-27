package com.waytone.animationdemo.indicate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.waytone.animationdemo.R;

/**
 * Created by double on 2016/12/27.
 */

public class StatusIndicateLayout extends FrameLayout {


    private StatusIndicateView mSIVFirst;
    private StatusIndicateView mSIVSecond;
    private StatusIndicateView mSIVThird;

    public StatusIndicateLayout(Context context) {
        this(context, null);
    }

    public StatusIndicateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusIndicateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.status_indicate, this);

        mSIVFirst = ((StatusIndicateView) findViewById(R.id.siv_first));
        mSIVSecond = ((StatusIndicateView) findViewById(R.id.siv_second));
        mSIVThird = ((StatusIndicateView) findViewById(R.id.siv_third));

        init();
    }

    private void init() {
        mSIVFirst.setStatus(StatusIndicateView.LoadingViewStatus.SELECTED);
        mSIVSecond.setStatus(StatusIndicateView.LoadingViewStatus.IDLE);
        mSIVThird.setStatus(StatusIndicateView.LoadingViewStatus.IDLE);
    }

    public void setStep(int i, int status) {
        switch (i) {
            case 0:
                mSIVFirst.setStatus(status);
                break;
            case 1:
                mSIVSecond.setStatus(status);
                break;
            case 2:
                mSIVThird.setStatus(status);
                break;
            default:

                break;
        }
    }
}
