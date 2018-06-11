package com.xiaodong.basetools.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.xiaodong.fflibrary.constants.DeviceInfo;
import com.xiaodong.basetools.R;


/**
 * Created by lenovo on 2016/9/5.
 */
public class DialogUtil {
    public static Dialog createLoadingDialog(Context context, String msg) {
        View v = LayoutInflater.from(context).inflate(R.layout.loaddialog, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        if (!TextUtils.isEmpty(msg)) {
            TextView tvMsg = (TextView) layout.findViewById(R.id.tipTextView);
            tvMsg.setText(msg);
        }


        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCanceledOnTouchOutside(false);// 空白区域不可以点击
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                DeviceInfo.WIDTHPIXELS / 3 * 2,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局


        return loadingDialog;


    }
}
