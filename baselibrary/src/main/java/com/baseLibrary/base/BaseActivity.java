package com.baseLibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by raytine on 2017/8/15.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initTitle();
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化头部
     */
    protected abstract void initTitle();

    /**
     * 初始化布局
     */
    protected abstract void setContentView();

    /**
     * 页面跳转方法
     * @param clasz
     */
    protected void startActivity(Class<?> clasz){
        Intent intent = new Intent(this,clasz);
        startActivity(intent);
    }
}
