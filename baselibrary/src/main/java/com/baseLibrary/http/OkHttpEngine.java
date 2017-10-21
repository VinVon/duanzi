package com.baseLibrary.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 默认引擎
 * Created by raytine on 2017/9/22.
 */

public class OkHttpEngine implements IHttpEngine {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Handler mHandler = new Handler();

    @Override
    public void get(String url, Map<String, Object> params, final EngineCallBack callBack) {
        Request request = new Request.Builder().url("http://test.med-vision.cn/api/v1/appControlDoctor/login?username=13972132002&password=easy888")
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(result);
                    }
                });
            }
        });
    }

    @Override
    public void post(String url, Map<String, Object> params, final EngineCallBack callBack) {

        RequestBody body = RequestBody.create(JSON, new Gson().toJson(params));
        Request request = new Request.Builder().url("http://test.med-vision.cn/api/v1/appControlDoctor/login")
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(result);
                    }
                });
            }
        });
    }
}
