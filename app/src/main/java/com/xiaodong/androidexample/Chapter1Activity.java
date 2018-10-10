package com.xiaodong.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaodong.androidexample.chapter1.AcceptShareActivity;
import com.xiaodong.androidexample.chapter1.DialogActivity;
import com.xiaodong.androidexample.chapter1.GeeTestActivity;
import com.xiaodong.androidexample.chapter1.QQSlideMenuActivity;
import com.xiaodong.androidexample.chapter1.RotateMenuActivity;
import com.xiaodong.androidexample.chapter1.SlidingDrawerActivity;
import com.xiaodong.androidexample.chapter1.VerticalViewpagerActivity;
import com.xiaodong.androidexample.chapter1.VerticalViewpagerCalanderActivity;
import com.xiaodong.androidexample.chapter1.WeiXinMenuActivity;
import com.xiaodong.androidexample.utils.FormatDataUtils;
import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.base.ListAdapter;
import com.xiaodong.basetools.bean.BeanWraper;
import com.xiaodong.basetools.utils.SystemBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class Chapter1Activity extends JBaseActivity {


    @InjectView(R.id.recycleview)
    RecyclerView mRecycleview;

    private ListAdapter mListAdapter;
    private List<BeanWraper> mList = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_chapter1;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
        setTitle("chapter1");
        showOrHideBackButton(true);
        mList.add(FormatDataUtils.getBeanWraper("微信右上角弹出菜单"));
        mList.add(FormatDataUtils.getBeanWraper("抽屉式公告"));
        mList.add(FormatDataUtils.getBeanWraper("QQ侧滑菜单"));
        mList.add(FormatDataUtils.getBeanWraper("收放的旋转菜单"));
        mList.add(FormatDataUtils.getBeanWraper("垂直viewpager"));
        mList.add(FormatDataUtils.getBeanWraper("垂直viewpager日历"));
        mList.add(FormatDataUtils.getBeanWraper("极验验证"));
        mList.add(FormatDataUtils.getBeanWraper("接受分享"));
        mList.add(FormatDataUtils.getBeanWraper("提示Dialog"));

    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);


        mListAdapter = new ListAdapter(this, mList);

        mRecycleview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleview.setAdapter(mListAdapter);
    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
        mListAdapter.setOnButtonClick(new ListAdapter.onButtonClick() {
            @Override
            public void onBtnClick(View view, int position) {
                BeanWraper beanWraper = mList.get(position);
                Intent intent = null;
                switch (beanWraper.name) {
                    case "微信右上角弹出菜单":
                        intent = new Intent(Chapter1Activity.this, WeiXinMenuActivity.class);
                        break;
                    case "抽屉式公告":
                        intent = new Intent(Chapter1Activity.this, SlidingDrawerActivity.class);
                        break;
                    case "QQ侧滑菜单":
                        intent = new Intent(Chapter1Activity.this, QQSlideMenuActivity.class);
                        break;
                    case "垂直viewpager":
                        intent = new Intent(Chapter1Activity.this, VerticalViewpagerActivity.class);
                        break;
                    case "垂直viewpager日历":
                        intent = new Intent(Chapter1Activity.this, VerticalViewpagerCalanderActivity.class);
                        break;
                    case "极验验证":
                        intent = new Intent(Chapter1Activity.this, GeeTestActivity.class);
                        break;
                    case "接受分享":
                        intent = new Intent(Chapter1Activity.this, AcceptShareActivity.class);
                        break;
                    case "收放的旋转菜单":
                        intent = new Intent(Chapter1Activity.this, RotateMenuActivity.class);
                        break;
                    case "提示Dialog":
                        intent = new Intent(Chapter1Activity.this, DialogActivity.class);
                        break;
                }
                if (intent == null) {
                    return;
                }
                startActivity(intent);
            }
        });

    }


    @Override
    protected void initData(Bundle onSavedInstance) {
        super.initData(onSavedInstance);
    }

}
