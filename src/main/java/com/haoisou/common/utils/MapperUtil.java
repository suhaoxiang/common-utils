//package com.haoisou.common.utils;
//
//import java.beans.BeanInfo;
//import java.beans.IntrospectionException;
//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class MapperUtil {
//    /**
//     * 合并两个对象属性值，以overrideObj为准，但跳过为null的属性
//     *
//     * @param orgObj      源对象
//     * @param overrideObj 覆盖者对象
//     * @param <T>         泛型类型
//     */
//    public static <T, D> void mergeIfNotNull(T orgObj, D overrideObj) {
//        if (orgObj == null || overrideObj == null) {
//            return;
//        }
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(orgObj.getClass());
//            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
//            Map<String, Object> valueMap = ReflectUtil.getFieldValueMap(overrideObj);
//            for (PropertyDescriptor descriptor : descriptors) {
//
//                if (descriptor.getWriteMethod() != null) {
//                    Object overrideValue = valueMap.get(descriptor.getName() + "|" + descriptor.getPropertyType().getName());
//                    if (overrideValue != null) {
//                        descriptor.getWriteMethod().invoke(orgObj, overrideValue);
//                    }
//                }
//            }
//        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 合并两个对象属性值，以overrideObj为准，但跳过为null的属性。忽略值
//     *
//     * @param orgObj           源对象
//     * @param overrideObj      覆盖者对象
//     * @param ignoreProperties 忽略的值
//     * @param <T>              泛型类型
//     */
//    public static <T, D> void mergeIfNotNull(T orgObj, D overrideObj, String... ignoreProperties) {
//        if (orgObj == null || overrideObj == null) {
//            return;
//        }
//
//        try {
//            List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
//
//            BeanInfo beanInfo = Introspector.getBeanInfo(orgObj.getClass());
//            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
//            Map<String, Object> valueMap = ReflectUtil.getFieldValueMap(overrideObj);
//
//            for (PropertyDescriptor descriptor : descriptors) {
//                Method writeMethod = descriptor.getWriteMethod();
//
//                boolean notContainsIgnore = (ignoreList == null || !ignoreList.contains(descriptor.getName()));
//                if (writeMethod != null && notContainsIgnore) {
//                    Object overrideValue = valueMap.get(descriptor.getName() + "|" + descriptor.getPropertyType().getName());
//                    if (overrideValue != null) {
//                        descriptor.getWriteMethod().invoke(orgObj, overrideValue);
//                    }
//                }
//            }
//        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 根据对象创建DTO实体
//     *
//     * @param entity    实体
//     * @param dtoClass  dto实体
//     * @param <TEntity> 数据库对象实体类型
//     * @param <TDto>    dto对象类型
//     * @return dto对象
//     */
//    public static <TEntity extends Domain, TDto extends BaseEntityDTO<TEntity>> TDto createDto(TEntity entity, Class<TDto> dtoClass) {
//        try {
//            TDto dto = dtoClass.newInstance();
//            return (TDto) dto.toDto(entity);
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static String getBeanExitsPropertyType(Class<?> clazz, String propertyName) {
//        String retValue = null;
//        List<Field> list = ReflectUtil.getAllFields(clazz);
//        for (Field field : list) {
//            if (propertyName.equals(field.getName())) {
//                retValue = field.getGenericType().toString();
//                break;
//            }
//        }
//        return retValue;
//    }
//
//    private static Object getPropertyValue(String value, String type) throws Exception {
//        Object retValue = value;
//        if (!type.equals("class java.lang.String") && StringUtils.isEmpty(value)) {
//            return null;
//        }
//        if (type.equals("class java.lang.Integer")) {
//            retValue = Integer.valueOf(value);
//        }
//        if (type.equals("class java.lang.Double")) {
//            retValue = Double.valueOf(value);
//        }
//        if (type.equals("class java.lang.Boolean")) {
//            retValue = Boolean.valueOf(value);
//        }
//        if (type.equals("class java.util.Date")) {
//            retValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
//        }
//        return retValue;
//    }
//
//    public static <T> T requestToBean(HttpServletRequest request, Class<T> clazz) {
//        if (null == request) {
//            return null;
//        }
//        // 获取页面所有的请求参数名称
//        Enumeration<String> enumeration = request.getParameterNames();
//        T beanObj = null;
//        try {
//            beanObj = clazz.newInstance();
//            while (enumeration.hasMoreElements()) {
//                // 参数名称
//                String propertyName = enumeration.nextElement();
//                // 判断是否存在此属性
//                String propertyType = getBeanExitsPropertyType(clazz, propertyName);
//                if (null != propertyType) {
//                    // 获取请求值
//                    String propertyValue = request.getParameter(propertyName);
//                    PropertyDescriptor pd = new PropertyDescriptor(propertyName, beanObj.getClass());
//                    Method methodSet = pd.getWriteMethod();
//                    methodSet.invoke(beanObj, getPropertyValue(propertyValue, propertyType));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return beanObj;
//    }
//
//    /**
//     * 将list map转为list Javabean
//     *
//     * @param mapList 要转的map list
//     * @param bean    要转成的对象
//     * @param <T>     泛型类
//     * @return 返回转化后的Javabean对象
//     */
//    public static <T> List<T> castListMapToBean(List<Map<String, Object>> mapList, Class<T> bean) {
//        List<T> beanListReturn = new ArrayList<>();
//        if (mapList != null && mapList.size() > 0) {
//            for (Map<String, Object> mapTemp :
//                    mapList) {
//                T t = castMapToBean(mapTemp, bean);
//                beanListReturn.add(t);
//            }
//        }
//        return beanListReturn;
//    }
//
//    /**
//     * 将map转为Javabean
//     *
//     * @param map  要转的map
//     * @param bean 要转成的对象
//     * @param <T>  泛型类
//     * @return 返回转化后的Javabean对象
//     */
//    public static <T> T castMapToBean(Map<String, Object> map, Class<T> bean) {
//        T t = null;
//        try {
//            t = bean.newInstance();
//            BeanUtils.populate(t, map);
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return t;
//    }
//
//    /**
//     * 将下划线的map转为驼峰的map
//     *
//     * @param map 下划线map
//     * @return 驼峰map
//     */
//    public static Map<String, Object> mapUnderlineToCamel(Map<String, Object> map) {
//        Map<String, Object> newMap = new HashMap<>(16);
//        if (map != null && !map.isEmpty()) {
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                newMap.put(underlineToCamel(entry.getKey()), entry.getValue());
//            }
//        }
//
//        return newMap;
//    }
//
//    /**
//     * 将下划线的String转为驼峰string
//     *
//     * @param param 下划线参数
//     * @return 驼峰信息
//     */
//    private static String underlineToCamel(String param) {
//        final char underline = '_';
//        if (param == null || "".equals(param.trim())) {
//            return "";
//        }
//
//        if (!param.contains(String.valueOf(underline))) {
//            return param;
//        }
//
//        int len = param.length();
//        StringBuilder sb = new StringBuilder(len);
//        for (int i = 0; i < len; i++) {
//            char c = param.charAt(i);
//            if (c == underline) {
//                if (++i < len) {
//                    sb.append(Character.toUpperCase(param.charAt(i)));
//                }
//            } else {
//                sb.append(Character.toLowerCase(param.charAt(i)));
//            }
//        }
//        return sb.toString();
//    }
//}
