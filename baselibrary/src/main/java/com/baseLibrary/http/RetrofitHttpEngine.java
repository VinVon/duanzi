package com.baseLibrary.http;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by raytine on 2017/9/25.
 */

public class RetrofitHttpEngine implements IHttpEngine {
    private Retrofit  retrofit = new Retrofit.Builder().baseUrl("https://ss0.bdstatic.com/")
            .build();
    private Handler mHandler = new Handler();
    @Override
    public void get(String url, Map<String, Object> params,final EngineCallBack callBack) {
        Log.e("TAG->Retrofit","en");
        Call<ResponseBody> responseBodyCall = retrofit.create(APIService.class).downBitmap();
        Log.e("TAG->Retrofit","en1");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("TAG->Retrofit",result);
                final String finalResult = result;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(finalResult);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void post(String url, Map<String, Object> params, EngineCallBack callBack) {

    }
}
