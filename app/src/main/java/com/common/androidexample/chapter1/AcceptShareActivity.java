package com.common.androidexample.chapter1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.androidexample200.R;
import com.common.library.commons.common.CommonActivity;

public class AcceptShareActivity extends CommonActivity {
    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvText;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-07-10 16:37:15 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {

    }

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_accept_share;
    }

    @Override
    protected void initValue(Bundle onSavedInstance) {
        super.initValue(onSavedInstance);


    }


    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);

        ivImage = (ImageView) findViewById(R.id.iv_image);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvText = (TextView) findViewById(R.id.tv_text);
        setTitle("接受第三方分享");
    }

    @Override
    protected void initListener(Bundle onSavedInstance) {
        super.initListener(onSavedInstance);
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            Log.e("TAG", type + "");
            //todo 处理各种类型的数据建议 抽取工具类
            handleData(intent, type);

        }
    }

    private void handleData(Intent intent, String type) {
        if (type.startsWith("text/")) {

            handleText(intent);
        } else if (type.startsWith("image/")) {

            handleImage(intent);
        }
    }

    private void handleImage(Intent intent) {
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        ivImage.setImageURI(uri);
    }

    private void handleText(Intent intent) {
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        String title = intent.getStringExtra(Intent.EXTRA_TITLE);
        tvTitle.setText(title);
        tvText.setText(text);
    }

}
