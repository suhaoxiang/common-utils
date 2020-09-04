package com.haoisou.common.utils;

import com.sun.istack.internal.Nullable;

public class StringUtil {
    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }
}
