package com.xyc.locationservice.logic.eventBus;

import com.avos.avoscloud.AVException;

/**
 * Created by hasee on 2018/3/29.
 */

public class RegisterEvent {
    private AVException avException;

    public RegisterEvent(AVException avException) {
        this.avException = avException;
    }

    public AVException getAvException() {
        return avException;
    }
}
