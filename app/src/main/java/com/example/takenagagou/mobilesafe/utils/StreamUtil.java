package com.example.takenagagou.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by takenagagou on 2016/12/1.
 */
public class StreamUtil {

    /**
     *
     * @param is 流对象
     * @return  流转换成字符串   返回null代表的异常
     */
    public static String streamToString(InputStream is) {
        //1.在读取的过程中，将读取的内容储存在缓存中
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //2.读流的操作,读到没有（循环）
        byte[] buffer = new byte[1024];
        //3.记录读取内容的临时变量
        int tmp = -1;
        try {
            while ((tmp = is.read(buffer)) != -1){
                bos.write(buffer,0,tmp);
            }
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
          try {
              is.close();
              bos.close();
          } catch (IOException e){
              e.printStackTrace();
          }
        }
        return null;
    }
}
