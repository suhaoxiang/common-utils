package com.haoisou.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertBeanUtil {
    /**
     * 将查询出来的list转为自定义的实体类
     *
     * @param list  查询出来的list
     * @param clazz 要转的类
     * @param <T>   泛型类c
     * @return 返回自定义的list
     */
    public static <T> List<T> convertListMapToBean(List<Map<String, Object>> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (Tools.notEmpty(list)) {
            for (Map<String, Object> map : list) {
                map = mapUnderlineToCamel(map);
                T t = convertMapToBean(map, clazz);
                result.add(t);
            }
        }

        return result;
    }

    /**
     * 将map转为Javabean
     *
     * @param map  要转的map
     * @param bean 要转成的对象
     * @param <T>  泛型类
     * @return 返回转化后的Javabean对象
     */
    public static <T> T convertMapToBean(Map<String, Object> map, Class<T> bean) {
        return JSONObject.parseObject(JSONObject.toJSONString(map), bean);
    }

    /**
     * 将下划线的map转为驼峰的map
     *
     * @param map 下划线map
     * @return 驼峰map
     */
    private static Map<String, Object> mapUnderlineToCamel(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>(16);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                newMap.put(underlineToCamel(entry.getKey()), entry.getValue());
            }
        }

        return newMap;
    }

    /**
     * 将下划线的String转为驼峰string
     *
     * @param param 下划线参数
     * @return 驼峰信息
     */
    private static String underlineToCamel(String param) {
        final char underline = '_';
        if (param == null || "".equals(param.trim())) {
            return "";
        }

        if (!param.contains(String.valueOf(underline))) {
            return param;
        }

        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == underline) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(Character.toLowerCase(param.charAt(i)));
            }
        }
        return sb.toString();
    }

    /**
     * 将驼峰String转为下划线string
     *
     * @param param 下划线参数
     * @return 驼峰信息
     */
    public static String camelToUnderline(String param) {
        final char underline = '_';
        if (param == null || "".equals(param.trim())) {
            return "";
        }

        final int len = param.length();
        final StringBuilder sb = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = param.charAt(i);
            final Character preChar = (i > 0) ? param.charAt(i - 1) : null;
            if (Character.isUpperCase(c)) {
                if (Character.isLowerCase(preChar)) {
                    sb.append(underline);
                }
                sb.append(Character.toLowerCase(c));
            } else {
                // 小写或符号
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
