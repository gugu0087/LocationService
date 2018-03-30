package com.xyc.locationservice.logic.location;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.xyc.locationservice.base.CommonParams;
import com.xyc.locationservice.logic.eventBus.LocationEvent;
import com.xyc.locationservice.logic.model.LocationModel;
import com.xyc.locationservice.utils.BaiduUtils;
import com.xyc.okutils.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


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
                    Log.d("xyc", "done: 存储成功");
                    // 存储成功
                   // Log.d(TAG, todo.getObjectId());// 保存成功之后，objectId 会自动从服务端加载到本地
                } else {
                    Log.d("xyc", "done: 存储失败");
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                }
            }
        });
    }
    public  void getLocationList(String userId){
        AVQuery<AVObject> query = new AVQuery<>(CommonParams.ADDRESS_TABLE_NAME);
        final List<LocationModel> locationList = new ArrayList<>();
        query.whereEqualTo("userId", userId);
        // 按时间，降序排列
        query.orderByDescending("createdAt");
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (int i = 0; i < list.size(); i++) {
                    LocationModel locationModel = new LocationModel();
                    locationModel.setAddress(list.get(i).getString("address"));
                    locationModel.setLastTime(DateUtils.getInstance().getLongToString(list.get(i).getCreatedAt().getTime(), DateUtils.MINUTE_FORMAT));
                    locationModel.setLatitude(list.get(i).getNumber("latitude").doubleValue());
                    locationModel.setLongtitude(list.get(i).getNumber("longtitude").doubleValue());
                    locationList.add(locationModel);
                }
                EventBus.getDefault().post(new LocationEvent(locationList));
            }
        });
    }
}
