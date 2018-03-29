package com.xyc.locationservice.UI.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.xyc.guguviews.views.BaseActivity;
import com.xyc.guguviews.views.PullListView;
import com.xyc.locationservice.R;
import com.xyc.locationservice.UI.adapter.LocationAdapter;
import com.xyc.locationservice.base.CommonParams;
import com.xyc.locationservice.logic.model.LocationModel;
import com.xyc.locationservice.utils.UiUtils;
import com.xyc.okutils.utils.DateUtils;
import com.xyc.okutils.utils.PreferencesUtils;
import com.xyc.okutils.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ReceiveActivity extends BaseActivity {

    private PullListView pullListView;
    private List<LocationModel> locationList = new ArrayList<>();
    private LocationAdapter adapter;

    @Override
    protected void initHeader() {
        setHeaderTitle("定位数据");
        setLeftIconVisibility(View.GONE);
        setRightIconVisibility(View.VISIBLE);
        setHeader_RightText("退出");
        setHeader_RightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.makeIntent(ReceiveActivity.this));
                PreferencesUtils.putBoolean(CommonParams.ISLOGINIT, false);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCenterView(R.layout.activity_receive);
        initView();
        initData();
    }

    private void initData() {
        adapter = new LocationAdapter(this, locationList);
        pullListView.setAdapter(adapter);
        loadData();

    }

    private void loadData() {
        AVQuery<AVObject> query = new AVQuery<>(CommonParams.ADDRESS_TABLE_NAME);
        String userId = PreferencesUtils.getString(CommonParams.OBJECT_ID);
        if (userId.isEmpty()) {
            ToastUtil.showShort(UiUtils.getValueString(R.string.login_invalid));
            return;
        }
        query.whereEqualTo("userId", userId);
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                pullListView.refreshComplete();
                pullListView.getMoreComplete();
                Log.d("xyc", "done: list=" + list);
                locationList.clear();
                for (int i = 0; i < list.size(); i++) {
                    LocationModel locationModel = new LocationModel();
                    locationModel.setAddress(list.get(i).getString("address"));
                    locationModel.setLastTime(DateUtils.getInstance().getLongToString(list.get(i).getCreatedAt().getTime(), DateUtils.MINUTE_FORMAT));
                    locationModel.setLatitude(list.get(i).getLong("latitude"));
                    locationModel.setLatitude(list.get(i).getLong("longtitude"));
                    locationList.add(locationModel);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        pullListView = (PullListView) findViewById(R.id.pullListView);
        pullListView.setOnGetMoreListener(new PullListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }, CommonParams.REFRESH_TIME);
            }
        });
        pullListView.setOnRefreshListener(new PullListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }, CommonParams.REFRESH_TIME);

            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ReceiveActivity.class);
    }

}
