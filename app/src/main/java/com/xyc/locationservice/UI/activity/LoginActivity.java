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

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvChangeType;
    private boolean isLogin = true;
    private EditText etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        EventBus.getDefault().register(this);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvChangeType = (TextView) findViewById(R.id.tvChangeType);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    String phoneNumber = etPhoneNumber.getText().toString();
                    String checkBackPhone = DataUtils.checkPhoneNumber(phoneNumber);
                    if (checkBackPhone != null) {
                        ToastUtil.showShort(checkBackPhone);
                        return;
                    }
                    User user = new User();
                    user.setUserName(userName);
                    user.setPassword(password);
                    user.setPhoneNumber(phoneNumber);
                    register(user);
                }
            }
        });
        tvChangeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI();
            }
        });

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

        ToastUtil.showShort(UiUtils.getValueString(R.string.login_success));

        startActivity(MainActivity.makeIntent(this));
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
            etPhoneNumber.setVisibility(View.GONE);
        } else {
            tvChangeType.setText(UiUtils.getValueString(R.string.login));
            btnLogin.setText(UiUtils.getValueString(R.string.register));
            etPhoneNumber.setVisibility(View.VISIBLE);
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
