package com.baseLibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by raytine on 2017/8/14.
 */

public class ViewUtils {
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    /**
     * @param viewFinder
     * @param object
     */
    private static void inject(ViewFinder viewFinder, Object object) {
        //注入属性
        injectFiled(viewFinder, object);
        //注入事件
        injectEvent(viewFinder, object);
    }

    //注入事件
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        Class<?> aClass = object.getClass();
        //获取所有方法
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods
                ) {
            OnClick annotation = method.getAnnotation(OnClick.class);
            CheckNet annotations = method.getAnnotation(CheckNet.class);
            if (annotation != null) {
                int[] value = annotation.value();
                for (int i : value) {
                    View viewById = viewFinder.findViewById(i);
                    boolean isCheckNet = false;

                    if (viewById != null) {
                        if (annotations != null){
                            int[] value1 = annotations.value();
                            for (int i1 : value1) {
                                if (i1 == i){
                                    isCheckNet = true;
                                }
                            }
                        }
                        viewById.setOnClickListener(new DeclaredOnClickListener(method, object,isCheckNet));
                    }
                }
            }
        }
    }

    /**
     * 注入属性
     *
     * @param viewFinder
     * @param object     反射需要的事件
     */
    private static void injectFiled(ViewFinder viewFinder, Object object) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ViewById annotation = field.getAnnotation(ViewById.class);
            if (annotation != null) {
                int viewId = annotation.value();
                if (viewId != -1) {
                    View viewById = viewFinder.findViewById(viewId);
                    field.setAccessible(true);
                    try {
                        field.set(object, viewById);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Method mMethod;
        private Object mObject;
        private boolean mIsNet;
        public DeclaredOnClickListener(Method method, Object object,boolean isnet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsNet = isnet;
        }

        @Override
        public void onClick(View view) {
            if (mIsNet){
                int netWorkType = getNetWorkType(view.getContext());
                if (netWorkType == NETWORKTYPE_INVALID){
                    Toast.makeText(view.getContext(),"没有网络",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            try {
                mMethod.setAccessible(true);
                mMethod.invoke(mObject, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 没有网络
     */
    public static final int NETWORKTYPE_INVALID = 0;
    /**
     * wap网络
     */
    public static final int NETWORKTYPE_WAP = 1;
    /**
     * 2G网络
     */
    public static final int NETWORKTYPE_2G = 2;
    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    public static final int NETWORKTYPE_3G = 3;
    /**
     * wifi网络
     */
    public static final int NETWORKTYPE_WIFI = 4;

    public static int mNetWorkType = NETWORKTYPE_INVALID;

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G}, *
     * {@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}*
     * <p>
     * {@link #NETWORKTYPE_WIFI}
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                int subtype = networkInfo.getSubtype();
                if (subtype == TelephonyManager.NETWORK_TYPE_GPRS)
                    mNetWorkType = NETWORKTYPE_WAP;
                else if (subtype == TelephonyManager.NETWORK_TYPE_EDGE
                        || subtype == TelephonyManager.NETWORK_TYPE_CDMA)
                    mNetWorkType = NETWORKTYPE_2G;
                else
                    mNetWorkType = NETWORKTYPE_3G;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        return mNetWorkType;
    }
}
