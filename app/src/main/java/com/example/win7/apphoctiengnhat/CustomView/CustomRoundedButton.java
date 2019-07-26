package com.example.win7.apphoctiengnhat.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.win7.apphoctiengnhat.R;

/**
 * Created by ntkhai1996 on 19-Apr-18.
 */

public class CustomRoundedButton extends View {

    private Paint mPaint;
    private TextPaint mTextPaint;
    private boolean isFirstTime    = true;
    private boolean onEndAnimation = false;
    private float left, top, right, bottom;
    private float CxCircleLeft, CxCircleRight, Cy, r;
    private int duration;
    private String text;

    public CustomRoundedButton(Context context) {
        super(context);
        init(null);
    }

    public CustomRoundedButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomRoundedButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){

        @SuppressLint("Recycle")
        TypedArray attrList = getContext().obtainStyledAttributes(attrs, R.styleable.CustomRoundedButton);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(attrList.getColor(R.styleable.CustomRoundedButton_buttonColor,0));

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(attrList.getColor(R.styleable.CustomRoundedButton_textColor,0));
        mTextPaint.setTextSize(attrList.getDimensionPixelSize(R.styleable.CustomRoundedButton_textSize,0));

        duration = attrList.getInteger(R.styleable.CustomRoundedButton_duration,0);
        text     = attrList.getString(R.styleable.CustomRoundedButton_text);

        if(duration == 0)
            duration = 20;
    }

    public void setButtonColor(int color){
        mPaint.setColor(ResourcesCompat.getColor(getResources(),color,null));
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int standardWidth  = widthMeasureSpec;
        int standartHeight = 120;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        // width
        if(widthMode == MeasureSpec.EXACTLY)
            width = widthSize;
        else if(widthMode == MeasureSpec.AT_MOST)
            width = Math.min(standardWidth,widthSize);
        else if(widthMode == MeasureSpec.UNSPECIFIED)
            width = standardWidth;

        //height
        if(heightMode == MeasureSpec.EXACTLY)
            height = heightSize;
        else if(heightMode == MeasureSpec.AT_MOST)
            height = Math.min(standartHeight,heightSize);
        else if(heightMode == MeasureSpec.UNSPECIFIED)
            height = standartHeight;

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isFirstTime){

            isFirstTime = false;

            // Rectangle
            left   = getWidth()>>1;
            right  = getWidth()>>1;
            top    = 0;
            bottom = getHeight();

            // Circle
            CxCircleLeft  = getWidth()>>1;
            CxCircleRight = getWidth()>>1;
            r             = (bottom - top)/2;
            Cy            = getHeight()>>1;
        }
        else if(left > r) {
            if( (left-duration) < r){
                CxCircleLeft  = left  = r;
                CxCircleRight = right = getWidth() - r;
            } else {
                left  -= duration;
                right += duration;
                CxCircleLeft  -= duration;
                CxCircleRight += duration;
            }
        } else {
            onEndAnimation = true;
        }

        canvas.drawRect(left, top, right, bottom, mPaint);
        canvas.drawCircle(CxCircleLeft,  Cy, r, mPaint);
        canvas.drawCircle(CxCircleRight, Cy, r, mPaint);

        if(onEndAnimation)
            drawText(canvas);

        invalidate();
    }

    private void drawText(Canvas canvas){

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2)) ;
        //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.

        canvas.drawText(text, xPos, yPos, mTextPaint);
    }
}
