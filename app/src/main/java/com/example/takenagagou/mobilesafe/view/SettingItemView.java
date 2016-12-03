package com.example.takenagagou.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.takenagagou.mobilesafe.R;

/**
 * Created by takenagagou on 2016/12/3.
 */

public class SettingItemView extends RelativeLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.example.takenagagou.mobilesafe";
    private CheckBox cb_box;
    private TextView tv_des;
    private TextView tv_title;
    private String mDestitle;
    private String mDesoff;
    private String mDeson;
    private static final String tag = "SettingItemView";
    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //xml——>转换view,直接添加到对应的view
        View.inflate(context, R.layout.setting_item_view,this);
        //标题描述
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_des = (TextView) this.findViewById(R.id.tv_des);
        cb_box = (CheckBox) this.findViewById(R.id.cb_box);

        //获取自定义属性的操作
        initAttrs(attrs);
        tv_title.setText(mDestitle);
    }

    /**
     * 返回属性集合中的数
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs){
        mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
        mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
        mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
    }

    /**
     * 判断是否开启
     * 返回 当前settingItemView的选中状态，true选中，false关闭
     * @return
     */
    public boolean isCheck(){
        return cb_box.isChecked();
    }

    /**
     *
     * @param isCheck 是否作为开启，有点击过程控制
     */
    public void setCheck(boolean isCheck){
        //当前条目在选择的过程中，跟随状态变化
        cb_box.setChecked(isCheck);
        if (isCheck){
            //开启
            tv_des.setText(mDeson);
        } else {
            tv_des.setText(mDesoff);
        }
    }
}
