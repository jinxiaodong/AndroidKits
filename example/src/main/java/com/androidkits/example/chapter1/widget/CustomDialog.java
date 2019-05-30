package com.androidkits.example.chapter1.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkits.example.R;


/**
 * Created by xiaodong.jin on 2018/10/10.
 * 功能描述：
 */
public class CustomDialog extends Dialog {
    public String screenheight;
    public String screenwidth;
    View mView;
    public TextView titleMessage;
    public TextView contentMessage;
    public TextView tv_dialog_cancel;
    public TextView tv_dialog_confirm;
    private TextView tv_confirm_bottom;
    private LinearLayout ll_operate;
    private View view_vertical_linr;

    public void setLeftTxt(String leftTxt) {
        if (!TextUtils.isEmpty(leftTxt)) {
            tv_dialog_cancel.setText(leftTxt);
        }
    }

    public void setRightTxt(String rightTxt) {
        if (!TextUtils.isEmpty(rightTxt)) {
            tv_dialog_confirm.setText(rightTxt);
        }
    }

    /**
     * 初始化dialog数据
     */
    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        screenheight = d.getHeight() + "";
        screenwidth = d.getWidth() + "";
        WindowManager.LayoutParams p = getWindow().getAttributes();
        double rote = d.getWidth() / 480.0;
        p.width = (int) (480 * rote);
        dialogWindow.setAttributes(p);
    }

    public CustomDialog(Context context, int res) {
        super(context);
        init();
        setContentView(res);
    }

    public CustomDialog(Context context, View res) {
        super(context);
//        init();
        setContentView(res);
    }

    public CustomDialog(Context context, String titleName) {
        super(context);
        init();
        initView(titleName, null, null, null);
    }

    public CustomDialog(Context context, String titleName, String leftText, String rightText) {
        super(context);
        init();
        initView(titleName, null, leftText, rightText);
    }

    public CustomDialog(Context context, String titleName, String content, String leftText, String rightText) {
        super(context);
        init();
        initView(titleName, content, leftText, rightText);
    }

    private void initView(String titleName, String content, String leftTxt, String rightTxt) {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_layout, null);
        setContentView(mView);//默认布局
        titleMessage = mView.findViewById(R.id.tv_dialog_messagetitle);
        contentMessage = mView.findViewById(R.id.tv_dialog_contenttitle);
        tv_dialog_confirm = mView.findViewById(R.id.tv_dialog_confirm);
        tv_dialog_cancel = mView.findViewById(R.id.tv_dialog_cancel);

        titleMessage.setText(titleName);
        if (TextUtils.isEmpty(content)) {
            contentMessage.setVisibility(View.GONE);
        } else {
            contentMessage.setVisibility(View.VISIBLE);
            contentMessage.setText(content);
        }
        if (!TextUtils.isEmpty(rightTxt)) {
            tv_dialog_confirm.setText(rightTxt);
        }
        if (!TextUtils.isEmpty(leftTxt)) {
            tv_dialog_cancel.setText(leftTxt);
        }
        tv_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    callBack.confirm(false);
                }
                dismiss();
            }
        });
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.confirm(true);
                }
                dismiss();
            }

        });
    }

    CustomDialogCallBack callBack;

    public void SetCustomDialogCallBack(CustomDialogCallBack callBack) {
        this.callBack = callBack;
    }


    public interface CustomDialogCallBack {
        void confirm(boolean positiveButton);
    }

    public void setType(int type) {
        if (type == 400) {
            tv_dialog_cancel.setText("再想想");
            tv_dialog_confirm.setText("去实名认证");
        }

        if (type == 100) {
            tv_dialog_cancel.setText("知道了");
            tv_dialog_confirm.setText("如何开店");
        }
        if (type == 200) {
            setCanceledOnTouchOutside(false);
            tv_dialog_cancel.setText("我再想想");
            tv_dialog_confirm.setText("原价购买");
        }

        if (type == 3) {
            setCanceledOnTouchOutside(false);
            ll_operate.setVisibility(View.GONE);
            tv_confirm_bottom.setVisibility(View.VISIBLE);

        }
        if (type == 4) {
            setCanceledOnTouchOutside(false);
            tv_dialog_cancel.setText("继续逛逛");
            tv_dialog_confirm.setText("不取消");

        }
        if (type == 5) {
            setCanceledOnTouchOutside(false);
            tv_dialog_cancel.setText("拒绝");
            tv_dialog_confirm.setText("好");
        }
        if (type == 6) {
            tv_dialog_cancel.setVisibility(View.GONE);
            view_vertical_linr.setVisibility(View.GONE);
        }
    }

}
