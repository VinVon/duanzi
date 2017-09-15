package com.demo.duanzi;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.baseLibrary.ExceptionCrashHandler;
import com.demo.duanzi.utils.AppVersionUtils;
import com.example.BindView;
import com.framelibrary.base.BaseSkinActivity;

/**
 * Created by raytine on 2017/8/26.
 */
public class BaseApplication extends Application {
    public static PatchManager patchManager;
    @Override
    public void onCreate() {
        super.onCreate();




        ExceptionCrashHandler.newInstance().init(this);
//        patchManager = new PatchManager(this);
//        String appVersionName = AppVersionUtils.getAppVersionName(this);
//        patchManager.init(appVersionName);//current version
//        patchManager.loadPatch();

    }
}
