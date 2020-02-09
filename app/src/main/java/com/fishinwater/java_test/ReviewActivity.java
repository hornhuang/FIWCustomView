package com.fishinwater.java_test;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.customview.FIWTextView;
import com.example.customview.TestActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * 主活动
 * 显示复习内容
 *
 * @author fishinwater-1999
 */
public class ReviewActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private int CAMERA_REQUEST_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //使用兼容库就无需判断系统版本
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.BODY_SENSORS)) {
            EasyPermissions.requestPermissions(this, "{Manifest.permission.BODY_SENSORS", CAMERA_REQUEST_CODE, Manifest.permission.BODY_SENSORS);
        }

        startActivity(new Intent(this, TestActivity.class));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "succeed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "lose", Toast.LENGTH_SHORT).show();
//        Dialog dialog = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
//                .setTitle(R.string.tips)
//                .setMessage(R.string.camera_peemission_tip)
//                .setPositiveButton(R.string.to_open, (dialog2, which) -> {
//                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
//                })
//                .setNegativeButton(R.string.cancel, (dialog3, which) -> {
//                    dialog3.dismiss();
//                })
//                .create();
//        dialog.show();
    }

}
