package com.waytone.animationdemo.textv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by double on 2016/12/26.
 */

public class CustomTextView extends View {

    private static String TAG = CustomTextView.class.getSimpleName();
    private int mWidth;
    private int mHeight;
    private Paint mTextPaint;
    private String mText = "Text";


    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLUE);
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

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        float x = (getWidth() - mTextPaint.measureText(mText)) / 2;

        mTextPaint.setStrikeThruText(true);
        canvas.drawText(mText, x, y, mTextPaint);



        float textSize = mTextPaint.getTextSize();
        Log.d(TAG, "onDraw: mTextPaint.getTextSize()=" + textSize);
        getTextWidth(mText, mTextPaint);
        getTextHeight(mText, mTextPaint);
    }


    /**
     * @param text  绘制的文字
     * @param paint 画笔
     * @return 文字的宽度
     */
    public int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        Log.d(TAG, "getTextWidth: " + width);
        return width;
    }

    /**
     * @param text  绘制的文字
     * @param paint 画笔
     * @return 文字的宽度
     */
    public int getTextWidth2(String text, Paint paint) {
        return ((int) paint.measureText(text));
    }


    /**
     * @param text  绘制的文字
     * @param paint 画笔
     * @return 文字的高度
     */
    public int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.bottom + bounds.height();
        Log.d(TAG, "getTextHeight: " + height);
        return height;
    }
}
