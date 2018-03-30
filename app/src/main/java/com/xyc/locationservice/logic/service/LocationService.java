package com.xyc.locationservice.logic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.baidu.location.BDLocation;
import com.xyc.locationservice.base.CommonParams;
import com.xyc.locationservice.logic.location.ILocationSuccessListener;
import com.xyc.locationservice.logic.location.LocationManager;
import com.xyc.locationservice.logic.manager.LogicManager;
import com.xyc.okutils.base.ApplicationHolder;
import com.xyc.okutils.manager.ThreadPoolManager;
import com.xyc.okutils.utils.PreferencesUtils;

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
                saveLocationData(bdLocation);

            }

            @Override
            public void locationFailed(String msg) {

            }
        }).start();
    }

    private void saveLocationData(BDLocation bdLocation) {

        String addrStr = bdLocation.getAddrStr();
        Log.d("xyc", "saveLocationData: addrStr="+addrStr);
        double latitude = bdLocation.getLatitude();
        double longitude = bdLocation.getLongitude();
        if (latitude == 0 || longitude == 0) {
            return;
        }
        AVObject object = new AVObject(CommonParams.ADDRESS_TABLE_NAME);
        String userId = PreferencesUtils.getString(CommonParams.OBJECT_ID);
        Log.d("xyc", "saveLocationData: userId="+userId);
        if (userId == null) {
            return;
        }
        object.put("userId", userId);
        object.put("latitude", latitude);
        object.put("longtitude", longitude);
        object.put("address", addrStr);
        LogicManager.getInstance().saveLocation(object);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int rate = PreferencesUtils.getInt(CommonParams.RATE_MINUTE, CommonParams.RATE_MINUTE_60);
        Log.d("xyc", "onCreate: rate="+rate);
        ThreadPoolManager.getInstance().getSingleScheduledThreadPool(2).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                startLocatiton();
            }
        }, 0, rate, TimeUnit.MINUTES);

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
