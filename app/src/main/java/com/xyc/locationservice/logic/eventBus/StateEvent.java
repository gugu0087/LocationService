package com.xyc.locationservice.logic.eventBus;

/**
 * Created by hasee on 2018/3/29.
 */

public class StateEvent {
    private int state;
    private String content;

    public StateEvent(int state, String content) {
        this.state = state;
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public String getContent() {
        return content;
    }
}
