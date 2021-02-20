package com.zzh.uidemo.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;

public class Util {

    public static Object convertMapToList(String reqResultStr, TypeToken typeToken) {
        Gson gson = getInstance();
        Type type = typeToken.getType();
        if (null == reqResultStr || "".equals(reqResultStr) || "{}".equals(reqResultStr)) {
            return null;
        } else {
            return gson.fromJson(reqResultStr, type);
        }
    }

    //饿汉式单例 节省内存
    public static Gson getInstance() {
        Gson gson = null;
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    public static String readAssert(Context context, String fileName){
        String jsonString="";
        String resultString="";
        try {
            InputStream inputStream=context.getResources().getAssets().open(fileName);
            byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString=new String(buffer,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
