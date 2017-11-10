package androlite.androidfragmentsearchview;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by ahmad on 26/09/17.
 */

public class Splash_activity extends AppCompatActivity

{
    public static final long DEFAULT_ANIMATION_DURATION = 2500L;
    public static final long DEFAULT_ANIMATION_DURATION_END = 2600L;
    protected View mLogo;
    protected View mFrameLayout;
    protected float mScreenHeight;
//    private final int SPLASH_DISPLAY_LENGTH = DEFAULT_ANIMATION_DURATION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        mLogo = findViewById(R.id.logo);

        AnimatorSet logoAnimatorSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.bounce_animation);
        // 4
        logoAnimatorSet.setTarget(mLogo);

        // 5
        AnimatorSet bothAnimatorSet = new AnimatorSet();
        bothAnimatorSet.playTogether(logoAnimatorSet);
        // 6
        bothAnimatorSet.setDuration(DEFAULT_ANIMATION_DURATION);
        bothAnimatorSet.start();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash_activity.this,MainActivity.class);
                Splash_activity.this.startActivity(mainIntent);
                Splash_activity.this.finish();
            }
        }, DEFAULT_ANIMATION_DURATION_END);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
    }

}

