/* --------------------------------------
 * CREATED ON 2007-11-23 15:35:25
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Spring业务查找器
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
public class ServiceLocator implements BeanFactoryAware {

    protected static final Log logger = LogFactory.getLog(ServiceLocator.class);
    private static BeanFactory beanFactory = null;
                                                              
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        beanFactory = factory;
    }

    public static Object getBean(String beanName) {
        if (beanFactory != null) {
            return beanFactory.getBean(beanName);
        }
        return null;
    }

    public static Object getDao(String daoName) {
        return getBean(daoName);
    }

    public static Object getService(String serviceName) {
        return getBean(serviceName);
    }
}