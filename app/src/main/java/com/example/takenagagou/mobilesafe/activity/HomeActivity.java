package com.example.takenagagou.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.takenagagou.mobilesafe.R;

/**
 * Created by takenagagou on 2016/12/2.
 */
public class HomeActivity extends Activity{
    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrawableIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

        //初始化数据
        initData();
    }

    private void initUI(){
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    private void initData(){
        //准备数据
        mTitleStr = new String[]{
                "手机防盗","通讯卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"
        };

        mDrawableIds = new int[]{
                R.drawable.home_safe,
                R.drawable.home_callmsgsafe,
                R.drawable.home_apps,
                R.drawable.home_taskmanager,
                R.drawable.home_netmanager,
                R.drawable.home_trojan,
                R.drawable.home_sysoptimize,
                R.drawable.home_tools,
                R.drawable.home_settings
        };
        //九宫格控件设置数据适配器（等同于ListView数据适配器）
        gv_home.setAdapter(new MyAdapter());
        //注册九宫格
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            //条数总数
            return mTitleStr.length;
        }

        @Override
        public Object getItem(int i) {
            return mTitleStr[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = View.inflate(getApplicationContext(),R.layout.gridview_item,null);
            TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
            ImageView iv_icon = (ImageView) view1.findViewById(R.id.iv_icon);
            tv_title.setText(mTitleStr[i]);
            iv_icon.setBackgroundResource(mDrawableIds[i]);
            return view1;
        }
    }

}
