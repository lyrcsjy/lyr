package com.lyr.demo.utils;

import com.google.common.collect.Maps;
import com.lyr.demo.annotations.CacheEnumValue;
import com.lyr.demo.exception.EnumDeserializeException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: lyr
 * @Description: 枚举缓存
 * @Date: 2020/03/12 9:35 下午
 * @Version: 1.0
 **/
@Slf4j
public class EnumCacheUtils {

    public static final String ERROR_MSG = "无法匹配枚举【{}】的值【{}】";
    public static final String ERROR_FULL_MSG = "无法匹配枚举【{}】的值【{}】，值只能为【{}】";
    public static final String GET = "get";
    /**
     * code属性名，用户转换为数据库的值
     */
    public static final String CODE = "code";
    /**
     * filter属性名，用于过滤枚举数据
     */
    public static final String FILTER = "filter";

    /**
     * 枚举的缓存
     */
    public static Map<String, Enum> CACHE = Maps.newConcurrentMap();

    /**
     * 枚举缓存映射，用于提供所有的枚举接口给前端
     * key为：枚举类名的首字母小写
     * value为：枚举的所有值
     */
    public static Map<String, Object[]> ENUMS = Maps.newConcurrentMap();

    /**
     * 添加到所有的枚举中，用于提供所有枚举给前端
     * @param enumClazz
     */
    public static void putToAllEnums(Class<?> enumClazz){
        ENUMS.put(StrUtils.firstToLowerCase(enumClazz.getSimpleName()), enumClazz.getEnumConstants());
    }

    /**
     * 将枚举过滤并返回给前端
     * 注：枚举中需要有filter属性，多个按[|]分隔
     * @param enumClazzName
     * @param filter
     * @return
     */
    public static Object[] filterEnums(String enumClazzName, String filter){
        if(!ENUMS.containsKey(enumClazzName)){
            return new Object[]{};
        }else {
            return Arrays.stream(ENUMS.get(enumClazzName)).filter(e->{
                Object filterValue = getFilterValue(e);
                if(filterValue.toString().contains(filter)){
                    return true;
                }else {
                    return false;
                }
            }).toArray();
        }
    }

    /**
     * 建立枚举缓存的数据
     * @param os
     * @param <E>
     */
    public static <E extends Enum> void build(Object[] os){
        Arrays.stream(os).forEach(o-> build((E)o));
    }

    /**
     * 建立枚举缓存的数据
     * @param es
     * @param <E>
     */
    public static <E extends Enum> void build(E...es){
        for(E e :es){
            //默认使用枚举的属性的code的值
            CACHE.put(e.getClass().getName() + ":" + getCodeValue(e).toString(), e);
            //添加属性上面存在注解：@CacheEnum的值
            Arrays.stream(e.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CacheEnumValue.class))
                    .forEach(field -> CACHE.put(e.getClass().getName() + ":" + getEnumPropertyValue(e, getMethodName(field.getName())), e));
        }
    }

    /**
     * 获取get方法
     * @param property
     * @return
     */
    private static String getMethodName(String property){
        return GET + StrUtils.firstToUpperCase(property);
    }

    /**
     * 获取枚举中code属性的值
     * @param e
     * @param <E>
     * @return
     */
    public static <E> Object getCodeValue(E e){
        return getEnumPropertyValue(e,getMethodName(CODE));
    }

    /**
     * 获取枚举中filter属性的值
     * @param e
     * @param <E>
     * @return
     */
    private static <E> Object getFilterValue(E e){
        return getEnumPropertyValue(e,getMethodName(FILTER));
    }

    /**
     * 获取值或枚举名称
     * @param e
     * @param <E>
     * @return
     */
    private static <E> Object getEnumPropertyValue(E e, String getMethodName){
        Method method;
        Object code = null;
        try {
            method = e.getClass().getMethod(getMethodName);
            code = method.invoke(e);
        }catch (IllegalAccessException e1) {

        } catch (NoSuchMethodException e1) {

        } catch (InvocationTargetException e1) {

        }
        if(code == null){
            code = ((Enum)e).name();
        }
        return code;
    }

    /**
     * 检查是否存在枚举
     * @param enumClass
     * @param value
     * @return
     */
    public static boolean hasEnum(Class<?> enumClass,Object value){
        if(value == null) {
            return false;
        }
        return CACHE.containsKey(enumClass.getName() + ":" + value);
    }

    /**
     * 检查是否包含枚举的名称
     * @param enumClass
     * @param name
     * @return
     */
    public static <E extends Enum> Optional<E> hasEnumName(Class<?> enumClass, Object name){
        if(name == null) {
            return Optional.empty();
        }
        return (Optional<E>) Arrays.stream(enumClass.getEnumConstants())
                .filter(e->((Enum)e).name().equals(name))
                .findFirst();
    }

    /**
     * 枚举不匹配时，获取枚举中可能的值，用于提示并输出到日志
     * @param enumClass
     * @return
     */
    public static String getPossibleEnumValueByClass(Class<?> enumClass){

        //获取所有name的名称
        List<String> possibleEnumValueList = Arrays.stream(enumClass.getEnumConstants())
                .map(e->((Enum)e).name())
                .collect(Collectors.toList());
        //获取缓存中所有的枚举值
        possibleEnumValueList.addAll(CACHE.keySet()
                .stream()
                .filter(key -> key.startsWith(enumClass.getName()) && key.contains(":"))
                .map(key->key.substring(key.lastIndexOf(":") + 1))
                .collect(Collectors.toList()));

        return possibleEnumValueList.stream().collect(Collectors.joining(","));

    }

    /**
     * 获取枚举
     * @param enumClass
     * @param value
     * @return
     */
    public static <E extends Enum> E get(Class<?> enumClass,Object value){
        if(value == null) {
            return null;
        }
        //先检查枚举的名称是否包含该名称
        Optional<E> existEnum = hasEnumName(enumClass,value);
        if(existEnum.isPresent()){
            return existEnum.get();
            //检查是否存在缓存值中
        } else if(hasEnum(enumClass,value)){
            return (E) CACHE.get(enumClass.getName() + ":" + value);
        } else{
            log.error(StrUtils.format(ERROR_MSG,enumClass.getName(),value));
            String errorMsg = StrUtils.format(
                    ERROR_FULL_MSG,
                    enumClass.getSimpleName(),
                    value,
                    getPossibleEnumValueByClass(enumClass));
            throw new EnumDeserializeException(errorMsg);
        }
    }
}

