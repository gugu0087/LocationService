package com.xyc.locationservice.logic.location;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.xyc.locationservice.utils.BaiduUtils;


/**
 * Created by hasee on 2018/1/13.
 */

public class LocationManager {
    public static LocationManager instance = null;

    private LocationClient mLocationClient;

    private LocationManager() {

    }

    public static LocationManager getInstance() {
        if (instance == null) {
            instance = new LocationManager();
        }
        return instance;
    }

    public LocationClient getLocationClient(Context context, ILocationSuccessListener listener) {
        mLocationClient = new LocationClient(context);
        //声明LocationClient类
        mLocationClient.registerLocationListener(new MyLocationHelper(listener));
        //注册监听函数
        BaiduUtils.initLocationOptions(mLocationClient);

        return mLocationClient;
    }

    public MapStatusUpdate getMapState(double latitude, double longitude) {
        LatLng p = new LatLng(latitude, longitude);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(p)
                .zoom(17.0f)
                .build();

        return MapStatusUpdateFactory.newMapStatus(mMapStatus);
    }

    public void stopLocationClient() {
        if (mLocationClient == null) {
            return;
        }
        mLocationClient.stop();
    }

    public void saveLocation(AVObject data){
        data.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 存储成功
                   // Log.d(TAG, todo.getObjectId());// 保存成功之后，objectId 会自动从服务端加载到本地
                } else {
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                }
            }
        });
    }
}
