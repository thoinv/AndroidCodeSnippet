package com.videotomp3.audio.videoconverter.util;

import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


/**
 * Created by ThoiNguyen on 7/13/2017.
 */

public class VCAnimationUtils {
    public static void setItemAnimation(Context context, final ViewGroup viewGroup, int animationResourceId){
        viewGroup.setHasTransientState(true);
        Animation animation = AnimationUtils.loadAnimation(context, animationResourceId);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewGroup.setHasTransientState(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewGroup.startAnimation(animation);
    }
}
