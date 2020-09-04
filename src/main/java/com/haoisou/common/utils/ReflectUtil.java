package com.haoisou.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 反射工具类
 * @author qiandutianxia
 */
public class ReflectUtil {
    private final static String OBJECT_CLASS_NAME = "java.lang.object";

    public static List<Field> getAllFields(Class<?> clazz) {

        List<Field> fields = new ArrayList<>();
        Class currentClass = clazz;
        while (currentClass != null && !currentClass.getName().toLowerCase().equals(OBJECT_CLASS_NAME)) {

            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }

    /**
     * 获取模板类型
     *
     * @param clazz 类
     * @param index 模板序号(从0开始)
     * @return 返回模板类型
     */
    public static Class findTemplateClass(Class<?> clazz, int index) {
        if (null == clazz) {
            return null;
        }
        Type[] typeArguments = Optional.ofNullable(clazz.getGenericSuperclass())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> (ParameterizedType) t)
                .map(t -> t.getActualTypeArguments())
                .orElse(null);
        Class templateClass = Optional.ofNullable(typeArguments)
                .filter(t -> t[index] instanceof Class)
                .map(t -> (Class) t[index])
                .orElse(null);
        if (null != templateClass) {
            return templateClass;
        }
        return findTemplateClass(clazz.getSuperclass(), index);
    }

    /**
     * 获取所有类成员(包括父类)
     *
     * @param clazz 类
     * @return 返回所有类成员
     */
    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (null != clazz && !"java.lang.object".equals(clazz.getName().toLowerCase())) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取类成员类型
     *
     * @param fields    所有类成员
     * @param fieldName 类成员名
     * @return 返回类成员类型
     */
    private static String getFieldType(List<Field> fields, String fieldName) {
        String retValue = null;
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                retValue = field.getType().getSimpleName();
                break;
            }
        }
        return retValue;
    }

    /**
     * 字符串转换类成员值
     *
     * @param str  值字符串
     * @param type 类成员类型
     * @return 返回类成员值
     */
    private static Object getFieldValue(String str, String type) throws Exception {
        Object retValue = str;
        if (StringUtil.isEmpty(str)) {
            return null;
        } else if ("Date".equals(type)) {
            retValue = parseDate(str);
        } else if ("Integer".equals(type)) {
            retValue = Integer.valueOf(str);
        } else if ("Long".equals(type)) {
            retValue = Long.valueOf(str);
        } else if ("Double".equals(type)) {
            retValue = Double.valueOf(str);
        } else if ("Boolean".equals(type)) {
            retValue = Boolean.valueOf(str);
        } else if ("BigDecimal".equals(type)) {
            retValue = new BigDecimal(str);
        }
        return retValue;
    }

    /**
     * 获取参数SQL字符
     *
     * @param object 参数
     * @return SQL字符
     */
    public static String getSqlStr(Object object) {
        String retValue = " ";
        if (object instanceof String) {
            return "'" + object + "'";
        } else if (object instanceof Date) {
            return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object) + "'";
        } else if (object instanceof Integer) {
            return String.valueOf(object);
        } else if (object instanceof Long) {
            return String.valueOf(object);
        } else if (object instanceof Double) {
            return String.valueOf(object);
        } else if (object instanceof Boolean) {
            return String.valueOf(object);
        } else if (object instanceof BigDecimal) {
            return object.toString();
        } else if (object instanceof List) {
            for (Object o : (List) object) {
                retValue += getSqlStr(o) + ",";
            }
            retValue = retValue.substring(0, retValue.length() - 1);
        }
        return retValue;
    }

    /**
     * 添加引号
     *
     * @param str 参数
     * @return 引号字符
     */
    public static String addQuotation(String str) {
        return "'" + str + "'";
    }

    /**
     * 请求转换Bean
     *
     * @param request 当前请求
     * @param clazz   类
     * @return 转换后的Bean对象
     */
    public static <T> T requestToBean(HttpServletRequest request, Class<T> clazz) {
        if (null == request) {
            return null;
        }
        // 获取页面请求所有的参数名称
        Enumeration<String> enumeration = request.getParameterNames();
        // 获取clazz所有成员
        List<Field> fields = getFields(clazz);
        T beanObj = null;
        try {
            beanObj = clazz.newInstance();
            while (enumeration.hasMoreElements()) {
                // 参数名称
                String propertyName = enumeration.nextElement();
                // 判断是否存在此属性
                String propertyType = getFieldType(fields, propertyName);
                if (null != propertyType) {
                    // 获取请求值
                    String propertyValue = request.getParameter(propertyName);
                    PropertyDescriptor pd = new PropertyDescriptor(propertyName, beanObj.getClass());
                    Method methodSet = pd.getWriteMethod();
                    methodSet.invoke(beanObj, getFieldValue(propertyValue, propertyType));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanObj;
    }

    /**
     * 格式化时间字符串
     *
     * @param str 时间字符串
     * @return 返回时间
     */
    private static Date parseDate(String str) throws Exception {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        String fmtStr;
        if (str.indexOf(':') > -1) {
            fmtStr = "yyyy-MM-dd HH:mm:ss";
        } else {
            fmtStr = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(fmtStr).parse(str);
    }

    /**
     * 取Bean的属性和值对应关系的MAP
     *
     * @param bean
     * @return Map
     */
    public static Map<String, Object> getFieldValueMap(Object bean) {
        Map<String, Object> valueMap = new HashMap<>();
        for (Field field : getAllFields(bean.getClass())) {
            try {
                field.setAccessible(true);
                Object fieldVal = field.get(bean);
                valueMap.put(field.getName() + "|" + field.getType().getName(), fieldVal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valueMap;
    }
}
