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
package com.jrails.modules.orm.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 命名实体
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@MappedSuperclass
public abstract class NameEntity<T, PK extends Serializable> extends Entity<T,PK> {
    protected String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Transient
    public String getSequenceName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}