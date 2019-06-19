package com.jinpo.customdialoglibrary;

import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * @author：jinpo_huang
 * @email： 1421406138@qq.com
 * @date： 2019/6/18 17:42
 * @function：
 */

public class CustomDialogFragment extends BaseDialogFragment {
    private AlertDialog.Builder builder;//系统默认弹窗
    private int layoutID = View.NO_ID;//自定义弹窗布局
    private DialogContentListener contentListener;//自定义弹窗处理事件接口

    //创建系统默认弹窗
    public static CustomDialogFragment newInstance(AlertDialog.Builder builder) {
        CustomDialogFragment customDialogFragment=new CustomDialogFragment();
        customDialogFragment.builder=builder;
        return customDialogFragment;
    }

    //创建自定义弹窗
    public static CustomDialogFragment newInstance(int layoutID,DialogContentListener contentListener) {
        CustomDialogFragment customDialogFragment=new CustomDialogFragment();
        customDialogFragment.layoutID=layoutID;
        customDialogFragment.contentListener =contentListener;
        return customDialogFragment;
    }


    @Override
    protected int getLayoutID() {
        return layoutID;
    }

    @Override
    protected AlertDialog.Builder getDialogBuilder() {
        return builder;
    }


    @Override
    public void onContentView(ViewHolder viewHolder, DialogFragment dialogFragment) {
        if (contentListener != null) {
            contentListener.onContentView(viewHolder, dialogFragment);
        }
    }
}
