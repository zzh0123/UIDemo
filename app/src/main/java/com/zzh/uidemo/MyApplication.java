package com.zzh.uidemo;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context;

    //获取全局Context
    public static Context getContext() {
        return context;
    }
}
