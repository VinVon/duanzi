package com.baseLibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by raytine on 2017/8/30.
 */

class AlertController {
    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mDialogViewHelper;
    public AlertController(AlertDialog alertDialog, Window window) {
        this.mDialog = alertDialog;
        this.mWindow = window;
    }

    /**
     * 获取dialog
     * @return
     */
    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }


    public void setContentView(View contentView) {
        mDialogViewHelper.setContentView(contentView);
    }

    /**
     * 设置文本
     * @param i
     * @param charSequence
     */
    public void setText(int i, CharSequence charSequence) {
        mDialogViewHelper.setText(i,charSequence);
    }

    public <T extends View>T getView(int i) {
        return mDialogViewHelper.getView(i);
    }


    public void setClick(int i, View.OnClickListener onClickListener) {
        mDialogViewHelper.setClick(i,onClickListener);
    }

    public void setmDialogViewHelper(DialogViewHelper mDialogViewHelper) {
        this.mDialogViewHelper = mDialogViewHelper;
    }

    public static class AlertParams {
        public Context mContext;
        public int mThemeResId;
        //点击空白是否取消
        public  boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public int mViewLayoutResId;
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mAninations = 0;


        public AlertParams(Context mContext, int mThemeResId) {
            this.mContext = mContext;
            this.mThemeResId = mThemeResId;
        }

        public void apply(AlertController mAlert) {

            //设置布局
            DialogViewHelper dialogViewHelper = null;
            if (mViewLayoutResId != 0){
                dialogViewHelper  = new DialogViewHelper(mContext,mViewLayoutResId);
            }
            if (mView !=null){
                dialogViewHelper  = new DialogViewHelper();
                dialogViewHelper.setContentView(mView);
            }
            if (dialogViewHelper == null){
                throw new IllegalArgumentException("请设置setContentView");
            }
            View contentView = dialogViewHelper.getContentView();
            mAlert.getDialog().setContentView(contentView);
            mAlert.setmDialogViewHelper(dialogViewHelper);
            //设置文本
            int size = mTextArray.size();
            for (int i = 0; i < size; i++) {
                mAlert.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }
            //设置点击
            int clickSize = mClickArray.size();
            for (int i = 0; i < clickSize; i++) {
                mAlert.setClick(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }
            //设置自定义属性
            Window window = mAlert.getWindow();
            window.setGravity(mGravity);
            window.setWindowAnimations(mAninations);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = mWidth;
            attributes.height = mHeight;
            window.setAttributes(attributes);
        }
    }
}
