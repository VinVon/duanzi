package com.baseLibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by raytine on 2017/8/30.
 */

public class DialogViewHelper {
    private View mContentView = null;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context mContext, int mThemeResId) {
        this();
        mContentView = LayoutInflater.from(mContext).inflate(mThemeResId,null);

    }

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    /**
     * 设置文本
     * @param i
     * @param charSequence
     */
    public void setText(int i, CharSequence charSequence) {
        /**
         * 减少findviewbyid的次数
         */
        TextView tv = getView(i);
        if (tv!=null){
        tv.setText(charSequence);
    }
    }

    public <T extends View>T getView(int i) {
        WeakReference<View> viewWeakReference = mViews.get(i);
        View v = null;
        if (viewWeakReference != null){
                v = viewWeakReference.get();
        }
        if (v == null){
            v =  mContentView.findViewById(i);
            if (v != null){
            mViews.put(i,new WeakReference<View>(v));
        }
        }
        return (T) v;
    }


    public void setClick(int i, View.OnClickListener onClickListener) {
        View viewById = getView(i);
        if (viewById !=null){
            viewById.setOnClickListener(onClickListener);
        }

    }

    public View getContentView() {
        return mContentView;
    }
}
