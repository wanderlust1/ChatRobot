package com.wanderlust.chattingrobot_mvp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;


/**
 * 各种工具方法
 */
public class Utils {

    /** 单位转换，将dp转成px **/
    public static int dpToPx(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }

}
