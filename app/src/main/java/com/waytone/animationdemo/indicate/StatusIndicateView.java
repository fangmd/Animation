package com.waytone.animationdemo.indicate;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by double on 2016/12/26.
 */

public class StatusIndicateView extends View {

    private static String TAG = StatusIndicateView.class.getSimpleName();
    private int mWidth;
    private int mHeight;
    private Paint mCirclePaint;
    private Paint mCenterPaint;


    public StatusIndicateView(Context context) {
        this(context, null);
    }

    public StatusIndicateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusIndicateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.GREEN);
        mCirclePaint.setStrokeWidth(10);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mCenterPaint = new Paint();
        mCenterPaint.setAntiAlias(true);
        mCenterPaint.setColor(Color.BLACK);
        mCenterPaint.setStrokeWidth(10);
        mCenterPaint.setStyle(Paint.Style.FILL);

        mOkColor = Color.parseColor("#22C064");
        mIDLEColor = Color.parseColor("#7F8C8C");
        mSelectedColor = Color.parseColor("#22C064");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minWidth = 100;
        int minHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "onMeasure: widthMode=" + widthMode + " widthSize=" + widthSize + " heightMode=" + heightMode + " heightSize=" + heightSize);

        if (widthMode == MeasureSpec.EXACTLY) {  // match_parent
            mWidth = widthSize; //Must be this size
            Log.d(TAG, "onMeasure: EXACTLY");
        } else if (widthMode == MeasureSpec.AT_MOST) {  // wrap_content
            mWidth = Math.max(minWidth, widthSize); //Can't be bigger than...
            Log.d(TAG, "onMeasure: AT_MOST");
        } else {
            mWidth = minWidth;  //Be whatever you want
            Log.d(TAG, "onMeasure: UNSPECIFIED");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = Math.max(minHeight, heightSize);
        } else {
            mHeight = minHeight;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCenter(canvas);
        circleLoading(canvas);
    }

    private void drawCenter(Canvas canvas) {
        mCenterPaint.setColor(mIDLEColor);
        if (mStatus == LoadingViewStatus.IDLE) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 6, mCenterPaint);
        } else if (mStatus == LoadingViewStatus.SELECTED) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 6, mCenterPaint);
        } else if (mStatus == LoadingViewStatus.LOADING) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 6, mCenterPaint);
        } else if (mStatus == LoadingViewStatus.OK) {
            mCenterPaint.setColor(mOkColor);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 6, mCenterPaint);
        }
    }

    private void circleLoading(Canvas canvas) {
        switch (mStatus) {
            case LoadingViewStatus.IDLE:
                break;
            case LoadingViewStatus.LOADING:
                int angleDiffer = mProgress * 180 / 100;
                RectF rectF = new RectF(10, 10, mWidth - 10, mHeight - 10);
                canvas.drawArc(rectF, 0 + angleDiffer, 80, false, mCirclePaint);
                canvas.drawArc(rectF, 80 + 40 + angleDiffer, 80, false, mCirclePaint);
                canvas.drawArc(rectF, 80 + 40 + 80 + 40 + angleDiffer, 80, false, mCirclePaint);
                break;
            case LoadingViewStatus.SELECTED:
                int radius = mProgress * ((mWidth - 10) / 2) / 100;
                canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mCirclePaint);
                break;
            case LoadingViewStatus.OK:
                int radius2 = mProgress * ((mWidth - 10) / 2) / 100;
                canvas.drawCircle(mWidth / 2, mHeight / 2, radius2, mCirclePaint);
                break;
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAllAnimate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    // loading view mStatus --------------------------------
    // color
    private int mOkColor;
    private int mIDLEColor;
    private int mSelectedColor;
    private ValueAnimator mToIDLEAniamte;
    private ValueAnimator mLoadingAnimate;
    private boolean mAnimating;
    private ValueAnimator mToSelectedAnimate;
    private ValueAnimator mToOkAnimate;

    private int mProgress;
    private int mStatus = 0; // 0:idle , 1:loading , 2 selected

    public void setStatus(int status) {
        int lastStatus = mStatus;
        mStatus = status;
        stopAllAnimate();

        switch (mStatus) {
            case LoadingViewStatus.IDLE:
                startToIDLEAnimate();
                break;
            case LoadingViewStatus.LOADING:
                startLoadingAnimate();
                break;
            case LoadingViewStatus.SELECTED:
                startToSelectedAnimate();
                break;
            case LoadingViewStatus.OK:
                startToOkAnimate();
                break;
        }
    }

    private void startToOkAnimate() {
        if (mToOkAnimate == null) {
            mToOkAnimate = ValueAnimator.ofInt(100, 0);
            mToOkAnimate.setDuration(200);
            mToOkAnimate.addUpdateListener(animation -> {
                mProgress = ((int) animation.getAnimatedValue());
                invalidate();
            });
            mToOkAnimate.addListener(listener);
        }
        mProgress = 100;
        mToOkAnimate.start();
    }

    private void startToSelectedAnimate() {
        if (mToSelectedAnimate == null) {
            mToSelectedAnimate = ValueAnimator.ofInt(0, 100);
            mToSelectedAnimate.setDuration(200);
            mToSelectedAnimate.addUpdateListener(animation -> {
                mProgress = ((int) animation.getAnimatedValue());
                invalidate();
            });
            mToSelectedAnimate.addListener(listener);
        }
        mProgress = 0;
        mToSelectedAnimate.start();
    }

    private void startLoadingAnimate() {
        if (mLoadingAnimate == null) {
            mLoadingAnimate = ValueAnimator.ofInt(0, 100);
            mLoadingAnimate.setRepeatMode(ValueAnimator.RESTART);
            mLoadingAnimate.setRepeatCount(ValueAnimator.INFINITE);
            mLoadingAnimate.addUpdateListener(animation -> {
                mProgress = ((int) animation.getAnimatedValue());
                invalidate();
            });
            mLoadingAnimate.addListener(listener);
        }
        mProgress = 0;
        mLoadingAnimate.start();
    }

    private void startToIDLEAnimate() {
        if (mToIDLEAniamte == null) {
            mToIDLEAniamte = ValueAnimator.ofInt(0, 100);
            mToIDLEAniamte.addUpdateListener(animation -> {
                mProgress = ((int) animation.getAnimatedValue());
                invalidate();
            });
            mToIDLEAniamte.addListener(listener);
        }
        mProgress = 0;
        mToIDLEAniamte.start();
    }


    private void stopAllAnimate() {
        if (mLoadingAnimate != null && mLoadingAnimate.isStarted()) {
            mLoadingAnimate.end();
        }
        if (mToIDLEAniamte != null && mToIDLEAniamte.isStarted()) {
            mToIDLEAniamte.end();
        }
        if (mToSelectedAnimate != null && mToSelectedAnimate.isStarted()) {
            mToSelectedAnimate.end();
        }
        if (mToOkAnimate != null && mToOkAnimate.isStarted()) {
            mToOkAnimate.end();
        }

    }


    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            mAnimating = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mAnimating = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public static class LoadingViewStatus {
        public static final int IDLE = 0;
        public static final int LOADING = 1;
        public static final int SELECTED = 2;
        public static final int OK = 3;
    }


}
