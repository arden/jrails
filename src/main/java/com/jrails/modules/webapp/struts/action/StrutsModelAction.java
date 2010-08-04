/* --------------------------------------
 * CREATED ON 2007-11-30 11:18:18
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.webapp.struts.action;

import com.opensymphony.xwork2.ModelDriven;
import com.jrails.commons.utils.GenericsUtils;
import com.jrails.modules.orm.model.Entity;

/**
 * 基于模型驱动的Action
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings({ "serial", "unchecked" })
public class StrutsModelAction<T extends Entity, PK extends java.io.Serializable> extends StrutsAction<T, PK> implements ModelDriven<T> {

    protected T model;

    public T getModel() {
        Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
        try {
            if (this.model == null) {
                this.model = entityClass.newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return model;
    }
}
