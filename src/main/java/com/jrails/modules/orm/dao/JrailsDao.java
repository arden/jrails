package com.jrails.modules.orm.dao;

import org.springside.modules.utils.BeanUtils;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

import com.jrails.page.TablePage;
import com.jrails.page.Page;
import com.jrails.commons.utils.SqlStringUtils;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-13 12:47:29
 */
public class JrailsDao<T, PK extends java.io.Serializable> extends HibernateTemplate {
    // 获得记录业务相关的日志记录器
    protected static final Logger logger = LoggerFactory.getLogger(JrailsDao.class);

    protected Class<T> entityClass;

    @Autowired
    public JrailsDao(@Qualifier("sessionFactory")SessionFactory sessionFactory, Class<T> entityClass) {
        super(sessionFactory);
        this.entityClass = entityClass;
    }

	public void delete(PK id) {
		Assert.notNull(id);
		this.delete(this.get(this.entityClass, id));
	}

	public List<T> findAll(boolean cacheable) {
		return findByCriteria(cacheable);
	}

	public TablePage<T> findAll(TablePage<T> page, boolean cacheable) {
		return findByCriteria(page, cacheable);
	}

	/**
	 * 按id获取对象.
	 */
	public T get(final PK id) {
		return (T) getSession().load(entityClass, id);
	}

	/**
	 * 按HQL查询对象列表.
	 *
	 * @param hql hql语句
	 * @param values 数量可变的参数
	 */
	public List find(String hql, boolean cacheable, Object... values) {
		return createQuery(hql, cacheable, values).list();
	}

	/**
	 * 按HQL分页查询.
	 * 暂不支持自动获取总结果数,需用户另行执行查询.
	 *
	 * @param page 分页参数.包括pageSize 和firstResult.
	 * @param hql hql语句.
	 * @param values 数量可变的参数.
	 *
	 * @return 分页查询结果,附带结果列表及所有查询时的参数.
	 */
	public TablePage<T> find(TablePage<T> page, String hql, boolean cacheable, Object... values) {
		Assert.notNull(page);

		if (page.isAutoCount()) {
			String countHql = SqlStringUtils.getCountString(hql);
			int totalCount = this.findLong(countHql, cacheable, values).intValue();
			page.setTotalCount(totalCount);
			//logger.warn("HQL查询暂不支持自动获取总结果数,hql为{}", hql);
		}
		Query q = createQuery(hql, cacheable, values);

		if (page.isFirstSetted()) {
			q.setFirstResult(page.getFirst());
		}
		if (page.isPageSizeSetted()) {
			q.setMaxResults(page.getPageSize());
		}
		page.setResult(q.list());
		return page;
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	public Object findUnique(String hql, boolean cacheable, Object... values) {
		return createQuery(hql, cacheable, values).uniqueResult();
	}

	/**
	 * 按HQL查询Intger类形结果.
	 */
	public Integer findInt(String hql, boolean cacheable, Object... values) {
		return (Integer) findUnique(hql, cacheable, values);
	}

	/**
	 * 按HQL查询Long类型结果.
	 */
	public Long findLong(String hql, boolean cacheable, Object... values) {
		return (Long) findUnique(hql, cacheable, values);
	}

	/**
	 * 按Criterion查询对象列表.
	 * @param criterion 数量可变的Criterion.
	 */
	public List<T> findByCriteria(boolean cacheable, Criterion... criterion) {
		return createCriteria(cacheable, criterion).list();
	}

	/**
	 * 按Criterion分页查询.
	 * @param page 分页参数.包括pageSize、firstResult、orderBy、asc、autoCount.
	 *             其中firstResult可直接指定,也可以指定pageNo.
	 *             autoCount指定是否动态获取总结果数.
	 *
	 * @param criterion 数量可变的Criterion.
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	public TablePage<T> findByCriteria(TablePage page, boolean cacheable, Criterion... criterion) {
		Assert.notNull(page);

		Criteria c = createCriteria(cacheable, criterion);

		if (page.isAutoCount()) {
			page.setTotalCount(countQueryResult(page, c));
		}
		if (page.isFirstSetted()) {
			c.setFirstResult(page.getFirst());
		}
		if (page.isPageSizeSetted()) {
			c.setMaxResults(page.getPageSize());
		}

		if (page.isOrderBySetted()) {
			if (page.getOrder().endsWith(TablePage.ASC)) {
				c.addOrder(Order.asc(page.getOrderBy()));
			} else {
				c.addOrder(Order.desc(page.getOrderBy()));
			}
		}
		page.setResult(c.list());
		return page;
	}

	/**
	 * 按属性查找对象列表.
	 */
	public List<T> findByProperty(String propertyName, boolean cacheable, Object value) {
		Assert.hasText(propertyName);
		return createCriteria(cacheable, Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 按属性查找唯一对象.
	 */
	public T findUniqueByProperty(String propertyName, boolean cacheable, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(cacheable, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
	 */
	public Query createQuery(String queryString, boolean cacheable, Object... values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (cacheable) {
			queryObject.setCacheable(cacheable);
		}
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	/**
	 * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
	 */
	public Criteria createCriteria(boolean cacheable, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (cacheable) {
			criteria.setCacheable(cacheable);
		}
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 *
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原值(orgValue)则不作比较.
	 * 传回orgValue的设计侧重于从页面上发出Ajax判断请求的场景.
	 * 否则需要SS2里那种以对象ID作为第3个参数的isUnique函数.
	 */
	public boolean isPropertyUnique(String propertyName, boolean cacheable, Object newValue, Object orgValue) {
		if (newValue == null || newValue.equals(orgValue))
			return true;

		Object object = findUniqueByProperty(propertyName, cacheable, newValue);
		return (object == null);
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * @return page对象中的totalCount属性将赋值.
	 */
	protected int countQueryResult(Page<T> page, Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) BeanUtils.getFieldValue(impl, "orderEntries");
			BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();
		if (totalCount < 1)
			return -1;

		// 将之前的Projection和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}

		try {
			BeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}
}