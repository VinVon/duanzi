package com.demo.duanzi;

import android.app.Application;

import com.baseLibrary.ExceptionCrashHandler;

/**
 * Created by raytine on 2017/8/26.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionCrashHandler.newInstance().init(this);
    }
}
