package com.androidkits.camera.mediaselect;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.androidkits.camera.R;
import com.common.library.commons.common.CommonActivity;
import com.common.library.commons.constants.ArouterPath;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xiaodong.jin on 2019/05/27.
 * 功能描述：相册图片选择器
 */
@Route(path = ArouterPath.camera.AlbumSelectorActivity)
public class AlbumSelectorActivity extends CommonActivity {


    private RecyclerView mRvImage;
    private TextView mTvTime;
    private View mMasking;
    private RecyclerView mRvFolder;
    private RelativeLayout mRlTopBar;
    private FrameLayout mBtnBack;
    private LinearLayout mBtnFolder;
    private TextView mTvFolderName;
    private ImageView mIvArrow;
    private FrameLayout mBtnConfirm;
    private TextView mTvConfirm;
    private LinearLayout mRlBottomBar;
    private FrameLayout mBtnPreview;
    private TextView mTvPreview;
    private TextView mTvSize;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2019-05-27 17:53:00 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {

    }


    @Override
    protected int getHeaderLayoutId() {
        return CUSTOMER_HEADER;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.camera_activity_album_selector;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        mRvImage = findViewById(R.id.rv_image);
        mTvTime = findViewById(R.id.tv_time);
        mMasking = findViewById(R.id.masking);
        mRvFolder = findViewById(R.id.rv_folder);
        mRlTopBar = findViewById(R.id.rl_top_bar);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnFolder = findViewById(R.id.btn_folder);
        mTvFolderName = findViewById(R.id.tv_folder_name);
        mIvArrow = findViewById(R.id.iv_arrow);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mTvConfirm = findViewById(R.id.tv_confirm);
        mRlBottomBar = findViewById(R.id.rl_bottom_bar);
        mBtnPreview = findViewById(R.id.btn_preview);
        mTvPreview = findViewById(R.id.tv_preview);
        mTvSize = findViewById(R.id.tv_size);



    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
    }

    @Override
    protected void initData() {
        super.initData();
    }


}
