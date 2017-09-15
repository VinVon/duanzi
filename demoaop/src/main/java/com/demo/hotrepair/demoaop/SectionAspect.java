package com.demo.hotrepair.demoaop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by raytine on 2017/8/28.
 */
@Aspect
public class SectionAspect {
    @Pointcut("execution(@com.demo.hotrepair.demoaop.CheckNet * *(..))")
    public void checkNetBehavior() {

    }
    @Around("checkNetBehavior()")
    public Object CheckNet(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Log.e("TAG","checkNet");
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        CheckNet annotation = signature.getMethod().getAnnotation(CheckNet.class);
        if (annotation != null){
            Object aThis = proceedingJoinPoint.getThis();
            Context context = getContext(aThis);
            if (context != null){
                if (!isNetworkAvailable(context)){
                    // 3.没有网络不要往下执行
                    Toast.makeText(context,"请检查您的网络", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

    private Context getContext(Object aThis) {
        if (aThis instanceof Activity){
            return (Activity) aThis;
        }else if(aThis instanceof Fragment){
            return ((Fragment) aThis).getActivity();
        }else if(aThis instanceof View){
            return ((View) aThis).getContext();
        }
        return null;
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
