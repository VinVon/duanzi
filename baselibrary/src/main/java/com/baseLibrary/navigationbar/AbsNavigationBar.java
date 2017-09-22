package com.baseLibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baseLibrary.R;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractCollection;

/**
 * Created by raytine on 2017/9/15.
 * 布局头部的基类
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNagationBarParams> implements INavigationBar {
    private P mParams;
    private View navigationView;
    public AbsNavigationBar(P mParams) {
        this.mParams = mParams;
        createAndBindView();
    }

    public P getmParams() {
        return mParams;
    }

    /**
     * 设置中间title
     * @param title
     * @param mTitle
     */
    public void setText(int title, String mTitle) {
        TextView textView = findViewByid(title);
        if (!TextUtils.isEmpty(mTitle)){
            textView.setVisibility(View.VISIBLE);
            textView.setText(mTitle);
        }
    }

    /**
     * 设置回退，默认关闭该页面
     * @param left
     */
    public void setLeft(int title,View.OnClickListener left) {
        ImageView viewByid = findViewByid(title);
        if (left == null){
            viewByid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)mParams.mContext).finish();
                }
            });
        }else {
            viewByid.setOnClickListener(left);
        }

    }
    public void setRightIcon(int right_icon, int mRightIcon,View.OnClickListener left) {
        ImageView viewByid = findViewByid(right_icon);
        viewByid.setVisibility(View.VISIBLE);
        viewByid.setBackgroundResource(mRightIcon);
        if (left != null){
            viewByid.setOnClickListener(left);
        }
    }

    public void setRightText(int right_text, String mRightText,View.OnClickListener left) {
        TextView textView = findViewByid(right_text);
        if (!TextUtils.isEmpty(mRightText)){
            textView.setVisibility(View.VISIBLE);
            textView.setText(mRightText);
            if (left != null){
                textView.setOnClickListener(left);
            }
        }

    }
    /**
     * findViewByid
     * @param id
     * @param <T>
     * @return
     */
    private <T extends View> T findViewByid(int id){
        return (T)navigationView.findViewById(id);
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
        if (mParams.mParent == null){
            /**
             * 获取decorView-->获取screen_simple布局(LinearLayout)-->不用考虑activity的布局添加头部
             */
            ViewGroup activityRoot = (ViewGroup) ((Activity)mParams.mContext).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }
        //1.创建View
        navigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);
        //2.添加
        mParams.mParent.addView(navigationView,0);

        applyView();
    }

    public abstract static class Builder{
        public Builder(Context context, ViewGroup mParent){
        }

        public abstract AbsNavigationBar builder();




        public static class AbsNagationBarParams{
            public Context mContext;
            public ViewGroup mParent;
           public AbsNagationBarParams(Context context, ViewGroup mParent){
                    this.mContext = context;
                    this.mParent = mParent;
            }
        }
    }
}
