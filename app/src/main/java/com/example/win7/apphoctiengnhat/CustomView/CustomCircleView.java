package com.example.win7.apphoctiengnhat.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.win7.apphoctiengnhat.R;

/**
 * Created by ntkhai1996 on 16-Apr-18.
 */

public class CustomCircleView extends View {

    private Bitmap mBitmap;
    private float mAngle = 360;
    private Paint mPaint;
    private float left,top,right,bottom;

    public CustomCircleView(Context context) {
        super(context);
        init();
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setViewPaintColor(int color){
        mPaint.setColor(ResourcesCompat.getColor(getResources(),color,null));

        invalidate();
    }

    public void setViewBackground(int idBackgroundResource) {
        mBitmap = BitmapFactory.decodeResource(getResources(), idBackgroundResource);

        invalidate();
    }

    public void setHeightCircle(float top){
        this.top = top;

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        left   = -(getWidth()>>2);
        right  = getWidth() + (getWidth()>>2);
        bottom = getHeight();
        top    = (getHeight()>>1) + (getHeight()>>4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mBitmap != null){
            @SuppressLint("DrawAllocation")
            Shader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
        }

        canvas.drawRect(0,0,getWidth(),(getHeight()>>1) + (getHeight()>>3),mPaint);

        canvas.drawArc(left, top, right, bottom,0, mAngle,false,mPaint);

    }

}
