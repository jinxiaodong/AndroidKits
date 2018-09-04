package com.xiaodong.androidexample.chapter1;

import android.os.Bundle;

import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.utils.SystemBarUtil;
import com.xiaodong.basetools.view.calendarview.Calendar;
import com.xiaodong.basetools.view.calendarview.CalendarLayout;
import com.xiaodong.basetools.view.calendarview.CalendarView;

public class VerticalViewpagerCalanderActivity extends JBaseActivity implements CalendarView.OnYearChangeListener, CalendarView.OnDateSelectedListener {


    private CalendarLayout calendarLayout;
    private CalendarView calendarview;
    private int mYear;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_vertical_viewpager_calander;
    }


    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);

        SystemBarUtil.setChenJinTitle(getCommonHeader().getGuider(), mContext);
        setTitle("垂直Viewpager日历");
        calendarLayout =(CalendarLayout) findViewById(R.id.calendarLayout);
        calendarview =(CalendarView) findViewById(R.id.calendarview);
        calendarview.setFixMode();
        calendarview.setOnYearChangeListener(this);
        calendarview.setOnDateSelectedListener(this);

        mYear = calendarview.getCurYear();
    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {

    }
}
