package com.baseLibrary.http;

import java.util.Map;

/**
 * Created by raytine on 2017/9/22.
 */

public interface IHttpEngine {
    //get请求
    void get(String url, Map<String,Object> params,EngineCallBack callBack);
    //post请求
    void post(String url, Map<String,Object> params,EngineCallBack callBack);
    //下载文件
    //上传文件
    //https添加证书

}
