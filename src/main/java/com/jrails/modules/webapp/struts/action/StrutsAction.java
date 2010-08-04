/* --------------------------------------
 * CREATED ON 2007-11-23 15:34:21
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

import javax.servlet.http.HttpServletRequest;
import com.jrails.commons.utils.DateUtils;
import com.jrails.commons.utils.StringUtils;
import com.jrails.modules.orm.model.Entity;
import com.jrails.page.TablePage;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.ServletActionContext;
import org.springside.modules.web.struts2.SimpleActionSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Struts的基本Action
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings("unchecked")
public abstract class StrutsAction<T extends Entity, PK extends java.io.Serializable> extends SimpleActionSupport implements Preparable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected HttpServletRequest request = ServletActionContext.getRequest();
    //每页5项，自动查询计算总页数.
    protected TablePage<T> page = new TablePage<T>(10, true);      

    public TablePage<T> getPage() {
        return page;
    }

    public String getDate() {
        return DateUtils.getDate2();
    }

    public void prepare() throws Exception {
        // 当前页
        String currentPage = StringUtils.nullStringToEmptyString(this.request.getParameter("p"));
        if (StringUtils.isEmpty(currentPage)) {
            currentPage = "0";
        }
        // 按什么字段排序
        String orderBy = StringUtils.nullStringToEmptyString(this.request.getParameter("o"));
        // 每页显示多少条记录
        String count = StringUtils.nullStringToEmptyString(this.request.getParameter("c"));
        if (StringUtils.isEmpty(count)) {
            count = "10";
        }
        // 排序方式（asc/desc)
        String orderByType = StringUtils.nullStringToEmptyString(this.request.getParameter("t"));
        if (StringUtils.isEmpty(orderByType)) {
            orderByType = "asc";
        }
        this.page.setPageNo(Integer.valueOf(currentPage));
        this.page.setOrder(orderByType);
        this.page.setPageSize(Integer.valueOf(count));
        this.page.setOrderBy(orderBy);
    }
}