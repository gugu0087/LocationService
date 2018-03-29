package com.xyc.locationservice.logic.manager;

import com.avos.avoscloud.AVObject;
import com.xyc.locationservice.logic.location.LocationManager;
import com.xyc.locationservice.logic.model.User;

/**
 * Created by hasee on 2018/3/29.
 */

public class LogicManager {

    private static LogicManager logicManager;

    private LogicManager() {

    }

    public static LogicManager getInstance() {
        if (logicManager == null) {
            logicManager =  new LogicManager();
        }
        return logicManager;
    }

    public void login(String userName,String password) {
         LoginManager.getInstance().login(userName,password);
    }

    public void register(User user) {
        LoginManager.getInstance().register(user);
    }

    public void saveLocation(AVObject data) {
        LocationManager.getInstance().saveLocation(data);
    }
}
