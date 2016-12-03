package com.example.takenagagou.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by takenagagou on 2016/12/3.
 */

public class SpUtil {
    private static SharedPreferences sp;
    //写

    /**
     * 写
     * @param ctx 上下文环境
     * @param key 存储节点
     * @param value 存储值
     */
    public static void putBoolean(Context ctx,String key,boolean value){
        if (sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();

    }


    /**
     * 读
     * @param ctx 上下文环境
     * @param key 存储节点
     * @param defValue   默认值
     * @return
     */
    public static boolean getBoolean(Context ctx,String key,boolean defValue){
        if (sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);

    }

    /**
     * 写
     * @param ctx 上下文环境
     * @param key 存储节点
     * @param value 存储值
     */
    public static void putString(Context ctx,String key,String value){
        if (sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();

    }


    /**
     * 读
     * @param ctx 上下文环境
     * @param key 存储节点
     * @param defValue   默认值
     * @return
     */
    public static String getString(Context ctx,String key,String defValue){
        if (sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);

    }

}
