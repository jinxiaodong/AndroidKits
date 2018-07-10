//package com.xiaodong.androidexample.block;
//
//import android.content.Context;
//
//import com.xiaodong.basetools.utils.LogUtil;
//
//
///**
// * Created by xiaodong.jin on 2017/10/23.
// */
//
//public class LocationBlock extends BaseBlock {
//
//    private LocationClient mLocationClient = null;
//    private BDAbstractLocationListener mLocationListener = new MyLocationListener();
//    private OnLocationListener mOnLocationListener;
//
//    public LocationBlock(Context context) {
//        super(context);
//        initLocation();
//    }
//
//    private void initLocation() {
//        mLocationClient = new LocationClient(BaseApplication.getContext());
//        //该类用来设置定位SDK的定位方式
//        LocationClientOption clientOption = new LocationClientOption();
//
//
//        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        clientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//
//        //可选，默认gcj02，设置返回的定位结果坐标系
//        clientOption.setCoorType("bd09ll");
//
//        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        int span = 1000;
//        clientOption.setScanSpan(span);
//
//        //可选，设置是否需要地址信息，默认不需要
//        clientOption.setIsNeedAddress(true);
//
//        //可选，默认false,设置是否使用gps
//        clientOption.setOpenGps(true);
//
//        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        clientOption.setLocationNotify(true);
//
//
//        mLocationClient.registerLocationListener(mLocationListener);
//        mLocationClient.setLocOption(clientOption);
//    }
//
//
//
//    private class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            //获取定位结果
//            int locType = bdLocation.getLocType();
//            String province = bdLocation.getProvince();//获取省信息
//            String city = bdLocation.getCity();        //获取城市信息
//            String district = bdLocation.getDistrict(); //获取区县信息
//            String cityCode = bdLocation.getCityCode();//获取城市码
//            Double longitude = bdLocation.getLongitude();//获取纬度信息
//            Double latitude = bdLocation.getLatitude(); //获取经度信息
//            if (latitude != IntentKey.Location.BAIDU_LOCATION_INVALID_VALUE && longitude != IntentKey.Location.BAIDU_LOCATION_INVALID_VALUE) {
//                LocationInfo locationInfo = new LocationInfo();
//                locationInfo.province = province;
//                locationInfo.city = city;
//                locationInfo.district = district;
//                locationInfo.cityCode = cityCode;
//                locationInfo.longitude = longitude;
//                locationInfo.latitude = latitude;
//
//                if(!LoactionUtils.getLocationInfo().city.equals(city)) {
//                    LoactionUtils.setLoactionInfo(locationInfo);
//                }
//                if (mOnLocationListener != null)
//                    mOnLocationListener.onLocationedSuccess(bdLocation);
//
//            } else {
//                if (mOnLocationListener != null)
//                    mOnLocationListener.onLocationedFail();
//            }
//
//            mLocationClient.stop();
//        }
//
//        @Override
//        public void onLocDiagnosticMessage(int i, int i1, String s) {
//            super.onLocDiagnosticMessage(i, i1, s);
//
//        }
//    }
//
//
//    public void statrLocation() {
//
//        if (mLocationClient != null) {
//            LogUtil.e("Location", "=================start location");
//            if (!mLocationClient.isStarted()) {
//                mLocationClient.start();
//            }
//        }
//    }
//
//    public void stopLocation() {
//        if (mLocationClient != null) {
//            if (mLocationClient.isStarted())
//                mLocationClient.stop();
//            if (mLocationListener != null) {
//                mLocationClient.unRegisterLocationListener(mLocationListener);
//                mLocationListener = null;
//            }
//            mLocationClient = null;
//        }
//    }
//
//
//    /*处理自己定义的option配置*/
//    public void setLocationClientOption(LocationClientOption option){
//        mLocationClient = null;
//        mLocationClient = new  LocationClient(BaseApplication.getContext());
//        mLocationClient.registerLocationListener(mLocationListener);
//        mLocationClient.setLocOption(option);
//    }
//
//
//    public void setOnLocationListener(OnLocationListener listener) {
//        mOnLocationListener = listener;
//    }
//
//    public interface OnLocationListener {
//        void onLocationedSuccess(BDLocation bdLocation);
//
//        void onLocationedFail();
//    }
//}
