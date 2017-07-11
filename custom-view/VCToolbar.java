package com.videotomp3.audio.videoconverter.views.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.videotomp3.audio.videoconverter.R;

/**
 * Created by ThoiNguyen on 7/4/2017.
 */

public class VCToolbar extends RelativeLayout {

    private ImageButton btnBack;
    private TextView txtTitle;
    private View rootView;

    public VCToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public VCToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public VCToolbar(Context context) {
        super(context);
        this.init(context);
    }

    private void init(Context context) {
        this.rootView = inflate(context, R.layout.toolbar, null);

        this.btnBack = this.rootView.findViewById(R.id.btn_back);

        this.txtTitle = this.rootView.findViewById(R.id.txt_title);

        this.addView(this.rootView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public VCToolbar setTitle(String text){
        if(this.txtTitle != null){
            this.txtTitle.setText(text);
        }
        return this;
    }

    public VCToolbar setTitleColor(String color){
        this.setTitleColor(Color.parseColor(color));
        return this;
    }

    public VCToolbar setTitleColor(int color){
        if(this.txtTitle != null){
            this.txtTitle.setTextColor(color);
        }
        return this;
    }

    public VCToolbar setToolbarBackground(String color){
        this.setToolbarBackground(Color.parseColor(color));
        return this;
    }

    public VCToolbar setToolbarBackground(int color){
        if(this.rootView != null){
            this.rootView.setBackgroundColor(color);
        }
        return this;
    }

    public VCToolbar setBackButtonColor(String color){
        this.setBackButtonColor(Color.parseColor(color));
        return this;
    }

    public VCToolbar setBackButtonColor(int color){
        if(this.btnBack != null){
            this.btnBack.setColorFilter(color);
        }
        return this;
    }

    public VCToolbar setBtnBackClickListener(OnClickListener btnBackClickListener) {
        if(btnBackClickListener != null){
            this.btnBack.setOnClickListener(btnBackClickListener);
        }
        return this;
    }
}
