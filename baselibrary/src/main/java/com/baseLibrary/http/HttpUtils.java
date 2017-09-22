package com.baseLibrary.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xws on 2017/9/22.
 */

public class HttpUtils implements IHttpEngine {
    //参数链式调用
    private String mUrl;
    private int m_Type = GET_TYPE;
    private static final int POST_TYPE = 0X0011;
    private static final int GET_TYPE = 0X0011;
    private Map<String,Object> mParams;
    private Context mContext;

    public HttpUtils(Context mContext) {
        this.mContext = mContext;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context mContext) {
        return new HttpUtils(mContext);
    }
    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }
    public HttpUtils post() {
        m_Type = POST_TYPE;
        return this;
    }

    public HttpUtils get() {
        m_Type = GET_TYPE;
        return this;
    }

    /**
     * 添加参数
     * @param key
     * @param value
     * @return
     */
    public HttpUtils addParams(String key,Object value) {
        mParams.put(key,value);
        return this;
    }
    public HttpUtils addParams(Map<String,Object> mParams) {
        mParams.putAll(mParams);
        return this;
    }

    /**
     * 添加回调 执行
     * @return
     */
    public void execute(EngineCallBack callBack) {
        if (callBack == null){
            callBack = EngineCallBack.DEFAULT_CALL_BACK;
        }
        if (m_Type == POST_TYPE){
            post(mUrl,mParams,callBack);
        }
        if (m_Type == GET_TYPE){
            get(mUrl,mParams,callBack);
        }
    }
    public void execute() {
        execute(null);
    }

    //默认引擎OkHttpEngine
    private static IHttpEngine mIHttpEngine = new OkHttpEngine();

    /**
     * 初始化
     *
     * @param httpEngine
     */
    public static void init(IHttpEngine httpEngine) {
        mIHttpEngine = httpEngine;
    }

    /**
     * 切换网络引擎
     *
     * @param httpEngine
     */
    public void exchangeEngine(IHttpEngine httpEngine) {
        mIHttpEngine = httpEngine;
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    @Override
    public void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.get(url, params, callBack);

    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    @Override
    public void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.post(url, params, callBack);
    }
}
