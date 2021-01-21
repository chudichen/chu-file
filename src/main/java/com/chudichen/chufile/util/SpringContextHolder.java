package com.chudichen.chufile.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author chudichen
 * @date 2021-01-21
 */
@Slf4j
@Service
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    /**
     * 取得存储在静态变量中的ApplicationContext
     *
     * @return Spring 上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 清除当前的ApplicationContext，设置为null
     */
    public static void clearHolder() {
        if (log.isDebugEnabled()) {
            log.debug("清除SpringContextHolder中的ApplicationContext: {}", applicationContext);
        }
        log.info("清除SpringContextHolder中的ApplicationContext: {}", applicationContext);
        applicationContext = null;
    }

    /**
     * 从静态变量applicationContext中取得Bean，并自动转为所需要的类型
     *
     * @param name beanName
     * @param <T> 类型
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean，并自动转为所需要的类型
     *
     * @param requiredType 类型
     * @param <T> 类型
     * @return bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 实现{@link DisposableBean}接口，在Context关闭时清理静态变量
     *
     * @throws Exception 异常
     */
    @Override
    public void destroy() throws Exception {
        clearHolder();
    }

}
