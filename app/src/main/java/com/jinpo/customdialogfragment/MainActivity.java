package com.jinpo.customdialogfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jinpo.customdialoglibrary.CustomDialogManage;

public class MainActivity extends AppCompatActivity {
    CustomDialogManage dialogManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogManage=new CustomDialogManage(this);
        dialogManage.showLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogManage.dismissLoadingDialog();
            }
        },2000);
    }
}
