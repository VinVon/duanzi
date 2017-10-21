package com.baseLibrary.http;

import android.graphics.Bitmap;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by raytine on 2017/9/25.
 */

public interface APIService {
    @GET("70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3138365389,851751545&fm=27&gp=0.jpg")
    Call<ResponseBody> downBitmap();

}
