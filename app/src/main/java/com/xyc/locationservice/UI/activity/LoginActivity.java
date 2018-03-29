package com.xyc.locationservice.UI.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.xyc.guguviews.views.BaseActivity;
import com.xyc.locationservice.R;
import com.xyc.locationservice.base.CommonParams;
import com.xyc.locationservice.logic.eventBus.LoginEvent;
import com.xyc.locationservice.logic.eventBus.RegisterEvent;
import com.xyc.locationservice.logic.eventBus.ResponseEvent;
import com.xyc.locationservice.logic.manager.LogicManager;
import com.xyc.locationservice.logic.manager.LoginManager;
import com.xyc.locationservice.logic.model.User;
import com.xyc.locationservice.utils.DataUtils;
import com.xyc.locationservice.utils.UiUtils;
import com.xyc.okutils.utils.PreferencesUtils;
import com.xyc.okutils.utils.ProgressUtils;
import com.xyc.okutils.utils.ToastUtil;
import com.xyc.okutils.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class LoginActivity extends BaseActivity {

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvChangeType;
    private boolean isLogin = true;

    private TextView tvChangeVerType;
    private boolean isReceiveClient = false;

    @Override
    protected void initHeader() {
        setHeaderTitle(UiUtils.getValueString(R.string.login));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCenterView(R.layout.activity_login);
        initView();
        EventBus.getDefault().register(this);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvChangeType = (TextView) findViewById(R.id.tvChangeType);
        tvChangeVerType = (TextView) findViewById(R.id.tvChangeVerType);
        isReceiveClient = PreferencesUtils.getBoolean(CommonParams.CLIENT_TYPE, false);
        updateChangeVerUI();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isReceiveClient) {
                    login();
                } else {
                    login();
                }
            }
        });
        tvChangeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI();
            }
        });
        tvChangeVerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isReceiveClient = !isReceiveClient;
                updateChangeVerUI();
            }
        });

    }

    private void updateChangeVerUI() {

        if (isReceiveClient) {
            tvChangeType.setVisibility(View.GONE);
            tvChangeVerType.setText(UiUtils.getValueString(R.string.change_ver_monitor));
            btnLogin.setText(UiUtils.getValueString(R.string.link));
            setHeaderTitle(UiUtils.getValueString(R.string.link));
        } else {
            tvChangeType.setVisibility(View.VISIBLE);
            btnLogin.setText(UiUtils.getValueString(R.string.login));
            setHeaderTitle(UiUtils.getValueString(R.string.login));
            tvChangeVerType.setText(UiUtils.getValueString(R.string.change_ver_receive));
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        ProgressUtils.getIntance().dismissProgress();
        AVUser avUser = event.getmUser();
        if (avUser == null) {
            ToastUtil.showShort(UiUtils.getValueString(R.string.name_psw_no_exist));
            return;
        }
        String objectId = avUser.getObjectId();
        if (objectId != null) {
            PreferencesUtils.putString(CommonParams.OBJECT_ID, objectId);
        }
        PreferencesUtils.putBoolean(CommonParams.ISLOGINIT, true);
        PreferencesUtils.putBoolean(CommonParams.CLIENT_TYPE,isReceiveClient);
        if (isReceiveClient) {
            ToastUtil.showShort(UiUtils.getValueString(R.string.link_success));
            startActivity(ReceiveActivity.makeIntent(this));
        } else {
            ToastUtil.showShort(UiUtils.getValueString(R.string.login_success));
            startActivity(MainActivity.makeIntent(this));
        }
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterEvent(RegisterEvent event) {
        ProgressUtils.getIntance().dismissProgress();
        AVException avException = event.getAvException();
        if (avException != null) {
            ToastUtil.showShort(UiUtils.getValueString(R.string.register_failed));
            return;
        }
        ToastUtil.showShort(UiUtils.getValueString(R.string.register_success));
        updateUI();
    }

    private void updateUI() {
        isLogin = !isLogin;
        if (isLogin) {
            tvChangeType.setText(UiUtils.getValueString(R.string.register));
            btnLogin.setText(UiUtils.getValueString(R.string.login));

        } else {
            tvChangeType.setText(UiUtils.getValueString(R.string.login));
            btnLogin.setText(UiUtils.getValueString(R.string.register));

        }
    }

    private void login() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String checkBackName = DataUtils.checkUserName(userName);
        String checkBackPsw = DataUtils.checkUserName(password);

        if (checkBackName != null) {
            ToastUtil.showShort(checkBackName);
            return;
        }
        if (checkBackPsw != null) {
            ToastUtil.showShort(checkBackPsw);
            return;
        }
        if (isLogin) {
            login(userName, password);
        } else {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            register(user);
        }
    }

    private void login(String userName, String password) {
        ProgressUtils.getIntance().setProgressDialog(UiUtils.getValueString(R.string.login_ing_tip), this);
        LogicManager.getInstance().login(userName, password);
    }

    private void register(User user) {
        ProgressUtils.getIntance().setProgressDialog(UiUtils.getValueString(R.string.register_ing_tip), this);
        LogicManager.getInstance().register(user);
    }
}
