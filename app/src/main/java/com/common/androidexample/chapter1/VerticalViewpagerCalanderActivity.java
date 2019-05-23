package com.common.androidexample.chapter1;

import android.os.Bundle;

import com.common.androidexample.view.calendarview.Calendar;
import com.common.androidexample.view.calendarview.CalendarLayout;
import com.common.androidexample.view.calendarview.CalendarView;
import com.common.androidexample200.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.utils.SystemBarUtil;

public class VerticalViewpagerCalanderActivity extends CommonActivity implements CalendarView.OnYearChangeListener, CalendarView.OnDateSelectedListener {


    private CalendarLayout calendarLayout;
    private CalendarView calendarview;
    private int mYear;

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

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
