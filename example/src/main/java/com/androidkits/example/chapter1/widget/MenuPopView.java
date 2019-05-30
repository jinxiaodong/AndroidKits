package com.androidkits.example.chapter1.widget;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.androidkits.example.R;
import com.common.library.utils.DeviceUtil;

/**
 * Created by xiaodong.jin on 2018/6/12.
 * description：待完善
 */

public class MenuPopView extends PopupWindow {

    private Activity activity;
    private int layoutId;


    /*参照物：View*/
    private View mTargetView;
    /*接口回调，自己设置适配器等*/
    private CallBackView mCallBackView;

    public MenuPopView(Activity activity, int layoutId, View mTargetView, CallBackView mcallbackView) {
        this(activity, layoutId, mTargetView, mcallbackView, true);
    }

    public MenuPopView(Activity activity, int layoutId, View mTargetView, CallBackView mcallbackView, boolean isShow) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.mTargetView = mTargetView;
        this.mCallBackView = mcallbackView;
        if (isShow) {
            initView();
        }
    }

    private void initView() {
        if (layoutId == 0)
            return;
        View view = createView(activity, layoutId);
        this.setContentView(view);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return false;
            }
        });

        mCallBackView.callBackView(view, this);

        setAnimationStyle(R.style.mypopwindow_anim_style);

        showAsDropDown(mTargetView, 0, 0);
    }


    private FrameLayout parent;

    private View createView(final Activity activity, int subview) {
        View view = LayoutInflater.from(activity).inflate(subview, null, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isShowing()) {
                    dismiss();
                }
                return true;
            }
        });

        parent = new FrameLayout(activity);
        parent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        parent.addView(view);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return parent;
    }

    public void popShow() {
        this.showAsDropDown(mTargetView, 0, DeviceUtil.dp2px(45));

    }






    /*设置参照物View*/
    public void setTargetView(View targetView) {
        mTargetView = targetView;
    }

    public interface CallBackView {
        void callBackView(View view, MenuPopView menuPopView);
    }

}
