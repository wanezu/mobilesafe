package com.example.takenagagou.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by takenagagou on 2016/12/3.
 */
public class TestActivity extends Activity{
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        TextView textView = new TextView(this);
        textView.setText("测试");
        setContentView(textView);
    }
}
