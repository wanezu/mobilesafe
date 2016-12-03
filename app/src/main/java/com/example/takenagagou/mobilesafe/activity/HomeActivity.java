package com.example.takenagagou.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.takenagagou.mobilesafe.R;
import com.example.takenagagou.mobilesafe.utils.ConstantValue;
import com.example.takenagagou.mobilesafe.utils.Md5Util;
import com.example.takenagagou.mobilesafe.utils.SpUtil;
import com.example.takenagagou.mobilesafe.utils.ToastUtil;

/**
 * Created by takenagagou on 2016/12/2.
 */
public class HomeActivity extends Activity{
    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrawableIds;
    private View view;
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
                        showDialog();
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

    /**
     * 设置密码对话框
     */
    protected void showDialog(){
        //判断本地是否有密码
        String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD,"");


        if (TextUtils.isEmpty(psd)){
            //初始设置密码对话框
            showSetPsdDialog();
        } else {
            //确认密码对话框
            showConfirmPsdDialog();
        }


    }

    /**
     * 设置密码对话框
     */
    private void showSetPsdDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        view = View.inflate(this,R.layout.dialog_set_psd,null);
        dialog.setView(view);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
                EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);
                String psd = et_set_psd.getText().toString();
                String confirmPsd = et_confirm_psd.getText().toString();
                if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)){
                    //进入手机防盗模块
                    if (psd.equals(confirmPsd)){
                        Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                        startActivity(intent);
                        //跳转到新的界面，隐藏对话框
                        dialog.dismiss();
                        String md5psd = Md5Util.main(psd);
                        SpUtil.putString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD,md5psd);
                    } else {
                        ToastUtil.show(getApplicationContext(),"确认密码错误！");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(),"请输入密码！");
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        view = View.inflate(this,R.layout.dialog_confirm_psd,null);
        dialog.setView(view);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);
                String confirmPsd = et_confirm_psd.getText().toString();
                if (!TextUtils.isEmpty(confirmPsd)){
                    //进入手机防盗模块
                    String psd = SpUtil.getString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD,"");
                    String md5psd = Md5Util.main(psd);
                    if (confirmPsd.equals(md5psd)){
                        Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                        startActivity(intent);
                        //跳转到新的界面，隐藏对话框
                        dialog.dismiss();
                    } else {
                        ToastUtil.show(getApplicationContext(),"确认密码错误！");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(),"请输入密码！");
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
