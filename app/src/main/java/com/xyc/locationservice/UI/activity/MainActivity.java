package com.xyc.locationservice.UI.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.xyc.guguviews.views.BaseActivity;
import com.xyc.guguviews.views.SlideItem;
import com.xyc.guguviews.views.SlideMenuLayout;
import com.xyc.locationservice.R;
import com.xyc.locationservice.base.CommonParams;
import com.xyc.locationservice.logic.service.LocationService;
import com.xyc.locationservice.utils.UiUtils;
import com.xyc.okutils.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private SlideMenuLayout slideMenu;

    @Override
    protected void initHeader() {
        setRightImgVisibility(View.VISIBLE);
        setLeftIconVisibility(View.GONE);
        setHeaderTitle("");
        setRightImage(R.mipmap.more);
        setHeader_rightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideMenu.openDrawerPage();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCenterView(R.layout.activity_main);
        initView();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private void initView() {
        slideMenu = (SlideMenuLayout) findViewById(R.id.slideMenu);
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_content_layout, null);
        slideMenu.setContentView(contentView);
        initContentView(contentView);
        initSlideMenu();
        startService();
    }

    private void initSlideMenu() {
        final List<SlideItem> slideItems = new ArrayList<>();
        SlideItem slideItem = new SlideItem();
        slideItem.setTitle(UiUtils.getValueString(R.string.slide_menu_item_login_out));
        slideItem.setItemId(CommonParams.ITEM_ID_OUT);
        slideItems.add(slideItem);
        slideMenu.addListData(slideItems);
        slideMenu.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                slideMenu.closeDrawerPage();
                switch (slideItems.get(position).getItemId()) {
                    case CommonParams.ITEM_ID_OUT:
                        startActivity(LoginActivity.makeIntent(MainActivity.this));
                        PreferencesUtils.putBoolean(CommonParams.ISLOGINIT, false);
                        finish();
                        break;
                }
            }
        });
    }

    private void initContentView(View contentView) {

    }

    private void startService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }
}
