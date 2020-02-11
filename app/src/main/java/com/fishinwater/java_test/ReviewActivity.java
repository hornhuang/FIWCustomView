package com.fishinwater.java_test;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.TestActivity;


/**
 * 主活动
 * 显示复习内容
 * @author fishinwater-1999
 */
public class ReviewActivity extends AppCompatActivity {

    private FIWStepView fiwStepView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        startActivity(new Intent(this, TestActivity.class));
    }

}
