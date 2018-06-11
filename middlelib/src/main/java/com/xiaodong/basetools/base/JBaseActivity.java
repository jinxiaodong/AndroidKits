package com.xiaodong.basetools.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.project.xiaodong.fflibrary.base.BaseActivity;
import com.xiaodong.basetools.R;
import com.xiaodong.basetools.constants.GlobalConstants;
import com.xiaodong.basetools.utils.DialogUtil;
import com.xiaodong.basetools.utils.easypermissions.EasyPermissions;
import com.xiaodong.basetools.view.JNavigationBar;
import com.xiaodong.basetools.view.swipebacklayout.SwipeBackActivityHelper;
import com.xiaodong.basetools.view.swipebacklayout.SwipeBackLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * Created by xiaodong.jin on 2018/6/11.
 * description：基类activity封装
 */

public class JBaseActivity extends BaseActivity {


    /*TAG*/
    public final String TAG = getClass().getSimpleName();

    /*activity*/
    protected Context mContext;

    /*LayoutInflater*/
    protected LayoutInflater mLayoutInflater;

    /*header 标题栏 */
    private JNavigationBar commonHeader;

    /*用于在异步请求中更新ui*/
    private Handler mHandler;

    /*处理框*/
    private Dialog mWaittingDialog = null;

    /*用于在计时操作*/
    private Timer mTimer;

    /*是否正在切换Fragment*/
    private boolean isSwitchFragmenting = false;

    /* 无数据view*/
    private View mNoDataView;

    /*swipebacklayout*/
    private SwipeBackActivityHelper swipeBackActivityHelper;

