package com.example.takenagagou.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by takenagagou on 2016/12/2.
 */

public class ToastUtil {
    /**
     * toast
     * @param ctx  上下文环境
     * @param msg   信息
     */
    public static void show(Context ctx, String msg){
        Toast.makeText(ctx,msg, 0).show();
    }
}
