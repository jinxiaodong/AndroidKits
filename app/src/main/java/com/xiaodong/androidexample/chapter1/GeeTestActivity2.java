package com.xiaodong.androidexample.chapter1;

import android.os.Bundle;

import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.xiaodong.basetools.base.JBaseActivity;

public class GeeTestActivity2 extends JBaseActivity {


    private GT3GeetestUtilsBind mUtilsBind;
    private String captchaURL ="http://m.laiyifen.com/ouser-web/api/user/init.do";
    private String validateURL ="http://m.laiyifen.com/ouser-web/mobileRegister/sendCaptchasCodeFormNew.do";


//    @Override
//    protected int getContentLayoutId() {
//        return R.layout.activity_gee_test;
//    }


    @Override
    protected void initWidget(Bundle onSavedInstance) {
        super.initWidget(onSavedInstance);
        mUtilsBind = new GT3GeetestUtilsBind(this);
    }

//    public void click(View view) {
//
//        mUtilsBind.getGeetest(this, captchaURL, validateURL, null, new GT3GeetestBindListener() {
//
//            /**
//             * num 1 点击验证码的关闭按钮来关闭验证码
//             * num 2 点击屏幕关闭验证码
//             * num 3 点击返回键关闭验证码
//             */
//            @Override
//            public void gt3CloseDialog(int i) {
//                super.gt3CloseDialog(i);
//            }
//
//            /**
//             * 验证码加载准备完成
//             */
//            @Override
//            public void gt3DialogReady() {
//                super.gt3DialogReady();
//            }
//
//            /**
//             * 拿到第一个url返回的数据
//             */
//            @Override
//            public void gt3FirstResult(JSONObject jsonObject) {
//                super.gt3FirstResult(jsonObject);
//            }
//
//            /**
//             * 往API1请求中添加参数
//             */
//            @Override
//            public Map<String, String> gt3CaptchaApi1() {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("t", System.currentTimeMillis()+"");
//                map.put("initType","4");
//                map.put("mobile", "18895612587");
//                map.put("platformId", "3");
//                return map;
//            }
//
//
//            /**
//             * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
//             */
//            @Override
//            public boolean gt3SetIsCustom() {
//                return false;
//            }
//
//            /**
//             * 统计数据
//             */
//            @Override
//            public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
//                super.gt3GeetestStatisticsJson(jsonObject);
//            }
//
//
//            /**
//             * 拿到二次验证需要的数据
//             */
//            @Override
//            public void gt3GetDialogResult(String s) {
//                super.gt3GetDialogResult(s);
//                Log.e("TAG", "二次验证需要的参数"+s);
//            }
//
//            /**
//             * 自定义二次验证，当gt3SetIsCustom为ture时执行这里面的代码
//             */
//            @Override
//            public void gt3GetDialogResult(boolean b, String s) {
//                super.gt3GetDialogResult(b, s);
//            }
//
//            /**
//             * 往二次验证里面put数据
//             */
//            @Override
//            public Map<String, String> gt3SecondResult() {
//
//                return super.gt3SecondResult();
//            }
//
//            /**
//             * 验证全部走完的回调，result为验证后的数据
//             */
//            @Override
//            public void gt3DialogSuccessResult(String s) {
//                super.gt3DialogSuccessResult(s);
//                Log.e("TAG", s);
//            }
//
//            /**
//             * 验证过程中有错误会走这里
//             */
//            @Override
//            public void gt3DialogOnError(String s) {
//                super.gt3DialogOnError(s);
//                Log.e("TAG", "验证失败");
//            }
//        });
//
//    }
}
