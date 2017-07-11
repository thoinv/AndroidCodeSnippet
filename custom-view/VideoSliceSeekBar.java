package com.videotomp3.audio.videoconverter.views.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.videotomp3.audio.videoconverter.R;


/**
 * Created by Cam on 1/29/2016.
 */
public class VideoSliceSeekBar extends android.support.v7.widget.AppCompatImageView {
    private static final int SELECT_THUMB_LEFT = 1;
    private static final int SELECT_THUMB_NON = 0;
    private static final int SELECT_THUMB_RIGHT = 2;
    private boolean blocked;
    private boolean isVideoStatusDisplay;
    private int maxValue;
    private Paint paint;
    private Paint paintThumb;
    private int progressBottom;
    private int progressColor;
    private int progressHalfHeight;
    private int progressMinDiff;
    private int progressMinDiffPixels;
    private int progressTop;
    private SeekBarChangeListener scl;
    private int secondaryProgressColor;
    private int selectedThumb;
    private Bitmap thumbCurrentVideoPosition;
    private int thumbCurrentVideoPositionHalfWidth;
    private int thumbCurrentVideoPositionX;
    private int thumbCurrentVideoPositionY;
    private int thumbPadding;
    private Bitmap thumbSlice;
    private int thumbSliceHalfWidth;
    private int thumbSliceLeftValue;
    private int thumbSliceLeftX;
    private int thumbSliceRightValue;
    private int thumbSliceRightX;
    private int thumbSliceY;
    private Bitmap thumbSlicer;

    public interface SeekBarChangeListener {
        void SeekBarValueChanged(int i, int i2);
    }

