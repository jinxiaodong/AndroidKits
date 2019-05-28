package com.androidkits.camera.mediaselect.config;

import com.common.library.bean.camera.MediaInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xiaodong.jin on 2019/05/28.
 * description：照片选择器配置类
 */
public class ImageSelectorConfig implements Serializable {

    //最大的图片选择数
    public int mMaxCount;
    //是否单选
    public boolean isSingle;
    //是否使用拍照功能
    public boolean useCamera;
    //是否包含视频
    public boolean isContainsVideo;
    //是否选择视频后就只能选一个
    public boolean isSingleVideo;
    //原来已选择的图片
    public ArrayList<MediaInfo> mSelectedImages;


    private ImageSelectorConfig(Builder builder) {
        mMaxCount = builder.mMaxCount;
        isSingle = builder.isSingle;
        useCamera = builder.useCamera;
        isContainsVideo = builder.isContainsVideo;
        isSingleVideo = builder.isSingleVideo;
        mSelectedImages = builder.mSelectedImages;
    }

    public static class Builder {
        //最大的图片选择数
        private int mMaxCount = 0;
        //是否单选
        private boolean isSingle = false;
        //是否使用拍照功能
        private boolean useCamera = true;
        //是否包含视频
        private boolean isContainsVideo = false;
        //是否选择视频后就只能选一个
        private boolean isSingleVideo = false;
        //原来已选择的图片
        private ArrayList<MediaInfo> mSelectedImages = new ArrayList<>();

        public Builder() {

        }

        public ImageSelectorConfig.Builder setMaxCount(int maxCount) {
            this.mMaxCount = maxCount;
            return this;
        }

        public ImageSelectorConfig.Builder isSingle(boolean isSingle) {
            this.isSingle = isSingle;
            return this;
        }

        public ImageSelectorConfig.Builder useCamera(boolean useCamera) {
            this.useCamera = useCamera;
            return this;
        }

        public ImageSelectorConfig.Builder isContainsVideo(boolean isContainsVideo) {
            this.isContainsVideo = isContainsVideo;
            return this;
        }

        public ImageSelectorConfig.Builder setSelectedImages(ArrayList<MediaInfo> mSelectedImages) {
            this.mSelectedImages = mSelectedImages;
            return this;
        }


        public ImageSelectorConfig build() {
            return new ImageSelectorConfig(this);
        }
    }

}
