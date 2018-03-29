package com.xyc.locationservice.UI.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xyc.locationservice.R;
import com.xyc.locationservice.base.CommonParams;
import com.xyc.okutils.utils.PreferencesUtils;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initData();
    }

    private void initData() {
        boolean isLogin = PreferencesUtils.getBoolean(CommonParams.ISLOGINIT, false);
        if (isLogin) {
            startActivity(MainActivity.makeIntent(this));
        } else {
            startActivity(LoginActivity.makeIntent(this));
        }
        finish();
    }
}
