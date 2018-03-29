package com.xyc.locationservice.logic.location;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

/**
 * Created by hasee on 2018/1/13.
 */

public class MyLocationHelper extends BDAbstractLocationListener {
     private ILocationSuccessListener mListener;
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if(mListener !=null){
            if(bdLocation==null){
                mListener.locationFailed("定位失败");
            }else {
                mListener.locationSuccess(bdLocation);
            }
        }
    }

    public MyLocationHelper(ILocationSuccessListener listener){
        mListener = listener;
    }
}
