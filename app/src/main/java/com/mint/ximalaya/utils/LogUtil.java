package com.mint.ximalaya.utils;

import android.util.Log;

public class LogUtil {
    public static String sTag = "LogUtil";
    public static boolean sIsRelease = false;
    public static void init(String bastTag, boolean isRelease){
        sTag = bastTag;
        sIsRelease = isRelease;
    }

    public static void d(String TAG, String content){
        if (!sIsRelease){
            Log.d("[" + sTag + "]" + TAG,  content);
        }
    }
    public static void v(String TAG, String content){
        if (!sIsRelease){
            Log.d("[" + sTag + "]" + TAG,  content);
        }
    }

    public static void i(String TAG, String content){
        if (!sIsRelease){
            Log.d("[" + sTag + "]" + TAG,  content);
        }
    }

    public static void w(String TAG, String content){
        if (!sIsRelease){
            Log.d("[" + sTag + "]" + TAG,  content);
        }
    }

    public static void e(String TAG, String content){
        if (!sIsRelease){
            Log.d("[" + sTag + "]" + TAG,  content);
        }
    }

}
