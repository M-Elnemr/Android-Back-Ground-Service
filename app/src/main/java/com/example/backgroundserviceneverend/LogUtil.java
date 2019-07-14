package com.example.backgroundserviceneverend;

import android.util.Log;

public class LogUtil {
    private static final String TAG = LogUtil.class.getSimpleName();

    public static void vebose(String message){
        Log.v(TAG, message);
    }
}
