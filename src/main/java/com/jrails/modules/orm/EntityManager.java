/* --------------------------------------
 * CREATED ON 2007-11-26 10:43:26
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.orm;

import com.jrails.modules.orm.model.Entity;
import com.jrails.page.TablePage;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import org.springframework.orm.hibernate3.HibernateOperations;

/**
 * 实体管理器
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings("unchecked")
public interface EntityManager<T extends Entity> extends HibernateOperations {

    @SuppressWarnings("unchecked")
    public Long generateId();

    void setSequence(AbstractDataFieldMaxValueIncrementer sequence);

    /**
     * 批量保存实体对象.
     * 
     * @param entities of transient instance of a persistent class
     */
    @SuppressWarnings("unchecked")
    void batchSave(final Collection entities);

    /**
     * 批量删除实体对象
     * 
     * @param entities
     */
    @SuppressWarnings("unchecked")
    void batchRemove(final Collection entities);

    /**
     * Update some of persistent instances with the identifier of each of the given detached
     * instances.
     *
     * @param entities detached instances containing updated state
     */
    @SuppressWarnings("unchecked")
    void batchUpdate(final Collection entities);

    /**
     * 执行HQL语句
     * 
     * @param hql
     * @param cacheable
     * @param values
     */
    public int executeByHql(final String hql, boolean cacheable, final Object... values);

    /**
     * 执行SQL语句
     * 
     * @param sql
     * @param cacheable
     * @param values
     */
    public int executeBySql(final String sql, final boolean cacheable, final Object... values);

    /**
     * 根据HQL获得记录条数
     * 
     * @param hql
     * @param values
     * @return
     */
    public int countByHql(final String hql, boolean cacheable, final Object... values);

    /**
     * 根据SQL获得记录条数
     * 
     * @param sql
     * @param values
     * @return
     */
    public int countBySql(final String sql, final boolean cacheable, final Object... values);

    /**
     * 根据HQL语句分页查询
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    public TablePage<T> findByHql(final String hql, final TablePage<T> page, final boolean cacheable, final Object... values);

    /**
     * 根据HQL语句分页查询（同时指定总记录统计查询语句）
     * @param hql
     * @param page
     * @param cacheable
     * @param countHql
     * @param countBySql 是否统计总记录条数用SQL来统计
     * @param values
     * @return
     */
    public TablePage<T> findByHql(final String hql, final String countHql, boolean countBySql, final TablePage<T> page, final boolean cacheable, final Object... values);

    /**
     * 根据SQL语句分页查询
     * @param sql
     * @param cacheable
     * @param values
     * @return
     */
    public TablePage<T> findBySql(final String sql, final TablePage<T> page, final boolean cacheable, final Object... values);

    /**
     * 根据DetachedCriteria分页查询
     * @param detachedCriteria
     * @param cacheable
     * @return
     */
    public TablePage<T> findByCriteria(final DetachedCriteria detachedCriteria, final TablePage<T> page, final boolean cacheable);

    /**
     * 根据属性查找
     * @param entityClass
     * @param propertyName
     * @param value
     * @param rowStartIdxAndCount
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<T> findByProperty(Class entityClass, String propertyName, final Object value, final boolean cacheable, final int... rowStartIdxAndCount);

    /**
     * 查找所有记录
     * @param entityClass
     * @param page
     * @return
     */
    @SuppressWarnings("unchecked")
	public TablePage<T> findAll(Class entityClass, final TablePage<T> page, final boolean cacheable);

    /**
     * 根据HQL语句分页查询
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findByHql(final String hql, final boolean cacheable, final Object... values);

    /**
     * 根据SQL语句分页查询
     * @param sql
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findBySql(final String sql, final boolean cacheable, final Object... values);

    /**
     * 查找所有记录
     * @param entityClass
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<T> findAll(Class entityClass, final boolean cacheable);

    /**
     * 查找第一个元素
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    public T findFirstByHql(final String hql, final boolean cacheable, final Object... values);

    /**
     * 根据SQL语句查询第一个元素
     * @param sql
     * @param cacheable
     * @param values
     * @return
     */
    public T findFirstBySql(final String sql, final boolean cacheable, final Object... values);

    /**
     * 查找第一个元素
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findLimitByHql(final String hql, final int limit, final boolean cacheable, final Object... values);

    /**
     * 根据SQL语句查询第一个元素
     * @param sql
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findLimitBySql(final String sql, final int limit, final boolean cacheable, final Object... values);
}
