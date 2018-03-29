package com.xyc.locationservice.logic.eventBus;

import com.avos.avoscloud.AVUser;

/**
 * Created by hasee on 2018/3/29.
 */

public class LoginEvent {
    private AVUser mUser;

    public LoginEvent(AVUser mUser) {
        this.mUser = mUser;
    }

    public AVUser getmUser() {
        return mUser;
    }
}
