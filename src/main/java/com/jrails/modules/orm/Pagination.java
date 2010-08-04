/* --------------------------------------
 * CREATED ON 2007-11-23 15:29:30
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

import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class Pagination<T> implements Serializable {
    protected final Log logger = LogFactory.getLog(this.getClass());
    // 一共有多少条记录
    private int totalCount = 0;

    // 每页显示多少条记录
    private int count = 20;

    // 当前第几页
    private int currentPage = 1;

    // 记录集
    private List<T> pageResult;
    private int totalPage = 1;
    private boolean hasNextPage = false;
    private boolean hasPrePage = false;

    public Pagination() {
    }

    public Pagination(int currentPage, int count) {
        this.setCount(count);
        this.setCurrentPage(currentPage);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < 1) {
            this.count = 20;
        } else {
            this.count = count;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置当前页
     * 
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage <= 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
    }

    /**
     * 是否还有下一页
     * 
     * @return
     */
    public boolean hasNextPage() {
        this.hasNextPage = this.currentPage < this.getTotalPage() ? true : false;
        return this.hasNextPage;
    }

    /**
     * 是否还有上一页
     * 
     * @return
     */
    public boolean hasPrePage() {
        this.hasPrePage = this.currentPage > 1 ? true : false;
        return this.hasPrePage;
    }

    /**
     * 获得取记录的起始位置
     * 
     * @return
     */
    public int getStartIndex() {
        int startIndex = (this.currentPage - 1) * count;
        return startIndex;
    }

    /**
     * 获得总的页数
     * 
     * @return
     */
    public int getTotalPage() {
        this.totalPage = this.totalCount / count;
        if ((totalPage * count) < this.totalCount || this.totalCount == 0) {
            this.totalPage++;
        }
        return this.totalPage;
    }

    /**
     * 返回一页的记录集
     * 
     * @return
     */
    public List<T> getPageResult() {
        return pageResult;
    }

    /**
     * 设置一页里面的记录集
     * 
     * @param pageResult
     */
    public void setPageResult(List<T> pageResult) {
        this.pageResult = pageResult;
    }
}