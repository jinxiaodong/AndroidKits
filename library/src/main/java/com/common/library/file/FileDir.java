package com.common.library.file;

import java.io.File;

/**
 * Created by xiaodong.jin on 2019/05/30.
 * description：各种app缓存和文件地址
 */
public class FileDir {

    /**
     * 缓存根目录
     */
    public final static String CACHE_ROOT = FileUtils.getCachePath()+ File.separator;

    /**
     * 文件存储根目录
     */
    public final static String FILES_ROOT = FileUtils.getFilesPath()+File.separator;


    public static class CachePath {
        /**
         * 数据缓存目录
         */
        public final static String CACHE_DATA = CACHE_ROOT + "data/";
        /**
         * 图片缓存目录
         */
        public final static String CACHE_IMG = CACHE_ROOT + "pictures/";
        /**
         * 音频缓存目录
         */
        public final static String CACHE_AUDIO = CACHE_ROOT + "audio/";
        /**
         * 聊天目录
         */
        public final static String CACHE_CHAT = CACHE_ROOT + "chat/";
        /**
         * 错误日志目录
         */
        public final static String CACHE_ERROR = CACHE_ROOT + "error/";
        /**
         * webview缓存路径 *
         */
        public static final String WEBVIEW_CACHE_PATH = "/webview";

        /**
         * 城市缓存时间 5天
         */
        public static final long CITY_INFO_CACHETIME = 1000 * 60 * 60 * 24 * 5;

        /**
         * 城市数据存储路径
         */
        public static final String CITY_FILE_PATH = CACHE_DATA + "cityinfo";

    }


    public static class FilesPath {

        /**
         * 图片存储目录
         */
        public final static String FILE_IMG = FILES_ROOT + "pictures/";
        /**
         * 音频存储目录
         */
        public final static String FILE_AUDIO = FILES_ROOT + "audios/";
        /**
         * 电影视频存储目录
         */
        public final static String FILE_MOVIES = FILES_ROOT + "movies/";
        /**
         * 音乐存储目录
         */
        public final static String FILE_MUSIC = FILES_ROOT + "music/";
    }
}
