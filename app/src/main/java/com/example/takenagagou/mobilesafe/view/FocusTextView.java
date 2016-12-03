package com.example.takenagagou.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 获取焦点的TextView
 * Created by takenagagou on 2016/12/3.
 */

public class FocusTextView extends TextView {
    //通过java代码
    public FocusTextView(Context context) {
        super(context);
    }

    //由系统调用（带属性，上下文）
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //当前空间指定相应的样式主题
    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //重写获取焦点的方法
    @Override
    public boolean isFocused(){
        return true;
    }
}