    /**
     * 保存图片方法
     */
    public static String saveBitmap(Bitmap bm, String picName) {
        if (TextUtils.isEmpty(picName))
            picName = "temp";
        File f = new File(GlobalConstants.CACHE_IMG, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return GlobalConstants.CACHE_IMG + picName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    protected int getHeaderLayoutId() {
        return R.layout.base_common_activity_header;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.base_common_activity_layout;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
        }
        return false;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
        ButterKnife.inject(this);
        mContext = this;
        mLayoutInflater = LayoutInflater.from(mContext);
        if (getHeaderView() != null) {
            commonHeader = findViewById(R.id.common_header);
        }

    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        swipeBackActivityHelper.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackActivityHelper.onPostCreate();
    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
        try {
            if (getHeaderView() != null && commonHeader != null) {

                commonHeader.setOnBackClickListener(new JNavigationBar.onBackClickListener() {
                    @Override
                    public void onTitleBarBackClick(View v) {
                        close();
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initData(Bundle onSavedInstance) {
        super.initData(onSavedInstance);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWaittingDialog != null && mWaittingDialog.isShowing()) {
            mWaittingDialog.dismiss();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * @param resLayId
     * @param fragment       是否加入返回栈
     * @param isAddBackStack
     * @description 替换Fragment (默认有动画效果)
     */
    protected void replaceFragment(int resLayId, Fragment fragment, boolean isAddBackStack) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
                R.anim.slide_out_right, R.anim.slide_in_left,
                R.anim.slide_out_right);
        fragmentTransaction.replace(resLayId, fragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        isSwitchFragmenting = false;
    }

    /**
     * @param resLayId       resid
     * @param fragment       fragment
     * @param isAddBackStack 是否加入返回栈
     * @param isAnimation    切换动画
     * @return
     * @description 替换Fragment
     */
    protected void replaceFragment(int resLayId, Fragment fragment,
                                   boolean isAddBackStack, boolean isAnimation) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        fragmentTransaction.replace(resLayId, fragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        isSwitchFragmenting = false;
    }

    /**
     * @param resLayId
     * @param showFragment
     * @param isAnimation
     * @param isAddBackStack
     * @param hideFragments  要隐藏的Fragment数组
     * @return
     * @description 添加Fragment
     */
    protected void addFragment(int resLayId, Fragment showFragment,
                               boolean isAnimation, boolean isAddBackStack,
                               Fragment... hideFragments) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (isAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        if (hideFragments != null) {
            for (Fragment hideFragment : hideFragments) {
                if (hideFragment != null)
                    fragmentTransaction.hide(hideFragment);
            }
        }
        fragmentTransaction.add(resLayId, showFragment);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        isSwitchFragmenting = false;
    }

    /**
     * @param showFragment   显示的fragment
     * @param hideFragments  要隐藏的Fragment数组
     * @param isAddBackStack 是否加入返回栈
     * @description 显示隐藏Fragment
     */
    protected void showHideFragment(Fragment showFragment, boolean isAnimation,
                                    boolean isAddBackStack, Fragment... hideFragments) {
        if (isSwitchFragmenting) {
            return;
        }
        isSwitchFragmenting = true;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (isAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right, R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        if (hideFragments != null) {
            for (Fragment hideFragment : hideFragments) {
                if (hideFragment != null && hideFragment.isAdded())
                    fragmentTransaction.hide(hideFragment);
            }
        }
        if (showFragment != null) {
            fragmentTransaction.show(showFragment);
        }
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        //#5266 nSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
        //再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()
        fragmentTransaction.commitAllowingStateLoss();
        isSwitchFragmenting = false;
    }


    /**
     * @param timerTask 定时器任务
     * @param seconds   定时器时长
     * @param loop      是否循环
     * @return Timer
     * @description 在子类中实现定时刷新功能
     */
    public Timer getTimer(TimerTask timerTask, int seconds, boolean loop) {
        try {
            cancelTimer();
            mTimer = new Timer();
            if (loop) {
                mTimer.schedule(timerTask, seconds * 1000, seconds * 1000);
            } else {
                mTimer.schedule(timerTask, seconds * 1000);
            }
            return mTimer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param
     * @return
     * @description 停止计时
     */
    public void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(mContext.getMainLooper());
        }
        return mHandler;
    }

    /*如果使用默认头部，可以从此方法获取，配置自己的属性*/
    public JNavigationBar getCommonHeader() {
        if (commonHeader == null) {
            return commonHeader = new JNavigationBar(mContext);
        }
        return commonHeader;
    }

    /*设置标题*/
    public void setTitle(String title) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            commonHeader.setTitlText(title);
            commonHeader.showOrHideTitle(true);
            commonHeader.showOrHideBar(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*设置标题*/
    public void setTitle(int strResId) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            commonHeader.setTitlText(getResString(strResId));
            commonHeader.showOrHideTitle(true);
            commonHeader.showOrHideBar(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*隐藏标题*/
    public void hideTitle() {
        if (getHeaderView() == null) {
            return;
        }
        try {
            commonHeader.showOrHideTitle(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shwoOrHideHeader(int viewState) {
        if (getHeaderView() == null) {
            return;
        }
        try {
            commonHeader.setVisibility(viewState);
        } catch (Exception e) {
        }
    }

    /*隐藏或显示返回按钮*/
    public void showOrHideBackButton(boolean isShow) {
        if (getHeaderView() == null) {
            return;
        }

        try {
            commonHeader.showOrHideBack(isShow);
        } catch (Exception e) {

        }

    }

    /**
     * 显示默认等待框
     */
    public void showDialog() {
        showDialog("");

    }

    public void showDialog(final String msg) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mWaittingDialog == null) {
                    mWaittingDialog = DialogUtil.createLoadingDialog(mContext, msg);
                }
                if (!JBaseActivity.this.isFinishing()) {
                    mWaittingDialog.show();
                }

            }
        });
    }

    /**
     * 取消等待框
     */
    public void dismissDialog() {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mWaittingDialog != null && mWaittingDialog.isShowing()) {
                    mWaittingDialog.dismiss();
                }
            }
        });

    }


    public void showNoDataNoti(ViewGroup viewGroup, View view) {
        try {
            if (mNoDataView == null) {
                mNoDataView = view;
                mNoDataView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //donothing
                    }
                });
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                viewGroup.addView(mNoDataView, lp);
            } else {
                if (mNoDataView == view) {
                    mNoDataView.setVisibility(View.VISIBLE);
                } else {
                    viewGroup.removeView(mNoDataView);
                    mNoDataView = view;
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    viewGroup.addView(mNoDataView, lp);
                    mNoDataView.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 显示无数据提示
     */
    public void showNoDataNoti(ViewGroup viewGroup, int layoutResId) {
        try {
            if (mNoDataView == null) {
                mNoDataView = mLayoutInflater.inflate(layoutResId, null);
                mNoDataView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //donothing
                    }
                });
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                viewGroup.addView(mNoDataView, lp);
            } else {
                mNoDataView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }
    }


    /**
     * 隐藏无数据提示
     */
    public void hideNoDataNoti() {
        if (mNoDataView != null) {
            mNoDataView.setVisibility(View.GONE);
        }
    }

    /**
     * 无网络刷新后，重新获得数据，供子类实现
     */
    public void reRequestData() {

    }

    public SwipeBackLayout getSwipeBackLayout() {
        if (swipeBackActivityHelper != null) {
            return swipeBackActivityHelper.getSwipeBackLayout();
        }
        return null;
    }

    public void setEnableBackLayout(boolean enable) {
        if (getSwipeBackLayout() != null) {
            getSwipeBackLayout().setEnableGesture(enable);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public String getResString(int res) {
        return getResources().getString(res) + "";
    }

    private long lastClickTime;

    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= 300;
    }
}
