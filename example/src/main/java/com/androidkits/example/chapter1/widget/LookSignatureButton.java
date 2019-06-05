package com.androidkits.example.chapter1.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.androidkits.example.R;
import com.common.library.commons.device.DeviceInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by xiaodong.jin on 2019/05/13.
 * description：
 */
public class LookSignatureButton extends FrameLayout {

    private final int msg_what = 1001;     //自动隐藏时间

    private int delayMillis = 3000;     //自动隐藏时间

    private RelativeLayout mllLookSignature;
    private RelativeLayout rl_view;

    private boolean isHide = true;
    private ObjectAnimator translationHide;
    private ObjectAnimator translationShow;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == msg_what && !isHide && mllLookSignature != null) {
                try {
                    translationHide.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private boolean isAnimationIng = false;

    public LookSignatureButton(@NonNull Context context) {
        this(context, null);
    }

    public LookSignatureButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LookSignatureButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAnimation();
        startCountTime();
    }


    private void initView(Context context) {
        View view = inflate(context, R.layout.layout_look_signature_button, this);
        mllLookSignature = view.findViewById(R.id.ll_look_signature);
        rl_view = view.findViewById(R.id.rl_view);
        rl_view.setTranslationX(-DeviceInfo.WIDTHPIXELS);
        mllLookSignature.setOnClickListener(v -> {
            if (isHide && !isAnimationIng) {
                translationShow.start();
            } else if (!isHide && !isAnimationIng) {
                translationHide.start();
            }
        });
    }

    public void initAnimation() {
        mllLookSignature.post(new Runnable() {
            @Override
            public void run() {
                int distance = DeviceInfo.WIDTHPIXELS;
                translationHide = new ObjectAnimator().ofFloat(rl_view, "translationX", 0, distance);
                translationShow = new ObjectAnimator().ofFloat(rl_view, "translationX", distance, 0);

                translationHide.setDuration(300);
                translationShow.setDuration(300);

                translationShow.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isHide = false;
                        isAnimationIng = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        isAnimationIng = true;
                    }
                });
                translationHide.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isHide = true;
                        isAnimationIng = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        isAnimationIng = true;
                    }
                });
            }
        });
    }


    public void startCountTime() {
        if (mHandler != null) {
            mHandler.removeMessages(msg_what);
            mHandler.sendEmptyMessageDelayed(msg_what, delayMillis);
        }
    }

    public void startCountTime(long delay) {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(msg_what, delay);
        }
    }


    public void destroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        translationHide.cancel();
        translationShow.cancel();
        mllLookSignature.clearAnimation();
    }


    public void setOnShowListClickListener(onShowListClickListener listClickListener) {
        this.onShowListClickListener = listClickListener;
    }

    private onShowListClickListener onShowListClickListener;

    public interface onShowListClickListener {
        void onShowListClick();
    }
}
