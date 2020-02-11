package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private QQStepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

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

}
