package com.xyc.locationservice.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.xyc.locationservice.location.ILocationSuccessListener;
import com.xyc.locationservice.location.LocationManager;
import com.xyc.okutils.base.ApplicationHolder;
import com.xyc.okutils.manager.ThreadPoolManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by hasee on 2018/3/28.
 */

public class LocationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startLocatiton() {
        Context appContext = ApplicationHolder.getAppContext();
        LocationManager.getInstance().getLocationClient(appContext, new ILocationSuccessListener() {
            @Override
            public void locationSuccess(BDLocation bdLocation) {
                String addrStr = bdLocation.getAddrStr();
                Log.d("xyc", "locationSuccess: addrStr=" + addrStr);

            }

            @Override
            public void locationFailed(String msg) {

            }
        }).start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
         ThreadPoolManager.getInstance().getSingleScheduledThreadPool(5).scheduleAtFixedRate(new Runnable() {
             @Override
             public void run() {
                 startLocatiton();
             }
         },0,1, TimeUnit.MINUTES);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent localIntent = new Intent();
        localIntent.setClass(this, LocationService.class); // 销毁时重新启动Service
        this.startService(localIntent);

    }
}
