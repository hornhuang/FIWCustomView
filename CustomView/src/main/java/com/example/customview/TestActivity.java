package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import com.example.customview.view_3.ColorTrackTextView;

public class TestActivity extends AppCompatActivity {

    private ColorTrackTextView mColorTrackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mColorTrackTextView = findViewById(R.id.color_track_text_view);

//        stepView = findViewById(R.id.step_view);
//        stepView.setmStepMax(10000);
//
//        // 属性动画
//        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 8000);
//        valueAnimator.setDuration(2000);
//        valueAnimator.setInterpolator(new DecelerateInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float currentStep = (float) animation.getAnimatedValue();
//                stepView.setmCurrentStep((int) currentStep);
//            }
//        });
//        valueAnimator.start();
    }

    public void leftToRight(View view) {
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener((animation) -> {
            float currentProgress = (float) animation.getAnimatedValue();
            mColorTrackTextView.setCurrentProgress(currentProgress);
        });
        valueAnimator.start();
    }

    public void rightToLeft(View view) {
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener((animation) -> {
            float currentProgress = (float) animation.getAnimatedValue();
            mColorTrackTextView.setCurrentProgress(currentProgress);
        });
        valueAnimator.start();
    }

}
