package com.xyc.locationservice.UI.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xyc.guguviews.views.BaseActivity;
import com.xyc.locationservice.R;
import com.xyc.locationservice.base.BaseAppActivity;
import com.xyc.locationservice.logic.location.LocationManager;
import com.xyc.locationservice.utils.UiUtils;

public class LocationActivity extends BaseAppActivity implements OnGetGeoCoderResultListener {

    private MapView mapView;
    private TextView tvAddressTip;
    private BaiduMap mBaiduMap;
    private GeoCoder mSearch;
    private BitmapDescriptor bitmapDescriptor;
    private Marker marker;

    @Override
    protected void initHeader() {
        setHeaderTitle(UiUtils.getValueString(R.string.act_location_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCenterView(R.layout.activity_location);
        initView();
        initData();
    }

    public static Intent makeIntent(Context context, double latitude, double longtitude, String address) {
        Intent intent = new Intent(context, LocationActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longtitude", longtitude);
        intent.putExtra("address", address);
        return intent;
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longtitude = intent.getDoubleExtra("longtitude", 0);
        String address = intent.getStringExtra("address");
        tvAddressTip.setText("位置：" + address);
        mBaiduMap = mapView.getMap();
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        LatLng latLng = new LatLng(latitude, longtitude);
        //准备 marker 的图片
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.lacation);
        //准备 marker option 添加 marker 使用
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(latLng);
        //获取添加的 marker 这样便于后续的操作
        marker = (Marker) mBaiduMap.addOverlay(markerOptions);
        showLocation(latLng);
    }

    private void showLocation(LatLng latLng) {
        marker.setPosition(latLng);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

        MapStatusUpdate mapState = LocationManager.getInstance().getMapState(latLng.latitude, latLng.longitude);
        //改变地图状态
        mBaiduMap.setMapStatus(mapState);

    }

    private void initView() {
        mapView = (MapView) findViewById(R.id.mapView);
        tvAddressTip = (TextView) findViewById(R.id.tvAddressTip);

    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null) {
            return;
        }

        LatLng latLng = geoCodeResult.getLocation();
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(status);
        //准备 marker option 添加 marker 使用
        // MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(latLng);
        //获取添加的 marker 这样便于后续的操作
        //marker = (Marker) mBaiduMap.addOverlay(markerOptions);
        marker.setPosition(latLng);

        MapStatusUpdate mapState = LocationManager.getInstance().getMapState(latLng.latitude, latLng.longitude);
        mBaiduMap.setMapStatus(mapState);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }
}
