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

    public void runAnimation() {
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setInterpolator(new DecelerateInterpolator(1.0f));
        alphaAnimation.setDuration(100);
        alphaAnimation.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);

        alphaAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.6f, 1, 0.0f);
        alphaAnimation.setInterpolator(new DecelerateInterpolator(1.0f));
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(alphaAnimation);

        setVisibility(View.VISIBLE);
        startAnimation(animationSet);
    }
}
