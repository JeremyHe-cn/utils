package me.zlv.utils;

import android.text.TextUtils;

/**
 * 字符串工具类
 * Created by jeremyhe on 2017/5/6.
 */

public class StringUtils {
    private StringUtils() {
        // do nothing
    }

    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    public static boolean isNotEmpty(String s) {
        return !TextUtils.isEmpty(s);
    }
}
