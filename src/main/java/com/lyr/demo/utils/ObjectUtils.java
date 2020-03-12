package com.lyr.demo.utils;

import com.lyr.demo.response.ResponseException;
import com.lyr.demo.response.RestData;
import lombok.NonNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: lyr
 * @Description: 对象工具
 * @Date: 2020/03/12 9:23 下午
 * @Version: 1.0
 **/
public class ObjectUtils {

    /** 包装类型为Key，原始类型为Value，例如： Integer.class =》 int.class. */
    public static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new ConcurrentHashMap<>(8);

    static {
        wrapperPrimitiveMap.put(Boolean.class, boolean.class);
        wrapperPrimitiveMap.put(Byte.class, byte.class);
        wrapperPrimitiveMap.put(Character.class, char.class);
        wrapperPrimitiveMap.put(Double.class, double.class);
        wrapperPrimitiveMap.put(Float.class, float.class);
        wrapperPrimitiveMap.put(Integer.class, int.class);
        wrapperPrimitiveMap.put(Long.class, long.class);
        wrapperPrimitiveMap.put(Short.class, short.class);
    }

    /**
     * 是否为普通的类
     * @param clazz
     * @return
     */
    public static boolean isNormalClass(Class<?> clazz) {
        return null != clazz
                && false == clazz.isInterface()
                && false == isAbstract(clazz)
                && false == clazz.isEnum()
                && false == clazz.isArray()
                && false == clazz.isAnnotation()
                && false == clazz.isSynthetic()
                && false == clazz.isPrimitive();
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return 是否为基本类型
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }

        return wrapperPrimitiveMap.containsKey(clazz);
    }

    /**
     * 是否为抽象类
     *
     * @param clazz 类
     * @return 是否为抽象类
     */
    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }


    /**
     * 判断是否为Bean对象<br>
     * 判定方法是是否存在只有一个参数的setXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     */
    public static boolean isBean(Class<?> clazz) {
        if (isNormalClass(clazz)) {
            final Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 1 && method.getName().startsWith("set")) {
                    // 检测包含标准的setXXX方法即视为标准的JavaBean
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查null则抛出空指针异常
     * @param t
     * @param errorMessage 异常消息
     * @param <T>
     * @return
     */
    public static <T> T checkNullThrow(T t, @NonNull Object errorMessage){
        return Optional.ofNullable(t).orElseThrow(()->new NullPointerException(StrUtils.utf8Str(errorMessage)));
    }

    /**
     * 检查null则抛出异常
     * @param t
     * @param e
     * @param <T>
     * @return
     */
    public static <T,E extends Throwable> T checkNullThrow(T t, @NonNull E e) throws E{
        return Optional.ofNullable(t).orElseThrow(()->e);
    }


    /**
     * 返回对象或状态码
     * @param restData
     * @param details
     * @param <T>
     * @return
     */
    public static  <T> T get(Optional optional, RestData restData, Object...details){
        if(!optional.isPresent()) {
            ResponseException.fatal(restData, details);
        }
        return (T) optional.get();
    }

    /**
     * 返回对象或状态码
     * @param t
     * @param restData
     * @param details
     * @param <T>
     * @return
     */
    public static  <T> T get(T t, RestData restData, Object...details){
        Optional optional = Optional.ofNullable(t);
        if(!optional.isPresent()) {
            ResponseException.fatal(restData, details);
        }
        return (T) optional.get();
    }

    /**
     * 获取数据，如果存在就返回本身，不存在返回默认值
     * @param t
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getOrElse(T t,T defaultValue){
        return Optional.ofNullable(t).orElse(defaultValue);
    }
}