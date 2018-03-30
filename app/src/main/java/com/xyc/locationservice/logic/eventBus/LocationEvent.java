package com.xyc.locationservice.logic.eventBus;

import com.xyc.locationservice.logic.model.LocationModel;

import java.util.List;

/**
 * Created by hasee on 2018/3/30.
 */

public class LocationEvent {
    private List<LocationModel> locationModel;

    public LocationEvent(List<LocationModel> locationModel) {
        this.locationModel = locationModel;
    }

    public List<LocationModel> getLocationModel() {
        return locationModel;
    }
}
