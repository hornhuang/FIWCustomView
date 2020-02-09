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

public class TestActivity extends AppCompatActivity implements SensorEventListener {

    private QQStepView stepView;

    public final String TAG = getClass().getName();
    private int mCurrentStep = 8000;


    private SensorManager manager;
    private Sensor stepSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        stepView = findViewById(R.id.step_view);
        stepView.setmStepMax(10000);

        // 属性动画
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> list = manager.getSensorList(Sensor.TYPE_ALL);
        if (list != null && list.size() != 0) {
            initData();
            manager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "have", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Toast.makeText(this, "walk", Toast.LENGTH_SHORT).show();
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            //event.values[0]为计步历史累加值
            Log.e(TAG, "onSensorChanged: 当前步数：" + event.values[0]);
            mCurrentStep = (int) (event.values[0]);
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, mCurrentStep);
            valueAnimator.setDuration(2000);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float currentStep = (float) animation.getAnimatedValue();
                    stepView.setmCurrentStep((int) currentStep);
                }
            });
            valueAnimator.start();
        }
    }

    private void initData() {
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
