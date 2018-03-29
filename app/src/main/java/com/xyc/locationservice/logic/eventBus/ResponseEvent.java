package com.xyc.locationservice.logic.eventBus;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by hasee on 2018/3/29.
 */

public class ResponseEvent {
    private List<AVObject> avObjectList;
    private int type;

    public ResponseEvent(List<AVObject> avObjectList, int type) {
        this.avObjectList = avObjectList;
        this.type = type;
    }

    public List<AVObject> getAvObjectList() {
        return avObjectList;
    }

    public int getType() {
        return type;
    }
}
