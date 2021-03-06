package com.androidkits.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.androidkits.example.chapter1.AcceptShareActivity;
import com.androidkits.example.chapter1.AnimalActivity2;
import com.androidkits.example.chapter1.ClickSpanActivity;
import com.androidkits.example.chapter1.ClipboardActivity;
import com.androidkits.example.chapter1.DialogActivity;
import com.androidkits.example.chapter1.DragScaleViewActivity;
import com.androidkits.example.chapter1.EditTextActivity;
import com.androidkits.example.chapter1.GeeTestActivity;
import com.androidkits.example.chapter1.QQSlideMenuActivity;
import com.androidkits.example.chapter1.RotateMenuActivity;
import com.androidkits.example.chapter1.SlidingDrawerActivity;
import com.androidkits.example.chapter1.SoftKeyBoardActivity;
import com.androidkits.example.chapter1.VerticalViewpagerActivity;
import com.androidkits.example.chapter1.VerticalViewpagerCalanderActivity;
import com.androidkits.example.chapter1.WeiXinMenuActivity;
import com.androidkits.example.utils.FormatDataUtils;
import com.common.library.base.adapter.ListAdapter;
import com.common.library.bean.BeanWraper;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class Chapter1Activity extends CommonActivity {


    @BindView(R.id.recycleview)
    RecyclerView mRecycleview;

    private ListAdapter mListAdapter;
    private List<BeanWraper> mList = new ArrayList<>();

    @Override
    protected int getHeaderLayoutId() {
        return DEFAULT_HEADER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_chapter1;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
        setTitle("chapter1");
        showBack();
        mList.add(FormatDataUtils.getBeanWraper("微信右上角弹出菜单"));
        mList.add(FormatDataUtils.getBeanWraper("抽屉式公告"));
        mList.add(FormatDataUtils.getBeanWraper("QQ侧滑菜单"));
        mList.add(FormatDataUtils.getBeanWraper("收放的旋转菜单"));
        mList.add(FormatDataUtils.getBeanWraper("垂直viewpager"));
        mList.add(FormatDataUtils.getBeanWraper("垂直viewpager日历"));
        mList.add(FormatDataUtils.getBeanWraper("极验验证"));
        mList.add(FormatDataUtils.getBeanWraper("接受分享"));
        mList.add(FormatDataUtils.getBeanWraper("提示Dialog"));
        mList.add(FormatDataUtils.getBeanWraper("EditText"));
        mList.add(FormatDataUtils.getBeanWraper("软键盘弹出监听"));
        mList.add(FormatDataUtils.getBeanWraper("剪切板"));
        mList.add(FormatDataUtils.getBeanWraper("Animation2"));
        mList.add(FormatDataUtils.getBeanWraper("ClickSpan"));
        mList.add(FormatDataUtils.getBeanWraper("DragScaleView"));

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
                    case "EditText":
                        intent = new Intent(Chapter1Activity.this, EditTextActivity.class);
                    case "软键盘弹出监听":
                        intent = new Intent(Chapter1Activity.this, SoftKeyBoardActivity.class);
                        break;
                    case "剪切板":
                        intent = new Intent(Chapter1Activity.this, ClipboardActivity.class);
                        break;
                    case "Animation2":
                        intent = new Intent(Chapter1Activity.this, AnimalActivity2.class);
                        break;
                    case "ClickSpan":
                        intent = new Intent(Chapter1Activity.this, ClickSpanActivity.class);
                        break;
                    case "DragScaleView":
                        intent = new Intent(Chapter1Activity.this, DragScaleViewActivity.class);
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
    protected void initData() {
        super.initData();
    }
}
