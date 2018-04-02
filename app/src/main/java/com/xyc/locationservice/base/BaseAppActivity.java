package com.xyc.locationservice.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xyc.guguviews.views.BaseActivity;
import com.xyc.gupermission.GuPermissionManager;

/**
 * Created by hasee on 2018/4/2.
 */

public class BaseAppActivity extends BaseActivity {
    private static final int REQUEST_PERMISSION_CODE = 100;

    @Override
    protected void initHeader() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
    }

    private void checkPermissions() {
        GuPermissionManager.getInstance().requestPermissions(REQUEST_PERMISSION_CODE, this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                GuPermissionManager.getInstance().dealWithRefusePermission(this, grantResults, permissions);
                break;
        }
    }
}
