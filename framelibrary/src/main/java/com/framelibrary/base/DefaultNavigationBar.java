package com.framelibrary.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.baseLibrary.navigationbar.AbsNavigationBar;
import com.framelibrary.R;

/**
 * Created by raytine on 2017/9/15.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationBarParams> {

    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationBarParams mParams) {
        super(mParams);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        setText(R.id.title,getmParams().mTitle);
        setLeft(R.id.back,getmParams().mLeftListener);
        setRightText(R.id.right_text,getmParams().mRightText,getmParams().mRightTextListener);
        setRightIcon(R.id.right_icon,getmParams().mRightIcon,getmParams().mRightIconListener);
    }




    public static class Builder extends AbsNavigationBar.Builder{
        DefaultNavigationBarParams p ;
        public Builder(Context context, ViewGroup mParent) {
            super(context, mParent);
            p = new DefaultNavigationBarParams(context,mParent);

        }
        public Builder(Context context) {
            super(context, null);
            p = new DefaultNavigationBarParams(context,null);

        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar(p);
            defaultNavigationBar.applyView();
            return defaultNavigationBar;
        }

        /**
         * 设置所有效果
         */
        public DefaultNavigationBar.Builder setTitle(String title){
            p.mTitle = title;
            return this;
        }

        /**
         * 设置右边文字
         * @param text
         * @return
         */
        public DefaultNavigationBar.Builder setRightText(String text,View.OnClickListener mListener){
            p.mRightText = text;
            p.mRightTextListener = mListener;
            return this;
        }
        /**
         * 设置右边图片
         * @param icon
         * @return
         */
        public DefaultNavigationBar.Builder setRightIcon(int icon,View.OnClickListener mListener){
            p.mRightIcon = icon;
            p.mRightIconListener = mListener;
            return this;
        }
        /**
         * 设置左边点击事件
         * @param mListener
         * @return
         */
        public DefaultNavigationBar.Builder setLeftListener(View.OnClickListener mListener){
            p.mLeftListener = mListener;
            return this;
        }


        public static class DefaultNavigationBarParams extends AbsNagationBarParams{

            public String mTitle;
            public String mRightText;
            public int mRightIcon;
            public View.OnClickListener mLeftListener;
            public View.OnClickListener mRightTextListener;
            public View.OnClickListener mRightIconListener;

            DefaultNavigationBarParams(Context context, ViewGroup mParent) {
                super(context, mParent);
            }
        }
    }
}