    public VideoSliceSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.thumbSlice = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trim);
        this.thumbSlicer = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trim);
        this.thumbCurrentVideoPosition = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);
        this.progressMinDiff = 15;
        this.progressColor = getResources().getColor(R.color.white);
        this.secondaryProgressColor = getResources().getColor(R.color.colorPrimaryLight);
        this.progressHalfHeight = 6;
        this.thumbPadding = getResources().getDimensionPixelOffset(R.dimen.margin_5);
        this.maxValue = 100;
        this.paint = new Paint();
        this.paintThumb = new Paint();
    }

    public VideoSliceSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.thumbSlice = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trim);
        this.thumbSlicer = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trim);
        this.thumbCurrentVideoPosition = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);
        this.progressMinDiff = 15;
        this.progressColor = ContextCompat.getColor(getContext(), R.color.white);
        this.secondaryProgressColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryLight);
        this.progressHalfHeight = 6;
        this.thumbPadding = getResources().getDimensionPixelOffset(R.dimen.margin_5);
        this.maxValue = 100;
        this.paint = new Paint();
        this.paintThumb = new Paint();
    }

    public VideoSliceSeekBar(Context context) {
        super(context);
        this.thumbSlice = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trim);
        this.thumbSlicer = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trim);
        this.thumbCurrentVideoPosition = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);
        this.progressMinDiff = 15;
        this.progressColor = getResources().getColor(R.color.white);
        this.secondaryProgressColor = getResources().getColor(R.color.colorPrimary);
        this.progressHalfHeight = 6;
        this.thumbPadding = getResources().getDimensionPixelOffset(R.dimen.margin_5);
        this.maxValue = 100;
        this.paint = new Paint();
        this.paintThumb = new Paint();
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {
        if (this.thumbSlice.getHeight() > getHeight()) {
            getLayoutParams().height = this.thumbSlice.getHeight() - 20;
        }
        this.thumbSliceY = (getHeight() / SELECT_THUMB_RIGHT) - (this.thumbSlice.getHeight() / SELECT_THUMB_RIGHT  + 10);
        this.thumbCurrentVideoPositionY = (getHeight() / SELECT_THUMB_RIGHT) - (this.thumbCurrentVideoPosition.getHeight() / SELECT_THUMB_RIGHT );
        this.thumbSliceHalfWidth = this.thumbSlice.getWidth() / SELECT_THUMB_RIGHT;
        this.thumbCurrentVideoPositionHalfWidth = this.thumbCurrentVideoPosition.getWidth() / SELECT_THUMB_RIGHT;
        if (this.thumbSliceLeftX == 0 || this.thumbSliceRightX == 0) {
            this.thumbSliceLeftX = this.thumbPadding;
            this.thumbSliceRightX = getWidth() - this.thumbPadding;
        }
        this.progressMinDiffPixels = calculateCorrds(this.progressMinDiff) - (this.thumbPadding * SELECT_THUMB_RIGHT);
        this.progressTop = (getHeight() / SELECT_THUMB_RIGHT) - this.progressHalfHeight;
        this.progressBottom = (getHeight() / SELECT_THUMB_RIGHT) + this.progressHalfHeight;
        invalidate();
    }

    public void setSeekBarChangeListener(SeekBarChangeListener scl) {
        this.scl = scl;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(this.progressColor);
        canvas.drawRect(new Rect(this.thumbPadding, this.progressTop, this.thumbSliceLeftX, this.progressBottom), this.paint);
        canvas.drawRect(new Rect(this.thumbSliceRightX, this.progressTop, getWidth() - this.thumbPadding, this.progressBottom), this.paint);
        this.paint.setColor(this.secondaryProgressColor);
        canvas.drawRect(new Rect(this.thumbSliceLeftX, this.progressTop, this.thumbSliceRightX, this.progressBottom), this.paint);
        if (!this.blocked) {
            canvas.drawBitmap(this.thumbSlice, (float) (this.thumbSliceLeftX - this.thumbSliceHalfWidth), (float) this.thumbSliceY, this.paintThumb);
            canvas.drawBitmap(this.thumbSlicer, (float) (this.thumbSliceRightX - this.thumbSliceHalfWidth), (float) this.thumbSliceY, this.paintThumb);
        }
        if (this.isVideoStatusDisplay) {
            canvas.drawBitmap(this.thumbCurrentVideoPosition, (float) (this.thumbCurrentVideoPositionX - this.thumbCurrentVideoPositionHalfWidth), (float) this.thumbCurrentVideoPositionY, this.paintThumb);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.blocked) {
            int mx = (int) event.getX();
            switch (event.getAction()) {
                case SELECT_THUMB_NON /*0*/:
                    if ((mx < this.thumbSliceLeftX - this.thumbSliceHalfWidth || mx > this.thumbSliceLeftX + this.thumbSliceHalfWidth) && mx >= this.thumbSliceLeftX - this.thumbSliceHalfWidth) {
                        if ((mx < this.thumbSliceRightX - this.thumbSliceHalfWidth || mx > this.thumbSliceRightX + this.thumbSliceHalfWidth) && mx <= this.thumbSliceRightX + this.thumbSliceHalfWidth) {
                            if ((mx - this.thumbSliceLeftX) + this.thumbSliceHalfWidth >= (this.thumbSliceRightX - this.thumbSliceHalfWidth) - mx) {
                                if ((mx - this.thumbSliceLeftX) + this.thumbSliceHalfWidth > (this.thumbSliceRightX - this.thumbSliceHalfWidth) - mx) {
                                    this.selectedThumb = SELECT_THUMB_RIGHT;
                                    break;
                                }
                                break;
                            }
                            this.selectedThumb = SELECT_THUMB_LEFT;
                            break;
                        }
                        this.selectedThumb = SELECT_THUMB_RIGHT;
                        break;
                    }
                    this.selectedThumb = SELECT_THUMB_LEFT;

                    break;
                case SELECT_THUMB_LEFT /*1*/:
                    this.selectedThumb = SELECT_THUMB_NON;
                    break;
                case SELECT_THUMB_RIGHT /*2*/:
                    if ((mx <= (this.thumbSliceLeftX + this.thumbSliceHalfWidth) + SELECT_THUMB_NON && this.selectedThumb == SELECT_THUMB_RIGHT) || (mx >= (this.thumbSliceRightX - this.thumbSliceHalfWidth) + SELECT_THUMB_NON && this.selectedThumb == SELECT_THUMB_LEFT)) {
                        this.selectedThumb = SELECT_THUMB_NON;
                    }
                    if (this.selectedThumb != SELECT_THUMB_LEFT) {
                        if (this.selectedThumb == SELECT_THUMB_RIGHT) {
                            this.thumbSliceRightX = mx;
                            break;
                        }
                        break;
                    }
                    this.thumbSliceLeftX = mx;
                    break;
            }
            notifySeekBarValueChanged();
        }
        return true;
    }

    private void notifySeekBarValueChanged() {
        if (this.thumbSliceLeftX < this.thumbPadding) {
            this.thumbSliceLeftX = this.thumbPadding;
        }
        if (this.thumbSliceRightX < this.thumbPadding) {
            this.thumbSliceRightX = this.thumbPadding;
        }
        if (this.thumbSliceLeftX > getWidth() - this.thumbPadding) {
            this.thumbSliceLeftX = getWidth() - this.thumbPadding;
        }
        if (this.thumbSliceRightX > getWidth() - this.thumbPadding) {
            this.thumbSliceRightX = getWidth() - this.thumbPadding;
        }
        invalidate();
        if (this.scl != null) {
            calculateThumbValue();
            this.scl.SeekBarValueChanged(this.thumbSliceLeftValue, this.thumbSliceRightValue);

        }
    }

    private void calculateThumbValue() {
        this.thumbSliceLeftValue = ((this.maxValue / 1000) * (this.thumbSliceLeftX - this.thumbPadding)) / (getWidth() - (this.thumbPadding * SELECT_THUMB_RIGHT)) * 1000;
        this.thumbSliceRightValue = ((this.maxValue / 1000) * (this.thumbSliceRightX - this.thumbPadding)) / (getWidth() - (this.thumbPadding * SELECT_THUMB_RIGHT)) * 1000;
    }

    private int calculateCorrds(int progress) {
        return ((int) (((((double) getWidth()) - (2.0d * ((double) this.thumbPadding))) / ((double) this.maxValue)) * ((double) progress))) + this.thumbPadding;
    }

    public void setLeftProgress(int progress) {
        if (progress < this.thumbSliceRightValue - this.progressMinDiff) {
            this.thumbSliceLeftX = calculateCorrds(progress);
        }
        notifySeekBarValueChanged();
    }

    public void setRightProgress(int progress) {
        if (progress > this.thumbSliceLeftValue + this.progressMinDiff) {
            this.thumbSliceRightX = calculateCorrds(progress);
        }
        notifySeekBarValueChanged();
    }

    public int getSelectedThumb() {
        return this.selectedThumb;
    }

    public int getLeftProgress() {
        return this.thumbSliceLeftValue;
    }

    public int getRightProgress() {
        return this.thumbSliceRightValue;
    }

    public void setProgress(int leftProgress, int rightProgress) {
        if (rightProgress - leftProgress > this.progressMinDiff) {
            this.thumbSliceLeftX = calculateCorrds(leftProgress);
            this.thumbSliceRightX = calculateCorrds(rightProgress);
        }
        notifySeekBarValueChanged();
    }

    public void videoPlayingProgress(int progress) {
        this.isVideoStatusDisplay = true;
        this.thumbCurrentVideoPositionX = calculateCorrds(progress);
        invalidate();
    }

    public void removeVideoStatusThumb() {
        this.isVideoStatusDisplay = false;
        invalidate();
    }

    public void setSliceBlocked(boolean isBLock) {
        this.blocked = isBLock;
        invalidate();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setProgressMinDiff(int progressMinDiff) {
        this.progressMinDiff = progressMinDiff;
        this.progressMinDiffPixels = calculateCorrds(progressMinDiff);
    }

    public void setProgressHeight(int progressHeight) {
        this.progressHalfHeight /= SELECT_THUMB_RIGHT;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }

    public void setSecondaryProgressColor(int secondaryProgressColor) {
        this.secondaryProgressColor = secondaryProgressColor;
        invalidate();
    }

    public void setThumbSlice(Bitmap thumbSlice) {
        this.thumbSlice = thumbSlice;
        init();
    }

    public void setThumbCurrentVideoPosition(Bitmap thumbCurrentVideoPosition) {
        this.thumbCurrentVideoPosition = thumbCurrentVideoPosition;
        init();
    }

    public void setThumbPadding(int thumbPadding) {
        this.thumbPadding = thumbPadding;
        invalidate();
    }
}
