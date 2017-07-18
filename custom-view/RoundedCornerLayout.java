package com.videotomp3.audio.videoconverter.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.videotomp3.audio.videoconverter.R;

public class RoundedCornerLayout extends LinearLayout {

    private Bitmap maskBitmap;
    private Paint paint, maskPaint;
    private float cornerRadius;
 
    public RoundedCornerLayout(Context context) {
        super(context);
        init(context, null, 0);
    } 
 
    public RoundedCornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    } 
 
    public RoundedCornerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    } 
 
    private void init(Context context, AttributeSet attrs, int defStyle) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornerLayoutAttrs, 0, 0);
        try {
            this.cornerRadius = ta.getDimension(R.styleable.RoundedCornerLayoutAttrs_radius, 0);
        } finally {
            ta.recycle();
        }

        this.cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadius, metrics);
 
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        this.maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        this.maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        this.setWillNotDraw(false);
    } 
 
    @Override 
    public void draw(Canvas canvas) {
        Bitmap offscreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas offscreenCanvas = new Canvas(offscreenBitmap);
 
        super.draw(offscreenCanvas);
 
        if (this.maskBitmap == null) {
            this.maskBitmap = createMask(canvas.getWidth(), canvas.getHeight());
        } 
 
        offscreenCanvas.drawBitmap(this.maskBitmap, 0f, 0f, this.maskPaint);
        canvas.drawBitmap(offscreenBitmap, 0f, 0f, this.paint);
    } 
 
    private Bitmap createMask(int width, int height) {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(mask);
 
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
 
        canvas.drawRect(0, 0, width, height, paint);
 
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(0, 0, width, height), this.cornerRadius, this.cornerRadius, paint);
 
        return mask;
    } 
} 