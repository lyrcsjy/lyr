package com.lyr.demo.provider;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @Author: lyr
 * @Description: 处理bean
 * @Date: 2020/03/12 6:00 下午
 * @Version: 1.0
 **/
@Order(0)
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;
    private static DefaultListableBeanFactory register;

    private ApplicationContextProvider(){}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取注册工厂
     * @return
     */
    public static DefaultListableBeanFactory getBeanFactory(){
        return getBeanFactory(context);
    }

    /**
     * 获取注册工厂，context可能为空，所以单独提供一个方法获取
     * @return
     */
    public static DefaultListableBeanFactory getBeanFactory(ApplicationContext context){
        if(register == null){
            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
            register =  (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        }
        return register;
    }

    /**
     * 返回bean
     * @param clazz
     * @param <T>
     * @return
     */
    public  static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    /**
     * 根据className获取bean
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T getBeanByClassName(@NonNull String className){
        try {
            return (T) getBean(ClassUtils.forName(className,ClassUtils.getDefaultClassLoader()));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 返回bean
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(@NonNull String name){
        return (T) context.getBean(name);
    }

    /**
     * 注册bean
     * @param name 注册的bean的名称
     * @param clazz bean的className，
     *                     三种获取方式：1、直接书写，如：com.mvc.entity.User
     *                                   2、User.class.getName
     *                                   3.user.getClass().getName()
     * @param args 注册bean的构造参数
     */
    public static  <T> T registerBean(String name, Class<T> clazz, Object... args) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        if (args.length > 0) {
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

        getBeanFactory().registerBeanDefinition(name, beanDefinition);
        return getBean(name);
    }

    /**
     * 注册bean
     * @param name 注册的bean的名称
     * @param instanceObject 实例化的对象
     */
    public static  <T> T registerBean(String name, boolean isPrimary, @NonNull Object instanceObject) {

        if(getBeanFactory().containsBeanDefinition(name)){
            return getBean(name);
        }

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(instanceObject.getClass());

        //注册属性值
        Field[] fields = instanceObject.getClass().getFields();
        Arrays.stream(fields).forEach(field -> {
            try {
                field.setAccessible(true);
                beanDefinitionBuilder.addPropertyValue(field.getName(), field.get(instanceObject));
            } catch (IllegalAccessException e) { }
        });

        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        beanDefinition.setPrimary(isPrimary);
        getBeanFactory().registerBeanDefinition(name, beanDefinition);
        return getBean(name);
    }

    /**
     * 移除bean
     * @param beanId bean的id
     */
    public static void removeBean(String beanId){
        getBeanFactory().removeBeanDefinition(beanId);
    }

}