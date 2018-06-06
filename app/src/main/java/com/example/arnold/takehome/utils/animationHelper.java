package com.example.arnold.takehome.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.example.arnold.takehome.R;

public class animationHelper {
    public static void animateLayer(float fromAlpha, final float toAlpha, View viewToAnimate, int duration) {
        viewToAnimate.setAlpha(fromAlpha);
        if (fromAlpha == 0) {
            viewToAnimate.setVisibility(View.VISIBLE);
        }

        ValueAnimator alphaAnimator = new ValueAnimator();
        alphaAnimator.setFloatValues(fromAlpha, toAlpha);
        alphaAnimator.setDuration(duration);
        alphaAnimator.addUpdateListener(animation -> viewToAnimate.setAlpha((Float) animation.getAnimatedValue()));
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (toAlpha == 0) {
                    viewToAnimate.setVisibility(View.GONE);
                }
            }
        });
        alphaAnimator.start();
    }

    public static void moveObjectIntoView(float fromY, float toY, View viewToAnimate, int duration) {
        TranslateAnimation moveUp = new TranslateAnimation(0, 0, fromY, toY);
        moveUp.setDuration(duration);
        moveUp.setFillAfter(true);

        viewToAnimate.startAnimation(moveUp);
    }
}
