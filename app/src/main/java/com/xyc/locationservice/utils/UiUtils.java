package com.xyc.locationservice.utils;

import com.xyc.okutils.base.ApplicationHolder;

/**
 * Created by hasee on 2018/3/29.
 */

public class UiUtils {
    /**
     * 代码里不直接写中文，通过这个方法返回string里面的资源文字
     * @param resId
     * @return
     */
    public static String getValueString(int resId){
        return ApplicationHolder.getAppContext().getResources().getString(resId);
    }
}
