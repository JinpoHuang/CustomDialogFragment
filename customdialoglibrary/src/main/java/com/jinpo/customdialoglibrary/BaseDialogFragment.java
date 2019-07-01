package com.jinpo.customdialoglibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author：jinpo_huang
 * @email： 1421406138@qq.com
 * @date： 2019/6/18 17:39
 * @function：
 */

public abstract class BaseDialogFragment extends DialogFragment implements DialogContentListener {
    private static final String TAG = "BaseDialogFragment";
    //DialogFragment常用属性
    private Boolean isCancelable = true;//能否通过空白或返回键关闭弹窗
    private Boolean isCanceledOnTouchOutside = true;//能否通过点击空白关闭弹窗
    private Boolean isTransparent = false;//是否设置弹窗背景透明
    private float dimAmount = 0.5f;//蒙版透明度(取值0-1)
    private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.CENTER;

    //监听弹窗关闭
    private DialogCancelListener dialogCancelListener;

    //系统弹窗
    protected abstract AlertDialog.Builder getDialogBuilder();

    //用户自定义弹窗布局
    protected abstract int getLayoutID();

    //系统默认弹窗
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getDialogBuilder() != null) {
            return getDialogBuilder().create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    //用户自定义布局弹窗
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认标题
        if (getDialogBuilder() == null && getLayoutID() != View.NO_ID) {
            ViewHolder viewHolder = ViewHolder.createView(getActivity(), getLayoutID(), container);
            onContentView(viewHolder, this);
            return viewHolder.getView();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            super.onStart();
            initDialog();
        } catch (Exception e) {
            Log.w(TAG, "onStart: ", e);
        }
    }

    //初始化弹窗属性（必须在onCreateView中执行或者之后，否则会有空指针）
    private void initDialog() {
        setCancelable(isCancelable);
        getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        Window window = getDialog().getWindow();
        if (window != null) {
            if (isTransparent) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = dimAmount;
            layoutParams.width = width;
            layoutParams.height = height;
            layoutParams.gravity = gravity;
            window.setAttributes(layoutParams);
        }
    }

    //方便链式设置弹窗属性
    public BaseDialogFragment setIsCancelable(Boolean cancelable) {
        isCancelable = cancelable;
        if (getDialog() != null) {
            setCancelable(isCancelable);
        }
        return this;
    }

    public BaseDialogFragment setIsCanceledOnTouchOutside(Boolean canceledOnTouchOutside) {
        isCanceledOnTouchOutside = canceledOnTouchOutside;
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        }
        return this;
    }

    public BaseDialogFragment setTransparent(Boolean transparent) {
        isTransparent = transparent;
        return this;
    }

    public BaseDialogFragment setDimAmount(float dimAmount) {
        if (dimAmount < 0 || dimAmount > 1) {
            Log.w(TAG, "dimAmount ranges from 0 to 1");
            return this;
        }
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public void setDialogCancelListener(DialogCancelListener dialogCancelListener) {
        this.dialogCancelListener = dialogCancelListener;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            Log.w(TAG, "show:" + e);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (dialogCancelListener != null) {
            dialogCancelListener.onCancel(this);
        }
    }

    public interface DialogCancelListener {
        void onCancel(DialogFragment dialogFragment);
    }
}
