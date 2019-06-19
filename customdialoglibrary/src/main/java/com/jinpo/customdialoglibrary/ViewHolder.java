package com.jinpo.customdialoglibrary;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author：jinpo_huang
 * @email： 1421406138@qq.com
 * @date： 2019/6/18 15:44
 * @function：
 */

public class ViewHolder {
    private View view;
    private SparseArray<View> viewArray;
    public ViewHolder(View view){
        this.view=view;
        this.viewArray=new SparseArray<>();
    }

    public static ViewHolder createView(View view){
        return new ViewHolder(view);
    }

    public static ViewHolder createView(Activity activity, int layoutId, ViewGroup container){
        View view= LayoutInflater.from(activity).inflate(layoutId,container,false);
        return createView(view);
    }

    public View getView() {
        return view;
    }

    public <T extends View> T getView(int id) {
        View view = viewArray.get(id);
        if (view == null) {
            view = this.view.findViewById(id);
            viewArray.put(id, view);
        }
        return (T) view;
    }

    public void setTextViewText(int id, CharSequence text){
        getTextView(id).setText(text);
    }

    public TextView getTextView(int id){
        return getView(id);
    }

    public ImageView getImageView(int id){
        return getView(id);
    }
}
