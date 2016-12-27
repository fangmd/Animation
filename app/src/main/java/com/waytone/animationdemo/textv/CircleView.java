package com.waytone.animationdemo.textv;

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
import android.view.animation.LinearInterpolator;

/**
 * Created by double on 2016/12/26.
 */

public class CircleView extends View {

    private static String TAG = CircleView.class.getSimpleName();
    private int mWidth;
    private int mHeight;
    private Paint mCirclePaint;
    private Paint mCenterPaint;
    private Paint mTextPaint;
    private String mText = "Text";


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

        initAnimate();
    }


    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStrokeWidth(10);
        mCirclePaint.setTextSize(160);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mCenterPaint = new Paint();
        mCenterPaint.setAntiAlias(true);
        mCenterPaint.setColor(Color.RED);
        mCenterPaint.setStrokeWidth(10);
        mCenterPaint.setTextSize(160);
        mCenterPaint.setStyle(Paint.Style.FILL);


        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(5);
        mTextPaint.setTextSize(160);
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

//        drawCenterText();

        drawCenter(canvas);

        // 绘制表盘
//        drawBiaopan(canvas);

        // 绘制 圆形 loading 动画  360 - 3 * 40 = 240
        circleLoading(canvas);

    }

    private void drawCenterText(Canvas canvas) {
        float x = (mWidth - mTextPaint.measureText(mText)) / 2;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText(mText, x, y, mTextPaint);
    }

    private void drawCenter(Canvas canvas) {
        if (mStep == 0 || mStep == 1) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 4, mCenterPaint);
        } else if (mStep == 2) {
            int radius = mProgress * (mWidth / 3 - mWidth / 4) / 100 + mWidth / 4;
            canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mCenterPaint);
        }
    }

    private void circleLoading(Canvas canvas) {
        if (mStep == 0 || mStep == 1) {
            int startAngle = mProgress * 720 / 100;
            RectF rectF = new RectF(0 + 10, 0 + 10, mWidth - 10, mHeight - 10);
            canvas.drawArc(rectF, startAngle, 80, false, mCirclePaint);
            canvas.drawArc(rectF, startAngle + 80 + 40, 80, false, mCirclePaint); // 120 - 200
            canvas.drawArc(rectF, startAngle + 80 + 40 + 80 + 40, 80, false, mCirclePaint);
        } else if (mStep == 2) {
            // 矩形框：（10，10）（mWidth-10，mHeight-10）--》 （30，30）（mWidth-30，mHeight-30）
            int differ = mProgress * 20 / 100;
            // 圆弧： 80 --》 120
            int angleDiffer = mProgress * 40 / 100;
            int startAngle = mProgress * 360 / 100;
            RectF rectF = new RectF(0 + 10 + differ, 0 + 10 + differ, mWidth - 10 - differ, mHeight - 10 - differ);
            canvas.drawArc(rectF, startAngle, 80 + angleDiffer, false, mCirclePaint);
            canvas.drawArc(rectF, startAngle + 80 + 40, 80 + angleDiffer, false, mCirclePaint); // 120 - 200
            canvas.drawArc(rectF, startAngle + 80 + 40 + 80 + 40, 80 + angleDiffer, false, mCirclePaint);
        }
    }

    private void drawBiaopan(Canvas canvas) {
        int step = mProgress * 60 / 100;
        for (int i = 0; i < step; i++) {
            canvas.drawLine(mWidth / 2, 0, mWidth / 2, 30, mCirclePaint);
            canvas.rotate(6, mWidth / 2, mWidth / 2);
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void pause() {
//        if (mStepOneAnimator.isRunning()) {
//            mStepOneAnimator.pause();
//        }
//    }

//    public void stop() {
//        if (mStepOneAnimator.isStarted()) {
//            mStepOneAnimator.end(); // 保持终点状态
//        }
//    }


    private ValueAnimator mStepOneAnimator;
    private int mProgress;
    private long mAnimationTime;

    private void initAnimate() {
        mStepOneAnimator = ValueAnimator.ofInt(0, 100);
        mStepOneAnimator.setDuration(2000);
//        mStepOneAnimator.setRepeatCount(ValueAnimator.RESTART);
        mStepOneAnimator.setInterpolator(new LinearInterpolator());

        mStepOneAnimator.addUpdateListener(animation -> {
            mProgress = (int) animation.getAnimatedValue();
            Log.d(TAG, "run: mProgress" + mProgress);
            invalidate();
        });

        mStepOneAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (((int) mStepOneAnimator.getAnimatedValue()) >= 98) {
                    mAnimationTime = 0;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void stop() {
        if (mStepOneAnimator != null) {
            mStep = 0;
            mAnimationTime = mStepOneAnimator.getCurrentPlayTime();
            mStepOneAnimator.cancel();  // 保持停止状态
        }
    }

    public void start() {
        if (mStepOneAnimator != null) {
            mStep = 0;
            mStepOneAnimator.start();
            mStepOneAnimator.setCurrentPlayTime(mAnimationTime);
        }
    }

    public void repeatStart() {
        repeatStart(ValueAnimator.REVERSE);
    }

    public void repeatStart(int repeatMode) {
        if (repeatMode == ValueAnimator.RESTART || repeatMode == ValueAnimator.REVERSE) {
            mStepOneAnimator.setRepeatMode(repeatMode);
        } else {
            Log.e(TAG, "repeatStart: repeatMode error");
            mStepOneAnimator.setRepeatMode(ValueAnimator.RESTART);
        }
        mStepOneAnimator.setRepeatCount(ValueAnimator.INFINITE);
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        run();
    }


    // ------------ 分步动画

    /**
     * 动画阶段
     */
    private int mStep;

    public void end() {
        // progress 0 -> 100 (100=0 的状态) 收尾动画
        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, 100);
        valueAnimator1.setDuration(300);
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.addUpdateListener(animation -> {
            mStep = 2;
            mProgress = ((int) animation.getAnimatedValue());
            invalidate();
        });


        // progress 收尾 ( 如果收尾调用的时候 mProgress 不在 100 的位置)
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mProgress, 100);
        valueAnimator.setDuration(100);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            mStep = 1;
            mProgress = ((int) animation.getAnimatedValue());
            invalidate();
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                stopStepOneAnimator();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                valueAnimator1.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    private void stopStepOneAnimator() {
        if (mStepOneAnimator != null && mStepOneAnimator.isStarted()) {
            mStepOneAnimator.cancel();
        }
    }



}
