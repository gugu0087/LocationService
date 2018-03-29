package com.xyc.locationservice.utils;

import com.xyc.locationservice.R;
import com.xyc.okutils.utils.UIUtils;

import java.security.interfaces.RSAKey;

/**
 * Created by hasee on 2018/3/29.
 */

public class DataUtils {
    public static String checkUserName(String userName) {
        if (userName.isEmpty()) {
            return UiUtils.getValueString(R.string.name_psw_no_empty);
        }
        if (userName.length() < 6 || userName.length() > 20) {
            return UiUtils.getValueString(R.string.user_name_length);
        }
        return null;
    }

    public static String checkPassword(String psw) {
        if (psw.isEmpty()) {
            return UiUtils.getValueString(R.string.name_psw_no_empty);
        }
        if (psw.length() < 6 || psw.length() > 20) {
            return UiUtils.getValueString(R.string.user_name_length);
        }
        return null;
    }

    public static String checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            return UiUtils.getValueString(R.string.register_phone);
        }
        if (phoneNumber.length() != 11) {
            return UiUtils.getValueString(R.string.register_phone_length);
        }
        return null;
    }
}
