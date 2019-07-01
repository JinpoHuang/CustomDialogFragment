package com.jinpo.customdialoglibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author：jinpo_huang
 * @email： 1421406138@qq.com
 * @date： 2019/6/18 17:43
 * @function：
 */

public class CustomDialogManage {
    private static final String TAG = "CustomDialogManage";
    int widthPixels,heightPixels;
    private FragmentManager fragmentManager;
    private Activity activity;
    private CustomDialogFragment lodingDialog;
    private int lodingLayoutID = R.layout.layout_loading;
    private BaseDialogFragment.DialogCancelListener dialogCancelListener;//监听弹窗关闭

    private CopyOnWriteArrayList<BaseDialogFragment> dialogFragmentList;//弹窗队列，管理已开启弹窗

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
        widthPixels=activity.getResources().getDisplayMetrics().widthPixels;
        heightPixels=activity.getResources().getDisplayMetrics().heightPixels;
        dialogFragmentList = new CopyOnWriteArrayList<>();
        lodingDialog = getLodingDialog();
        //监听弹窗关闭
        dialogCancelListener = new BaseDialogFragment.DialogCancelListener() {
            @Override
            public void onCancel(DialogFragment dialogFragment) {
                Log.i(TAG, "touch or back dissmiss dialog: " + dialogFragment);
                if (dialogFragmentList.size() > 0) {
                    dialogFragmentList.remove(dialogFragment);//移除弹窗队列
                }
            }
        };
    }

    //显示弹窗的方法
    private void show(BaseDialogFragment dialogFragment) {
        dialogFragmentList.add(dialogFragment);//添加到弹窗队列
        Log.i(TAG, "showDialog: " + dialogFragment);
        dialogFragment.setDialogCancelListener(dialogCancelListener);
        dialogFragment.show(fragmentManager, dialogFragment.getTag());
    }

    //去重，同一个弹窗只能显示一次
    private void removalDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragmentList.contains(dialogFragment)) {
            dismissDialog(dialogFragment);
        }
    }

    //显示弹窗的方法（去重）
    public void showDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            Log.w(TAG, "dFragment is null.");
            return;
        }
        removalDialog(dialogFragment);
        show(dialogFragment);
    }

    //关闭弹窗的方法
    public void dismissDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragmentList.size() > 0) {
            dialogFragmentList.remove(dialogFragment);//移除弹窗队列
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

    //关闭所有弹窗
    public void dismissAllDialog(){
        for (BaseDialogFragment dialogFragment : dialogFragmentList) {
            dismissDialog(dialogFragment);
        }
        dialogFragmentList.clear();
    }

    //显示系统弹窗
    public BaseDialogFragment showSystemDialog(CharSequence title, CharSequence message, CharSequence noText, DialogInterface.OnClickListener noListener, CharSequence yesText, DialogInterface.OnClickListener yesListener) {
        CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(
                new AlertDialog.Builder(activity).setTitle(title).setMessage(message)
                        .setNegativeButton(noText, noListener)
                        .setPositiveButton(yesText, yesListener));
        showDialog(dialogFragment);
        return dialogFragment;
    }

    //显示自定义布局的系统弹窗
    public BaseDialogFragment showSystemDialog(int layoutID) {
        View view=activity.getLayoutInflater().inflate(layoutID,null);
        CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(
                new AlertDialog.Builder(activity).setView(view).setPositiveButton("",null).setNegativeButton("",null));
        showDialog(dialogFragment);
        return dialogFragment;
    }



    //获取加载弹窗
    private CustomDialogFragment getLodingDialog() {
        CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(lodingLayoutID, new DialogContentListener() {
            @Override
            public void onContentView(ViewHolder viewHolder, DialogFragment dialogFragment) {

            }
        });
        dialogFragment.setIsCanceledOnTouchOutside(false).setTransparent(true).setDimAmount(0.3f)
                .setWidth(widthPixels).setHeight(heightPixels);
        return dialogFragment;
    }

    public void setLodingLayoutID(int lodingLayoutID) {
        this.lodingLayoutID = lodingLayoutID;
        lodingDialog = getLodingDialog();
    }
}
