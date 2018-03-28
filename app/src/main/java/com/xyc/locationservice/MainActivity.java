package com.xyc.locationservice;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.baidu.location.BDLocation;
import com.xyc.locationservice.location.ILocationSuccessListener;
import com.xyc.locationservice.location.LocationManager;
import com.xyc.locationservice.service.LocationService;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.btnStart);
        tvContent = (TextView) findViewById(R.id.tvContent);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 测试 SDK 是否正常工作的代码
                AVObject testObject = new AVObject("TestObject");
                testObject.put("words","Hello World!");
                testObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null){
                            Log.d("xyc","success!");
                        }
                    }
                });
                //startService();
            }
        });
    }

     private void startService(){
         Intent intent = new Intent(this,LocationService.class);
         startService(intent);
     }
}
