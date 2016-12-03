package com.example.takenagagou.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.takenagagou.mobilesafe.R;
import com.example.takenagagou.mobilesafe.utils.ConstantValue;
import com.example.takenagagou.mobilesafe.utils.SpUtil;
import com.example.takenagagou.mobilesafe.view.SettingItemView;

/**
 * Created by takenagagou on 2016/12/3.
 */

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
    }

    private void initUpdate(){
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE,false);
        siv_update.setCheck(open_update);

        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = siv_update.isCheck();
                siv_update.setCheck(!check);
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!check);
            }
        });
    }
}