package com.androidkits;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.common.androidexample200.R;
import com.common.library.view.MyCoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by jinxuefen on 2019/4/2.
 * 功能描述：首页复杂布局demo
 */
public class HomeLayoutActivity extends AppCompatActivity {

    private RecyclerView mHomeRecyclerview;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private MagicIndicator mMagicIndicator;
    private RelativeLayout mRlIndicator;
    private AppBarLayout mAppbarLayout;
    private ViewPager mViewPager;
    private MyCoordinatorLayout mCoordinatorLayout;
    private SmartRefreshLayout mHomeRefreshLayout;
    private CommonNavigator mCommonNavigator;
    private boolean isCanPullDownRefresh;

    private List<String> mTitles = new ArrayList<>();
    private Context mContext;
    private List<HomeBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        mContext = this;
        for (int i = 1; i < 7; i++) {
            mTitles.add("第" + i + "个");
        }
        initView();
        initListener();

        initData();
    }


    private void initView() {
        mHomeRecyclerview = (RecyclerView) findViewById(R.id.home_recyclerview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mMagicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        mRlIndicator = (RelativeLayout) findViewById(R.id.rl_indicator);
        mAppbarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCoordinatorLayout = (MyCoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mHomeRefreshLayout = (SmartRefreshLayout) findViewById(R.id.home_refresh_layout);

        mCommonNavigator = new CommonNavigator(mContext);
        mHomeRecyclerview.setNestedScrollingEnabled(true);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        mHomeRefreshLayout.setEnableRefresh(false);


    }


    private void initListener() {

        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mHomeRefreshLayout.setEnableRefresh(verticalOffset == 0);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.RED);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(mTitles.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);

                return indicator;
            }
        });

        mMagicIndicator.setNavigator(mCommonNavigator);


    }


    private void initData() {

        mHomeRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HomeBean homeBean = new HomeBean();
            homeBean.index = "我是Activity中的第" + (i + 1) + "item";
            mList.add(homeBean);
        }
        mHomeRecyclerview.setAdapter(new HomeLayoutAdapter(mList));


        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return HomeCaseFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });
        mCommonNavigator.notifyDataSetChanged();
    }
}
