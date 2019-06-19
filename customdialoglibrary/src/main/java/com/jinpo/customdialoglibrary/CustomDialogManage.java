package com.jinpo.customdialoglibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

/**
 * @author：jinpo_huang
 * @email： 1421406138@qq.com
 * @date： 2019/6/18 17:43
 * @function：
 */

public class CustomDialogManage {
    private static final String TAG = "CustomDialogManage";
    private FragmentManager fragmentManager;
    private Activity activity;
    private CustomDialogFragment lodingDialog;
    private int lodingLayoutID = R.layout.layout_loading;
    private BaseDialogFragment.DialogCancelListener dialogCancelListener;//监听弹窗关闭

    public CustomDialogManage(AppCompatActivity activity) {
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
        init();
    }

    public CustomDialogManage(Fragment fragment) {
        this.activity = fragment.getActivity();
        fragmentManager = fragment.getChildFragmentManager();
        init();
    }

    private void init() {
        lodingDialog = getLodingDialog();
        //监听弹窗关闭
        dialogCancelListener=new BaseDialogFragment.DialogCancelListener() {
            @Override
            public void onCancel(DialogFragment dialogFragment) {
                Log.i(TAG, "touch or back dissmiss dialog: " + dialogFragment);
            }
        };
    }

    //显示弹窗的方法
    public void showDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            Log.w(TAG, "dFragment is null.");
            return;
        }
        Log.i(TAG, "showDialog: " + dialogFragment);
        dialogFragment.setDialogCancelListener(dialogCancelListener);
        dialogFragment.show(fragmentManager, dialogFragment.getTag());
    }

    //关闭弹窗的方法
    public void dismissDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            Log.w(TAG, "dFragment is null.");
            return;
        }
        Log.i(TAG, "dismissDialog: " + dialogFragment);
        dialogFragment.dismiss();
    }

    //显示加载弹窗
    public BaseDialogFragment showLoadingDialog() {
        showDialog(lodingDialog);
        return lodingDialog;
    }
    //关闭加载弹窗
    public void dismissLoadingDialog() {
        dismissDialog(lodingDialog);
    }



    //显示系统弹窗
    public BaseDialogFragment showSystemDialog(CharSequence title, CharSequence message, CharSequence noText, DialogInterface.OnClickListener noListener, CharSequence yesText, DialogInterface.OnClickListener yesListener){
        CustomDialogFragment dialogFragment=CustomDialogFragment.newInstance(
                new AlertDialog.Builder(activity).setTitle(title).setMessage(message)
                        .setNegativeButton(noText,noListener)
                        .setPositiveButton(yesText,yesListener));
        showDialog(dialogFragment);
        return dialogFragment;
    }



    //
    private CustomDialogFragment getLodingDialog() {
//        int width = activity.getResources().getDisplayMetrics().widthPixels;
        CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(lodingLayoutID, new DialogContentListener() {
            @Override
            public void onContentView(ViewHolder viewHolder, DialogFragment dialogFragment) {

            }
        });
        dialogFragment.setIsCanceledOnTouchOutside(false).setTransparent(true).setDimAmount(0.3f).setWidth(ViewGroup.LayoutParams.MATCH_PARENT).setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        return dialogFragment;
    }
}
