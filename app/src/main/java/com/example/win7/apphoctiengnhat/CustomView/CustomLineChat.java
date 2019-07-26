package com.example.win7.apphoctiengnhat.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.win7.apphoctiengnhat.R;

public class CustomLineChat extends View {

    private Paint mAbovePaint;
    private Paint mBelowPaint;
    private int color_gradient_above;
    private int color_gradient_below;

    public CustomLineChat(Context context) {
        super(context);
        init(null);
    }

    public CustomLineChat(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomLineChat(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){

        @SuppressLint("Recycle")
        TypedArray attrList = getContext().obtainStyledAttributes(attrs, R.styleable.CustomLineChat);

        mAbovePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBelowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBelowPaint.setColor(attrList.getColor(R.styleable.CustomLineChat_color_line_below,0));

        color_gradient_above = attrList.getColor(R.styleable.CustomLineChat_color_gradient_above,0);
        color_gradient_below = attrList.getColor(R.styleable.CustomLineChat_color_gradient_below,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLineAbove(canvas);
        drawLineBelow(canvas);
    }

    private void drawLineBelow(Canvas canvas) {
        canvas.drawRect(0, getHeight()>>1, getWidth(), getHeight(), mBelowPaint);
    }

    private void drawLineAbove(Canvas canvas) {
        // line above luôn là gradient
        mAbovePaint.setShader(new LinearGradient(0, 0, getWidth(), getHeight()/3, color_gradient_above, color_gradient_below, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, getWidth(), getHeight()>>1, mAbovePaint);
    }

    public void setColorLineAbove(int color){
        color_gradient_above = color_gradient_below = color;
        invalidate();
    }

    public void setColorLineBelow(int color){
        mBelowPaint.setColor(color);
        invalidate();
    }

    public void setGradientColorLineAbove(int color_gradient_above, int color_gradient_below){
        this.color_gradient_above = color_gradient_above;
        this.color_gradient_below = color_gradient_below;
        invalidate();
    }
}
