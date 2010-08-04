/* --------------------------------------
 * CREATED ON 2007-11-30 11:16:55
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

import com.jrails.modules.orm.model.Entity;
import com.jrails.modules.webapp.action.ScaffoldAction;

/**
 * 基于模型驱动和CRUD操作的Action
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings({ "serial", "unchecked" })
public class StrutsScaffoldAction<T extends Entity, PK extends java.io.Serializable> extends StrutsModelAction<T, PK> implements ScaffoldAction {

    public String index() throws Exception {        
        return INDEX;
    }

    @SuppressWarnings("unchecked")
	public String save() throws Exception {
        return SAVE;
    }

    @SuppressWarnings("unchecked")
	public String show() throws Exception {
       return SHOW;
    }

    public String update() throws Exception {
        return UPDATE;
    }

    public String destroy() throws Exception {
        return DESTROY;
    }

    public String list() throws Exception {
        return LIST;
    }

}