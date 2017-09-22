package com.baseLibrary.http;

/**
 * Created by raytine on 2017/9/22.
 */

public interface EngineCallBack {
    public void onError(Exception e);
    public void onSuccess(String result);

    public  final  EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
