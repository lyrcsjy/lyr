package com.lyr.demo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: lyr
 * @Description: 缓存枚举的值到枚举缓存工具类【EnumCacheUtils】中,如在属性上增加这个注解，可将值缓存到：EnumCacheUtils，类似枚举中的code属性的值。
 * @Date: 2020/03/12 9:36 下午
 * @Version: 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CacheEnumValue {

}