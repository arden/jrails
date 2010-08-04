/*
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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * ID实体
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@MappedSuperclass
public abstract class IdEntity<T, PK extends Serializable> extends Entity<T,PK> {
    
    protected PK id;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    @Override
    @Transient
    public String getSequenceName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}