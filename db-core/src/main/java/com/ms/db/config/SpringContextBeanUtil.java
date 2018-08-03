package com.ms.db.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取IOC容器中的bean
 *
 * @author lushaonan
 */
@Component
public class SpringContextBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public SpringContextBeanUtil(ApplicationContext applicationContext) {

        SpringContextBeanUtil.applicationContext = applicationContext;
    }

    public SpringContextBeanUtil() {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextBeanUtil.applicationContext == null) {
            SpringContextBeanUtil.applicationContext = applicationContext;
        }
    }

    private static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext 注入失败！");
        }
        return applicationContext;
    }

    public static Object getBean(String name) {
        try {
            return getApplicationContext().getBean(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static void cleanApplicationContext() {
        applicationContext = null;
    }

}
