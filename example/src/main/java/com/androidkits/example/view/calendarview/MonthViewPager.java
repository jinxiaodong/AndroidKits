package com.androidkits.example.view.calendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.androidkits.example.view.VerticalViewPager;

import java.lang.reflect.Constructor;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by xiaodong.jin on 2018/6/21.
 * description：
 */

public class MonthViewPager extends VerticalViewPager {


    /*设置年限区间的月数总和*/
    private int mMonthCount;

    private CalendarViewDelegate mDelegate;

    private int mNextViewHeight, mPreViewHeight, mCurrentViewHeight;

    CalendarLayout mParentLayout;

    WeekBar mWeekBar;

    /**
     * 是否使用滚动到某一天
     */
    private boolean isUsingScrollToCalendar = false;

    public MonthViewPager(Context context) {
        this(context, null);
    }

    public MonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    void setup(CalendarViewDelegate delegate) {

        this.mDelegate = delegate;
        updateMonthViewHeight(mDelegate.getCurrentDay().getYear(), mDelegate.getCurrentDay().getMonth());
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = mCurrentViewHeight;
        setLayoutParams(params);
        init();
    }

    private void init() {
        mMonthCount = 12 * (mDelegate.getMaxYear() - mDelegate.getMinYear())
                - mDelegate.getMinYearMonth() + 1 +
                mDelegate.getMaxYearMonth();
        setAdapter(new MonthViewPagerAdapter());
        setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (mDelegate.getMonthViewShowMode() == CalendarViewDelegate.MODE_ALL_MONTH) {
                    return;
                }
                int height;
                if (position < getCurrentItem()) {//右（下）滑-1
                    height = (int) ((mPreViewHeight) * (1 - positionOffset) + mCurrentViewHeight * positionOffset);
                } else {//左（上）滑+1
                    height = (int) ((mCurrentViewHeight)
                            * (1 - positionOffset) +
                            (mNextViewHeight)
                                    * positionOffset);
                }
                ViewGroup.LayoutParams layoutParams = mParentLayout.getLayoutParams();
//                ViewGroup.LayoutParams params = getLayoutParams();
                layoutParams.height = height;
                mParentLayout.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                Calendar calendar = new Calendar();
                calendar.setYear((position + mDelegate.getMinYearMonth() - 1) / 12 + mDelegate.getMinYear());
                calendar.setMonth((position + mDelegate.getMinYearMonth() - 1) % 12 + 1);
                calendar.setDay(1);
                calendar.setCurrentMonth(calendar.getYear() == mDelegate.getCurrentDay().getYear() &&
                        calendar.getMonth() == mDelegate.getCurrentDay().getMonth());
                calendar.setCurrentDay(calendar.equals(mDelegate.getCurrentDay()));
//                LunarCalendar.setupLunarCalendar(calendar);

                if (mDelegate.mMonthChangeListener != null) {
                    mDelegate.mMonthChangeListener.onMonthChange(calendar.getYear(), calendar.getMonth());
                }

                if (mDelegate.getMonthViewShowMode() != CalendarViewDelegate.MODE_ALL_MONTH
                        && getVisibility() == VISIBLE) {
                    updateMonthViewHeight(calendar.getYear(), calendar.getMonth());
                }


                if (!calendar.isCurrentMonth()) {
                    mDelegate.mSelectedCalendar = calendar;
                } else {
                    mDelegate.mSelectedCalendar = mDelegate.createCurrentDate();
                }

                if (mDelegate.mDateSelectedListener != null && !isUsingScrollToCalendar) {
                    mWeekBar.onDateSelected(mDelegate.mSelectedCalendar, mDelegate.getWeekStart(), false);
                    mDelegate.mDateSelectedListener.onDateSelected(mDelegate.mSelectedCalendar, false);
                }

                MonthView view = (MonthView) findViewWithTag(position);
                if (view != null) {
                    int index = view.getSelectedIndex(mDelegate.mSelectedCalendar);
                    Calendar currentDay = mDelegate.getCurrentDay();
                    view.mCurrentItem = index;
                    if (index >= 0 && mParentLayout != null) {
                        mParentLayout.setSelectPosition(index);
                    }
                    view.invalidate();
                }
                updateMonthViewHeight(calendar.getYear(), calendar.getMonth());
                isUsingScrollToCalendar = false;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 更新月视图的高度
     *
     * @param year
     * @param month
     */
    private void updateMonthViewHeight(int year, int month) {
        if (mDelegate.getMonthViewShowMode() == CalendarViewDelegate.MODE_ALL_MONTH) {
            mCurrentViewHeight = 6 * mDelegate.getCalendarItemHeight();
            return;
        }

        if (mParentLayout != null) {
            if (getVisibility() != VISIBLE) {//如果已经显示周视图，则需要动态改变月视图高度
                ViewGroup.LayoutParams params = getLayoutParams();
                params.height = CalendarUtil.getMonthViewHeight(year, month, mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart());
                setLayoutParams(params);
            }
            mParentLayout.updateContentViewTranslateY();
        }
        mCurrentViewHeight = CalendarUtil.getMonthViewHeight(year, month, mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart());

        if (month == 1) {
            mPreViewHeight = CalendarUtil.getMonthViewHeight(year - 1, 12, mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart());
            mNextViewHeight = CalendarUtil.getMonthViewHeight(year, 2, mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart());
        } else {
            mPreViewHeight = CalendarUtil.getMonthViewHeight(year, month - 1, mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart());
            if (month == 12) {
                mNextViewHeight = CalendarUtil.getMonthViewHeight(year + 1, 1, mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart());
            } else {
                mNextViewHeight = CalendarUtil.getMonthViewHeight(year, month + 1, mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart());
            }
        }

    }


    void notifyDataSetChanged() {
        mMonthCount = 12 * (mDelegate.getMaxYear() - mDelegate.getMinYear())
                - mDelegate.getMinYearMonth() + 1 +
                mDelegate.getMaxYearMonth();
        getAdapter().notifyDataSetChanged();
    }

    /**
     * 滚动到指定日期
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    void scrollToCalendar(int year, int month, int day, boolean smoothScroll) {
        isUsingScrollToCalendar = true;
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setCurrentDay(calendar.equals(mDelegate.getCurrentDay()));
//        LunarCalendar.setupLunarCalendar(calendar);
        mDelegate.mSelectedCalendar = calendar;

        int y = calendar.getYear() - mDelegate.getMinYear();
        int position = 12 * y + calendar.getMonth() - mDelegate.getMinYearMonth();
        int curItem = getCurrentItem();
        if (curItem == position) {
            isUsingScrollToCalendar = false;
        }
        setCurrentItem(position, smoothScroll);

        MonthView view = (MonthView) findViewWithTag(position);
        if (view != null) {
            view.setSelectedCalendar(mDelegate.mSelectedCalendar);
            view.invalidate();
            if (mParentLayout != null) {
                mParentLayout.setSelectPosition(view.getSelectedIndex(mDelegate.mSelectedCalendar));
            }
        }
        if (mParentLayout != null) {
            int i = CalendarUtil.getWeekFromDayInMonth(calendar, mDelegate.getWeekStart());
            mParentLayout.setSelectWeek(i);
        }


        if (mDelegate.mInnerListener != null) {
            mDelegate.mInnerListener.onMonthDateSelected(calendar, false);
        }

        if (mDelegate.mDateSelectedListener != null) {
            mDelegate.mDateSelectedListener.onDateSelected(calendar, false);
        }
        updateSelected();
    }

    /**
     * 滚动到当前日期
     */
    void scrollToCurrent(boolean smoothScroll) {
        isUsingScrollToCalendar = true;
        int position = 12 * (mDelegate.getCurrentDay().getYear() - mDelegate.getMinYear()) +
                mDelegate.getCurrentDay().getMonth() - mDelegate.getMinYearMonth();
        int curItem = getCurrentItem();
        if (curItem == position) {
            isUsingScrollToCalendar = false;
        }

        setCurrentItem(position, smoothScroll);

        MonthView view = (MonthView) findViewWithTag(position);
        if (view != null) {
            view.setSelectedCalendar(mDelegate.getCurrentDay());
            view.invalidate();
            if (mParentLayout != null) {
                mParentLayout.setSelectPosition(view.getSelectedIndex(mDelegate.getCurrentDay()));
            }
        }
        if (mDelegate.mDateSelectedListener != null && getVisibility() == VISIBLE) {
            mDelegate.mDateSelectedListener.onDateSelected(mDelegate.createCurrentDate(), false);
        }
    }


    /**
     * 更新选择效果
     */
    void updateSelected() {
        for (int i = 0; i < getChildCount(); i++) {
            MonthView view = (MonthView) getChildAt(i);
            view.setSelectedCalendar(mDelegate.mSelectedCalendar);
            view.invalidate();
        }
    }

    /**
     * 更新标记日期
     */
    void updateScheme() {
        for (int i = 0; i < getChildCount(); i++) {
            MonthView view = (MonthView) getChildAt(i);
            view.update();
        }
    }


    /**
     * 更新当前日期，夜间过度的时候调用这个函数，一般不需要调用
     */
    void updateCurrentDate() {
        for (int i = 0; i < getChildCount(); i++) {
            MonthView view = (MonthView) getChildAt(i);
            view.updateCurrentDate();
        }
    }

    /**
     * 更新显示模式
     */
    void updateShowMode() {
        for (int i = 0; i < getChildCount(); i++) {
            MonthView view = (MonthView) getChildAt(i);
            view.updateShowMode();
            view.requestLayout();
        }
        if (mDelegate.getMonthViewShowMode() == CalendarViewDelegate.MODE_ALL_MONTH) {
            mCurrentViewHeight = 6 * mDelegate.getCalendarItemHeight();
            mNextViewHeight = mCurrentViewHeight;
            mPreViewHeight = mCurrentViewHeight;
        } else {
            updateMonthViewHeight(mDelegate.mSelectedCalendar.getYear(), mDelegate.mSelectedCalendar.getMonth());
        }
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = mCurrentViewHeight;
        setLayoutParams(params);
        if (mParentLayout != null) {
            mParentLayout.updateContentViewTranslateY();
        }
    }

    /**
     * 更新周起始
     */
    void updateWeekStart() {
        for (int i = 0; i < getChildCount(); i++) {
            MonthView view = (MonthView) getChildAt(i);
            view.updateWeekStart();
            view.requestLayout();
        }
        if (mDelegate.getMonthViewShowMode() == CalendarViewDelegate.MODE_ALL_MONTH) {
            mCurrentViewHeight = 6 * mDelegate.getCalendarItemHeight();
            return;
        }
        updateMonthViewHeight(mDelegate.mSelectedCalendar.getYear(), mDelegate.mSelectedCalendar.getMonth());
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = mCurrentViewHeight;
        setLayoutParams(params);
        if (mParentLayout != null) {
            int i = CalendarUtil.getWeekFromDayInMonth(mDelegate.mSelectedCalendar, mDelegate.getWeekStart());
            mParentLayout.setSelectWeek(i);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDelegate.isMonthViewScrollable() && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDelegate.isMonthViewScrollable() && super.onInterceptTouchEvent(ev);
    }


    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (Math.abs(getCurrentItem() - item) > 1) {
            super.setCurrentItem(item, false);
        } else {
            super.setCurrentItem(item, smoothScroll);
        }
    }


    /**
     * 日历卡月份Adapter
     */
    private class MonthViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mMonthCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int year = (position + mDelegate.getMinYearMonth() - 1) / 12 + mDelegate.getMinYear();
            int month = (position + mDelegate.getMinYearMonth() - 1) % 12 + 1;
            MonthView view;
            if (TextUtils.isEmpty(mDelegate.getMonthViewClass())) {
                view = new DefaultMonthView(getContext());
            } else {
                try {
                    Class<?> cls = Class.forName(mDelegate.getMonthViewClass());
                    Constructor constructor = cls.getConstructor(Context.class);
                    view = (MonthView) constructor.newInstance(getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            view.mParentLayout = mParentLayout;
            view.mMonthViewPager = MonthViewPager.this;
            view.setup(mDelegate);
            view.setTag(position);
            view.setCurrentDate(year, month);
            view.setSelectedCalendar(mDelegate.mSelectedCalendar);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
