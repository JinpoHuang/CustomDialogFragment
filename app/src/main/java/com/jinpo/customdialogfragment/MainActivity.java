package com.jinpo.customdialogfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.jinpo.customdialoglibrary.CustomDialogFragment;
import com.jinpo.customdialoglibrary.CustomDialogManage;
import com.jinpo.customdialoglibrary.DialogContentListener;
import com.jinpo.customdialoglibrary.ViewHolder;

public class MainActivity extends AppCompatActivity {
    CustomDialogManage dialogManage;
    CustomDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogManage = new CustomDialogManage(this);
    }

    public void showDialog(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                dialogManage.showSystemDialog("温馨提示", "系统消息", "取消", null, "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case R.id.tv2:
                dialogManage.showLoadingDialog();
                break;

            case R.id.tv3:
                dialogManage.setLodingLayoutID(R.layout.layout_loding);
                dialogManage.showLoadingDialog();
                break;

            case R.id.tv4:
                dialogFragment = CustomDialogFragment.newInstance(R.layout.layout_dialog, new DialogContentListener() {
                    @Override
                    public void onContentView(ViewHolder viewHolder, DialogFragment dialogFragment) {
                        viewHolder.setTextViewText(R.id.tv_dialog, "这是一个自定义布局弹窗");
                    }
                });
                dialogFragment.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogFragment.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                dialogManage.showDialog(dialogFragment);
                break;

            case R.id.tv5:

                break;


            default:
                break;
        }

    }

}
