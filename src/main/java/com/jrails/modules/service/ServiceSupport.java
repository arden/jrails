/* --------------------------------------
 * CREATED ON 2007-11-30 14:51:06
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.service;

import com.jrails.commons.utils.GenericsUtils;
import com.jrails.modules.orm.EntityManager;
import com.jrails.modules.orm.model.Entity;
import com.jrails.modules.orm.dao.JrailsDao;
import com.jrails.page.TablePage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

/**
 * 业务逻辑管理（供自己写业务管理类的时候公用的基类）
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings("unchecked")
public abstract class ServiceSupport<T extends Entity, PK extends Serializable> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected EntityManager<T> entityManager;

    protected JrailsDao<T, PK> dao;

    public EntityManager<T> getEntityManager() {
        return entityManager;
    }

    public JrailsDao<T, PK> getDao() {
        return dao;
    }
    
    @Autowired
    public void setEntityManager(@Qualifier("entityManager")EntityManager<T> entityManager) {
        this.entityManager = entityManager;
    }
    
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactory")SessionFactory sessionFactory) {
        Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
		dao = new JrailsDao<T, PK>(sessionFactory, entityClass);
	}
    
    public void update(T entity) {
    	this.entityManager.update(entity);
    }
    
    public Long generateId() {
        return this.entityManager.generateId();
    }

    public void setSequence(AbstractDataFieldMaxValueIncrementer sequence) {
        this.entityManager.setSequence(sequence);
    }

    /**
     * 批量保存实体对象.
     *
     * @param entities of transient instance of a persistent class
     */
    @SuppressWarnings("unchecked")
    public void batchSave(final Collection entities) {
        this.entityManager.batchSave(entities);
    }

    /**
     * 批量删除实体对象
     *
     * @param entities
     */
    @SuppressWarnings("unchecked")
    public void batchRemove(final Collection entities) {
        this.entityManager.batchRemove(entities);
    }

    /**
     * Update some of persistent instances with the identifier of each of the given detached
     * instances.
     *
     * @param entities detached instances containing updated state
     */
    @SuppressWarnings("unchecked")
    public void batchUpdate(final Collection entities) {
        this.entityManager.batchUpdate(entities);
    }

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param cacheable
     * @param values
     */
    public int executeByHql(final String hql, boolean cacheable, final Object... values) {
        return this.entityManager.executeByHql(hql, cacheable, values);
    }

    /**
     * 执行SQL语句
     *
     * @param sql
     * @param cacheable
     * @param values
     */
    public int executeBySql(final String sql, final boolean cacheable, final Object... values) {
        return this.entityManager.executeBySql(sql, cacheable, values);
    }

    /**
     * 根据HQL获得记录条数
     *
     * @param hql
     * @param values
     * @return
     */
    public int countByHql(final String hql, final boolean cacheable, final Object... values) {
        return this.entityManager.countByHql(hql, cacheable, values);
    }

    /**
     * 根据SQL获得记录条数
     *
     * @param sql
     * @param values
     * @return
     */
    public int countBySql(final String sql, final Object... values) {
        return this.countBySql(sql, values);
    }

    /**
     * 根据HQL语句分页查询
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    public TablePage<T> findByHql(final String hql, final TablePage<T> page, final boolean cacheable, final Object... values) {
        return this.entityManager.findByHql(hql, page, cacheable, values);
    }

    /**
     * 根据SQL语句分页查询
     * @param sql
     * @param cacheable
     * @param values
     * @return
     */
    public TablePage<T> findBySql(final String sql, final TablePage<T> page, final boolean cacheable, final Object... values) {
        return this.entityManager.findBySql(sql, page, cacheable, values);
    }

    /**
     * 根据DetachedCriteria分页查询
     * @param detachedCriteria
     * @param cacheable
     * @return
     */
    public TablePage<T> findByCriteria(final DetachedCriteria detachedCriteria, final TablePage<T> page, final boolean cacheable) {
        return this.entityManager.findByCriteria(detachedCriteria, page, cacheable);
    }

    /**
     * 根据属性查找
     * @param entityClass
     * @param propertyName
     * @param value
     * @param rowStartIdxAndCount
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<T> findByProperty(Class entityClass, String propertyName, final Object value, final boolean cacheable, final int... rowStartIdxAndCount) {
        return this.entityManager.findByProperty(entityClass, propertyName, value, cacheable, rowStartIdxAndCount);
    }

    /**
     * 查找所有记录
     * @param entityClass
     * @param page
     * @return
     */
    @SuppressWarnings("unchecked")
	public TablePage<T> findAll(Class entityClass, final TablePage<T> page, final boolean cacheable) {
        return this.entityManager.findAll(entityClass, page, cacheable);
    }

    /**
     * 查找第一个元素
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    public T findFirstByHql(final String hql, final boolean cacheable, final Object... values) {
        return this.entityManager.findFirstByHql(hql, cacheable, values);
    }

    /**
     * 根据SQL语句查询第一个元素
     * @param cacheable
     * @param values
     * @return
     */
    public T findFirstBySql(final String sql, final boolean cacheable, final Object... values) {
        return this.entityManager.findFirstBySql(sql, cacheable, values);
    }

    public List<T> findByHql(final String hql, final boolean cacheable, final Object... values) {
        return this.entityManager.findByHql(hql, cacheable, values);
    }

    public List<T> findBySql(final String sql, final boolean cacheable, final Object... values) {
        return this.entityManager.findBySql(sql, cacheable, values);
    }

    public List<T> findAll(Class entityClass, final boolean cacheable) {
        return this.entityManager.findAll(entityClass, cacheable);
    }

    /**
     * 获得指定多少条记录
     * @param hql
     * @param limit
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findLimitByHql(String hql, int limit, boolean cacheable, Object... values) {
        return this.entityManager.findLimitByHql(hql, limit, cacheable, values);
    }

    /**
     * 获得指定多少条记录
     * @param sql
     * @param limit
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findLimitBySql(String sql, int limit, boolean cacheable, Object... values) {
        return this.entityManager.findLimitBySql(sql, limit, cacheable, values);
    }    
}