package com.xyc.locationservice.logic.location;

import com.baidu.location.BDLocation;

/**
 * Created by hasee on 2018/1/13.
 */

public interface ILocationSuccessListener {
    void  locationSuccess(BDLocation bdLocation);
    void  locationFailed(String msg);
}
