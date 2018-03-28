package com.xyc.locationservice.base;



import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.SDKInitializer;
import com.xyc.okutils.MyApplication;


/**
 * Created by hasee on 2017/12/20.
 */

public class MyLeanCloudApp extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        AVOSCloud.initialize(this, CommonParams.APP_ID,CommonParams.APP_KEY);
        AVOSCloud.setDebugLogEnabled(true);
    }

}
