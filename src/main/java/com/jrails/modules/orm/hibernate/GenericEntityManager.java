/* --------------------------------------
 * CREATED ON 2007-11-26 10:46:09
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.orm.hibernate;

import com.jrails.commons.utils.*;
import com.jrails.modules.orm.EntityManager;
import com.jrails.modules.orm.model.Entity;
import com.jrails.page.TablePage;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.lang.InstantiationException;

import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 泛型实体管理器
 *
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings("unchecked")
public abstract class GenericEntityManager<T extends Entity> extends HibernateTemplate implements EntityManager<T> {
    private AbstractDataFieldMaxValueIncrementer sequence;
    
    @Override
	public Serializable save(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.save(entity);
	}

	/**
     * 批量保存
     *
     * @param entities
     */
    @SuppressWarnings("unchecked")
    public void batchSave(final Collection entities) {

        this.execute(new HibernateCallback() {
        	
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                if (CollectionUtils.isNotEmpty(entities)) {
                    int max = CollectionUtils.size(entities);
                    int i = 0;
                    for (Object t : entities) {
                        session.save(t);
                        if ((i != 0 && i % CoreConstants.DEFAULT_BATCH_SIZE.getValue() == 0) || i == max - 1) {
                            session.flush();
                            session.clear();
                        }
                        i++;
                    }
                }
                return null;
            }
        });
    }

    /**
     * 批量删除
     *
     * @param entities
     */
    @SuppressWarnings("unchecked")
    public void batchRemove(final Collection entities) {
        this.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                if (CollectionUtils.isNotEmpty(entities)) {
                    int max = CollectionUtils.size(entities);
                    int i = 0;
                    for (Object t : entities) {
                        session.refresh(t);
                        session.delete(t);
                        if ((i != 0 && i % CoreConstants.DEFAULT_BATCH_SIZE.getValue() == 0) || i == max - 1) {
                            session.flush();
                            session.clear();
                        }
                        i++;
                    }
                }
                return null;
            }
        });
    }

    /**
     * 批量更新
     *
     * @param entities
     */
    @SuppressWarnings("unchecked")
    public void batchUpdate(final Collection entities) {
        this.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                if (CollectionUtils.isNotEmpty(entities)) {
                    int max = CollectionUtils.size(entities);
                    int i = 0;
                    for (Object t : entities) {
                        session.update(t);
                        if ((i != 0 && i % CoreConstants.DEFAULT_BATCH_SIZE.getValue() == 0) || i == max - 1) {
                            session.flush();
                            session.clear();
                        }
                        i++;
                    }
                }
                return null;
            }
        });
    }

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    public int executeByHql(final String hql, final boolean cacheable, final Object... values) {
        Integer count = (Integer)this.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                prepareQuery(query);
                if (cacheable) {
                    query.setCacheable(true);
                    //query.setCacheRegion("");
                }
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.executeUpdate();
            }
        });
        return count == null ? 0 : count.intValue();
    }

    /**
     * 执行SQL语句
     *
     * @param sql
     * @param cacheable
     * @param values
     * @return
     */
    public int executeBySql(final String sql, final boolean cacheable, final Object... values) {
        Integer count = (Integer)this.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                prepareQuery(query);
                if (cacheable) {
                    query.setCacheable(true);
                    //query.setCacheRegion("");
                }
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.executeUpdate();
            }
        });
        return count == null ? 0 : count.intValue();
    }

    /**
     * 根据指定的HQ语句查询记录条数
     *
     * @param hql
     * @param values
     * @return
     */
    public int countByHql(final String hql, final boolean cacheable, final Object... values) {
        Long count = (Long) this.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                prepareQuery(query);
                if (cacheable) {
                    query.setCacheable(cacheable);
                }
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.uniqueResult();
            }
        });
        return count == null ? 0 : count.intValue();
    }

    /**
     * 根据指定的SQL语句查询记录条数
     *
     * @param sql
     * @param values
     * @return
     */
    public int countBySql(final String sql, final boolean cacheable, final Object... values) {
        Long count = (Long) this.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createSQLQuery(sql).addScalar("count(*)", Hibernate.LONG);
                prepareQuery(query);
                if (cacheable) {
                    query.setCacheable(cacheable);
                }
                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.uniqueResult();
            }
        });
        return count == null ? 0 : count.intValue();
    }

    /**
     * 根据指定的属性查询
     *
     * @param propertyName
     * @param value
     * @param rowStartIdxAndCount
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> findByProperty(Class entityClass, String propertyName, final Object value, final boolean cacheable, final int... rowStartIdxAndCount) {
        String className = entityClass.getSimpleName();
        try {
            final String queryString = "from " + className + " where " + propertyName + "= :propertyValue";
            return (List<T>) execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createQuery(queryString);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    query.setParameter("propertyValue", value);
                    if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
                        int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
                        if (rowStartIdx > 0) {
                            query.setFirstResult(rowStartIdx);
                        }
                        if (rowStartIdxAndCount.length > 1) {
                            int rowCount = Math.max(0, rowStartIdxAndCount[1]);
                            if (rowCount > 0) {
                                query.setMaxResults(rowCount);
                            }
                        }
                    }
                    return (List<T>) query.list();
                }
            });
        } catch (RuntimeException re) {
            logger.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * 查询所有
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public TablePage<T> findAll(Class entityClass, final TablePage<T> page, final boolean cacheable) {
        final String className = entityClass.getSimpleName();
        logger.info("finding all " + className + " instances");
        try {
            final String queryString = "from " + className;
            return this.findByHql(queryString, page, cacheable);
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 根据指定的HQL查询
     *
     * @param hql
     * @param page
     * @param cacheable
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    public TablePage<T> findByHql(final String hql, final TablePage<T> page, final boolean cacheable, final Object... values) {
        try {
            return (TablePage<T>) this.execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    int startIndex = page.getFirst();
                    int count = page.getPageSize();
                    String countHql = SqlStringUtils.getCountString(hql);
                    int totalCount = countByHql(countHql, cacheable, values);
                    Query query = session.createQuery(hql);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    query.setFirstResult(startIndex);
                    query.setMaxResults(count);
                    page.setResult(query.list());
                    page.setTotalCount((int) totalCount);
                    return page;
                }
            });
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 根据HQL语句查询
     * @param hql
     * @param page
     * @param cacheable
     * @param countHql
     * @param values
     * @return
     */
    public TablePage<T> findByHql(final String hql, final String countHql, final boolean countBySql, final TablePage<T> page, final boolean cacheable, final Object... values) {
        try {
            return (TablePage<T>) this.execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    int startIndex = page.getFirst();
                    int count = page.getPageSize();
                    String theCountHql = countHql;
                    if (StringUtils.isEmpty(countHql)) {
                        theCountHql = SqlStringUtils.getCountString(hql);
                    }
                    int totalCount = 0;
                    if (countBySql) {
                        totalCount = countBySql(theCountHql, cacheable, values);    
                    } else {
                        totalCount = countByHql(theCountHql, cacheable, values);
                    }
                    Query query = session.createQuery(hql);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    query.setFirstResult(startIndex);
                    query.setMaxResults(count);
                    page.setResult(query.list());
                    page.setTotalCount((int) totalCount);
                    return page;
                }
            });
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 根据指定的SQL分页查询
     *
     * @param sql
     * @param page
     * @param cacheable
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    public TablePage<T> findBySql(final String sql, final TablePage<T> page, final boolean cacheable, final Object... values) {
        try {
            return (TablePage<T>) this.execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    int startIndex = page.getFirst();
                    int count = page.getPageSize();
                    String countHql = SqlStringUtils.getCountString(sql);
                    int totalCount = countBySql(countHql, cacheable, values);
                    Query query = session.createSQLQuery(sql);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    query.setFirstResult(startIndex);
                    query.setMaxResults(count);
                    page.setResult(query.list());
                    page.setTotalCount((int) totalCount);
                    return page;
                }
            });
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 根据Criteria查找
     *
     * @param detachedCriteria
     * @param page
     * @param cacheable
     * @return
     */
    @SuppressWarnings("unchecked")
    public TablePage<T> findByCriteria(final DetachedCriteria detachedCriteria, final TablePage<T> page, final boolean cacheable) {
        try {
            return (TablePage<T>) this.execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    int startIndex = page.getFirst();
                    int count = page.getPageSize();
                    Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                    if (cacheable) {
                        criteria.setCacheable(cacheable);
                    }
                    int totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                    page.setTotalCount(totalCount);
                    criteria.setProjection(null);
                    detachedCriteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
                    List records = criteria.setFirstResult(startIndex).setMaxResults(count).list();
                    page.setResult(records);
                    return page;
                }
            });
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 设置序列号生成器
     */
    public void setSequence(AbstractDataFieldMaxValueIncrementer sequence) {
        this.sequence = sequence;
    }

    /**
     * 根据HQL语句查找第一个元素
     *
     * @param hql
     * @param cacheable
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
	public T findFirstByHql(String hql, boolean cacheable, Object... values) {
        List<T> records = this.findByHql(hql, cacheable, values);
        return this.first(records);
    }

    /**
     * 根据SQL语句查找第一个元素
     *
     * @param sql
     * @param cacheable
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
	public T findFirstBySql(final String sql, final boolean cacheable, final Object... values) {
        List<T> records = this.findBySql(sql, cacheable, values);
        return this.first(records);
    }

    /**
     * 获得列表里的第一个元素
     *
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    protected T first(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (T) list.get(0);
    }
    
    /**
     * 根据指定的模型生成ID
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long generateId() { 
    	T model = null;
    	Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
        try {
            if (model == null) {
            	model = entityClass.newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }    	
        String sequenceName = model.getSequenceName();
        this.sequence.setIncrementerName(sequenceName);
        long id = this.sequence.nextLongValue();
        return id;
    }

    public List<T> findByHql(final String hql, final boolean cacheable, final Object... values) {
        try {
            return (List<T>) this.execute(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createQuery(hql);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    return query.list();
                }
            });
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    public List<T> findBySql(final String sql, final boolean cacheable, final Object... values) {
        try {
            return (List<T>) this.execute(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createSQLQuery(sql);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    return query.list();
                }
            });
        } catch (RuntimeException re) {
            re.printStackTrace();
            logger.error("find all failed", re);
            throw re;
        }
    }

    public List<T> findAll(Class entityClass, final boolean cacheable) {
        final String className = entityClass.getSimpleName();
        logger.info("finding all " + className + " instances");
        try {
            final String queryString = "from " + className;
            return this.findByHql(queryString, cacheable);
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 获得指定多少条记录
     * @param hql
     * @param limit
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findLimitByHql(final String hql, final int limit, final boolean cacheable, final Object... values) {
        try {
            return (List<T>) this.execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createQuery(hql);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    query.setFirstResult(0);
                    query.setMaxResults(limit);
                    return query.list();
                }
            });
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 获得指定多少条记录
     * @param sql
     * @param limit
     * @param cacheable
     * @param values
     * @return
     */
    public List<T> findLimitBySql(final String sql, final int limit, final boolean cacheable, final Object... values) {
        try {
            return (List<T>) this.execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createSQLQuery(sql);
                    prepareQuery(query);
                    if (cacheable) {
                        query.setCacheable(cacheable);
                    }
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            query.setParameter(i, values[i]);
                        }
                    }
                    query.setFirstResult(0);
                    query.setMaxResults(limit);
                    return query.list();
                }
            });
        } catch (RuntimeException re) {
            logger.error("find all failed", re);
            throw re;
        }
    }
}