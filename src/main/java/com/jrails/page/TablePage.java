package com.jrails.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-12 14:20:52
 * 基于数据库的分页
 */
public class TablePage<T> extends Page<T> {
    // 每页最大记录数
    private int maxPageSize = 100;
	public static final String ASC = "asc";
	public static final String DESC = "desc";
    
	protected String orderBy = null;
	protected String order = ASC;

    private List<T> result;
    protected boolean autoCount = false;

	public TablePage() {}

	public TablePage(int pageSize) {
		this.pageSize = pageSize;
	}

	public TablePage(int pageSize, boolean autoCount) {
		this.setPageSize(pageSize);
		this.autoCount = autoCount;
	}

	public void setPageSize(int pageSize) {
        if (pageSize > this.maxPageSize) {
            this.pageSize = this.maxPageSize;
        } else {
		    this.pageSize = pageSize;
        }
	}

	/**
	 * 页内的数据列表.
	 */
	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * 获得排序字段,无默认值.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 是否已设置排序字段.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy);
	}

	/**
	 * 获得排序方向,默认为asc.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 *
	 * @param order 可选值为desc或asc.
	 */
	public void setOrder(String order) {
		if (ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order)) {
			this.order = order.toLowerCase();
		} else {
			this.order = "asc";
        }
	}

	/**
	 * 是否自动获取总页数,默认为false.
	 * 注意本属性仅于query by Criteria时有效,query by HQL时本属性无效.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
}
