package com.server.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpringContextHolder
 * @Description: 以静态变量保存Spring ApplicationContext,可在任意代码中取出ApplicationContext.
 * @Author yanlin
 * @Date 2019/12/5
 * @Version V1.0
 **/
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    /*
    从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return (T) applicationContext.getBean(name, clazz);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null)
            throw new IllegalStateException("applicationContext未注入Bean");
    }
}
