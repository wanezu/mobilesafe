package com.example.takenagagou.mobilesafe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takenagagou.mobilesafe.activity.HomeActivity;
import com.example.takenagagou.mobilesafe.utils.StreamUtil;
import com.example.takenagagou.mobilesafe.utils.ToastUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    protected static final String tag = "SplashActivity";
    private TextView tv_version_name;
    private int mLocalVersionCode;
    //更新新版本
    private static final int UPDATE_VERSION = 100;
    private static final int ENTER_HONE = 101;
    private static final int ERROR = 102;

    private String mVersionDes;
    private String mDownloadUrl;
    private RelativeLayout tv_root;
    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;

                case ENTER_HONE:
                    //进入应用程序主界面
                    enterHome();
                    break;

                case ERROR:
                    ToastUtil.show(getApplicationContext(),"访问异常");
                    enterHome();
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 弹出对话框
     */
    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //下载apk
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //取消对话框
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                enterHome();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去头
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        //初始化控件
        initUI();

        //初始化数据
        initData();

        //初始化动画
        initAnimation();
    }

    /**
     * 初始化动画
     */
    private void initAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        tv_root.startAnimation(alphaAnimation);
    }

    /**
     * 下载apk
     */
    protected void downloadApk(){
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            //获取sd的路径
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mobilesafe74.apk";
            //发送请求，获取apk，放置指定路径
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Log.i(tag,"下载成功");
                    File file = responseInfo.result;
                    //提示用户安装
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i(tag,"下载失败");
                }

                //开始下载
                @Override
                public void onStart(){
                    Log.i(tag,"刚刚开始下载");
                    super.onStart();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i(tag,"下载中.....");
                    Log.i(tag,"total = ....." + total);
                    Log.i(tag,"current = ....." + current);
                    super.onLoading(total,current,isUploading);
                }
            });
        } else {
            ToastUtil.show(getApplicationContext(),"没有SD卡");
        }
    }

    /**
     * 安装对应的apk
     * @param file
     */
    protected void installApk(File file){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.action.DEFAULT");
//        intent.setData(Uri.fromFile(file));
//        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//        startActivity(intent);
        startActivityForResult(intent,0);
    }

    /**
     * 开启新的activity，然后返回上一个activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跳转到首页
     */
    protected void enterHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        tv_root = (RelativeLayout) findViewById(R.id.tv_root);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        //1、应用的版本名称
        tv_version_name.setText(getVersionName());
        //检测（本地和服务端版本号比对）是否更新，下载
        mLocalVersionCode = getVersionCode();
        //获取服务器的版本号（发请求）
        checkVersion();
    }

    /**
     * 检测版本
     */
    private void checkVersion() {
        //开启线程
        new Thread() {
            public void run() {
//                Message message = new Message();
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                //发送请求json的链接地址
                try {
                    //封装url地址
                    URL url = new URL("http://www.cecotw.com/android/version.json");
                    //开启链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置常见参数
                    connection.setConnectTimeout(2000);//请求超时
                    connection.setReadTimeout(2000);//读取超时
                    //默认以get请求
//                    connection.setRequestMethod();
                    //获取响应码
                    if (connection.getResponseCode() == 200) {
                        //以流的方式将数据获取下来
                        InputStream is = connection.getInputStream();
                        //将流转换成字符串（工具类封装）
                        String json = StreamUtil.streamToString(is);
                        Log.i(tag, json);
                        //解析json
                        JSONObject jsonObject = new JSONObject(json);
                        String versionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        Log.i(tag,versionName);
                        Log.i(tag,mVersionDes);
                        Log.i(tag,versionCode);
                        Log.i(tag,mDownloadUrl);

                        //比对版本号
                        if (mLocalVersionCode < Integer.parseInt(versionCode)){
                            //提示用户更新
                            msg.what = UPDATE_VERSION;
                        } else {
                            //进入用户主界面
                            msg.what = ENTER_HONE;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = ERROR;
                }finally {
                    //指定睡眠时间,请求网络超过4秒不作处理
                    long endtime = System.currentTimeMillis();
                    if (endtime - startTime < 4000){
                        try {
                            Thread.sleep(4000 - (endtime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
    }

    /**
     * 非零代表成功
     *
     * @return
     */
    private int getVersionCode() {
        //1、包管理者对象packageManager
        PackageManager pm = getPackageManager();
        //2、从包管理者对象中，获取指定包名的基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本的名称：清单文件
     * 应用版本名称  null表示异常
     */
    private String getVersionName() {
        //1、包管理者对象packageManager
        PackageManager pm = getPackageManager();
        //2、从包管理者对象中，获取指定包名的基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
