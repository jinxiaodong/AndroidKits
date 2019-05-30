package com.androidkits.example.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.androidkits.example.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class GlideUtil {

    private static RequestOptions options;

    public static String getUrl(String url, int width) {
        if (url != null && url.contains("cdn.oudianyun")) {
            if (url.contains("@base@tag=imgScale")) {
                if (width > 0) {
                    url += "&w=" + width;
                }
                url += "&F=webp";
            } else {
                url += "@base@tag=imgScale";
                if (width > 0) {
                    url += "&w=" + width;
                }
                url += "&F=webp";
            }
        }
        return url;
    }

    public static void load(Context context, RequestOptions options, String url, int res, int width, ImageView imageView) {
        if (filterContext(context)) {
            Glide.with(context)
                    .load(res == 0 ? getUrl(url, width) : res)
                    .apply(options).into(imageView);
        }
    }

    public static void load(Context context, String url, int width, ImageView imageView) {
        load(context, getRequestOptions(), url, 0, width, imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        load(context, getRequestOptions(), url, 0, 0, imageView);
    }

    public static void load(Context context, int res, ImageView imageView) {
        load(context, getRequestOptions(), null, res, 0, imageView);
    }


    public static boolean filterContext(Context context) {
        if (context == null) {
            return false;
        }

        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) context).isDestroyed()) {
                return false;
            }
        }

        return true;

    }

    private static RequestOptions getRequestOptions() {
        if (options == null) {
            synchronized (GlideUtil.class) {
                if (options == null) {
                    options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .fallback(R.drawable.ic_launcher_background).diskCacheStrategy(DiskCacheStrategy.ALL);
                }
            }
        }
        return options;
    }

}
