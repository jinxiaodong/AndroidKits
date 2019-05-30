package com.androidkits.chapter1;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidkits.chapter1.widget.MenuPopView;
import com.androidkits.utils.FormatDataUtils;
import com.common.androidexample200.R;
import com.common.library.bean.BeanWraper;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class WeiXinMenuActivity extends CommonActivity {


    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.btn_menu)
    LinearLayout mBtnMenu;
    private List<BeanWraper> mList = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_wei_xin_menu;
    }

    @Override
    protected int getHeaderLayoutId() {
        return -1;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        SystemBarUtil.setChenJinTitle(mStatusBar, mContext);
//        setTitle("微信右上角弹出菜单");
//        showOrHideBackButton(true);
        mList.add(FormatDataUtils.getBeanWraper("发起群聊"));
        mList.add(FormatDataUtils.getBeanWraper("添加好友"));
        mList.add(FormatDataUtils.getBeanWraper("扫一扫"));
        mList.add(FormatDataUtils.getBeanWraper("收付款"));
        mList.add(FormatDataUtils.getBeanWraper("帮助与反馈"));
    }

    public void OnMenu(View view) {

        MenuPopView menuPopView = new MenuPopView(this, R.layout.weixi_menu, mBtnMenu, new MenuPopView.CallBackView() {
            @Override
            public void callBackView(View view, final MenuPopView menuPopView) {
                RecyclerView recyclerView = view.findViewById(R.id.recycleview);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                MenuAdapter menuAdapter = new MenuAdapter(mContext, mList);
                menuAdapter.setOnItemClickListener(new MenuAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        menuPopView.dismiss();
                    }
                });
                recyclerView.setAdapter(menuAdapter);
            }

        });
    }


}
