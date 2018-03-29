package com.xyc.locationservice.logic.manager;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.xyc.locationservice.base.CommonParams;
import com.xyc.locationservice.logic.eventBus.LoginEvent;
import com.xyc.locationservice.logic.eventBus.RegisterEvent;
import com.xyc.locationservice.logic.eventBus.ResponseEvent;
import com.xyc.locationservice.logic.eventBus.StateEvent;
import com.xyc.locationservice.logic.model.User;
import com.xyc.okutils.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by hasee on 2018/3/29.
 */

public class LoginManager {
    private static LoginManager loginManager;

    private LoginManager() {

    }

    public static LoginManager getInstance() {
        if (loginManager == null) {
            loginManager = new LoginManager();
        }
        return loginManager;
    }

    public void login(String userName, String password) {
        AVUser.logInInBackground(userName, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                EventBus.getDefault().post(new LoginEvent(avUser));
            }
        });
    }

    public void register(User user) {
        AVUser avuser = new AVUser();// 新建 AVUser 对象实例
        avuser.setUsername(user.getUserName());// 设置用户名
        avuser.setPassword(user.getPassword());// 设置密码
        avuser.setMobilePhoneNumber(user.getPhoneNumber());
        avuser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                Log.d("xyc", "done: e="+e);
               EventBus.getDefault().post(new RegisterEvent(e));
            }
        });
    }
}
