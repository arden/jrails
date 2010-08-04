/* DefaultEntity.java
 * --------------------------------------
 * CREATED ON 2006-8-12 下午06:23:34
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.orm.model;

import com.jrails.commons.utils.GenericsUtils;
import com.jrails.modules.orm.BaseObject;
import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 实体对象（封装了一些数据库操作的方法）
 *
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@MappedSuperclass
public abstract class Entity<T, PK extends Serializable> extends BaseObject implements Serializable {
    protected PK id;
    
    @SuppressWarnings("unchecked")
    @Transient
    public String getClassName() {
        Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
        return entityClass.getSimpleName();
    }

    /**
     * 主键生成的序列名字
     * @return
     */
    @Transient
    public abstract String getSequenceName();

    @Transient
    public abstract PK getId();

    public abstract void setId(PK id);

    public boolean equals(Object o) {        
        return false;
    }

    public int hashCode() {
        return 0;
    }

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
