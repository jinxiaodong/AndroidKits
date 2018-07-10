package com.xiaodong.androidexample.chapter1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.xiaodong.androidexample200.R;
import com.xiaodong.basetools.base.JBaseActivity;
import com.xiaodong.basetools.view.JButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GeeTestActivity extends JBaseActivity {

    private JButton button;

    private GT3GeetestUtilsBind mUtilsBind;
    private String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
    private String validateURL = "http://m.laiyifen.com/ouser-web/mobileRegister/sendCaptchasCodeFormNew.do";
    private String mGeetest_challenge;
    private String mMGeetest_challenge;
    private String mGeetest_validate;
    private String mGeetest_seccode;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_gee_test;
    }

    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });

    }

    private void go() {
        mUtilsBind = new GT3GeetestUtilsBind(mContext);
        mUtilsBind.getGeetest(mContext, captchaURL, validateURL, null, new GT3GeetestBindListener() {

            /**
             * num 1 点击验证码的关闭按钮来关闭验证码
             * num 2 点击屏幕关闭验证码
             * num 3 点击返回键关闭验证码
             */
            @Override
            public void gt3CloseDialog(int i) {
                super.gt3CloseDialog(i);
            }

            /**
             * 验证码加载准备完成
             */
            @Override
            public void gt3DialogReady() {
                super.gt3DialogReady();
            }

            /**
             * 拿到第一个url返回的数据
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {
                super.gt3FirstResult(jsonObject);
            }

            /**
             * 往API1请求中添加参数
             */
            @Override
            public Map<String, String> gt3CaptchaApi1() {
                Map<String, String> map = new HashMap<String, String>();
//                map.put("initType", "4");
//                map.put("mobile", "18895612587");
//                map.put("platformId", "3");
                return map;
            }


            /**
             * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
             */
            @Override
            public boolean gt3SetIsCustom() {
                return false;
            }

            /**
             * 统计数据
             */
            @Override
            public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
                super.gt3GeetestStatisticsJson(jsonObject);
            }


            /**
             * 拿到二次验证需要的数据
             */
            @Override
            public void gt3GetDialogResult(String s) {
                super.gt3GetDialogResult(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    mMGeetest_challenge = (String) jsonObject.get("geetest_challenge");
                    mGeetest_validate = (String) jsonObject.get("geetest_validate");
                    mGeetest_seccode = (String) jsonObject.get("geetest_seccode");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("TAG", "二次验证需要的参数" + s);
            }

            /**
             * 自定义二次验证，当gt3SetIsCustom为ture时执行这里面的代码
             */
            @Override
            public void gt3GetDialogResult(boolean b, String s) {
                super.gt3GetDialogResult(b, s);
            }

            /**
             * 往二次验证里面put数据
             */
            @Override
            public Map<String, String> gt3SecondResult() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("captcha_challenge",mMGeetest_challenge);
                map.put("captcha_validate",mGeetest_validate);
                map.put("captcha_seccode",mGeetest_seccode);
                map.put("mobile","18895612587");
                map.put("captchasType","3");
                map.put("platformId","3");
                return map;
            }

            /**
             * 验证全部走完的回调，result为验证后的数据
             */
            @Override
            public void gt3DialogSuccessResult(String s) {
                super.gt3DialogSuccessResult(s);
                Log.e("TAG", s);
            }

            /**
             * 验证过程中有错误会走这里
             */
            @Override
            public void gt3DialogOnError(String s) {
                super.gt3DialogOnError(s);
                Log.e("TAG", "验证失败");
            }
        });
    }
}
