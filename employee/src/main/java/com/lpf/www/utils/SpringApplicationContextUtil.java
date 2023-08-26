package com.lpf.www.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @auther zzyy
 * @create 2023-06-28 17:41
 */
@Component
public class SpringApplicationContextUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext param) throws BeansException {
        applicationContext=param;
    }
    public static ApplicationContext getApplicationContext(){
        //System.out.println("**********************"+applicationContext.getApplicationName().toString());
        return applicationContext;
    }
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
}
