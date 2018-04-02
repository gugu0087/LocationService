package com.xyc.locationservice.base;



import android.Manifest;

import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.SDKInitializer;
import com.xyc.gupermission.GuPermissionManager;
import com.xyc.okutils.MyApplication;


/**
 * Created by hasee on 2017/12/20.
 */

public class MyLeanCloudApp extends MyApplication {
    private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        AVOSCloud.initialize(this, CommonParams.APP_ID,CommonParams.APP_KEY);
        AVOSCloud.setDebugLogEnabled(true);
        GuPermissionManager.getInstance().addPermissions(permissions);
    }

}
