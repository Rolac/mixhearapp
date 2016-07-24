package com.gccdev.mixhearapp;

/**
 * Created by rolac on 24/07/16.
 */
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

public class AnimationUtil {

    public static void animate(RecyclerView.ViewHolder holder ,boolean goesDown){


        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown ? 200 : -200, 0);
        animatorTranslateY.setDuration(1000);


        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,-20,20,-5,5,0);
        animatorTranslateX.setDuration(1000);
        animatorSet.playTogether(animatorTranslateX,animatorTranslateY);
        animatorSet.start();

    }
}
