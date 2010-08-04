/* --------------------------------------
 * CREATED ON 2007-11-23 15:41:52
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.modules.webapp.action;

/**
 * 控制器接口
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
public interface ScaffoldAction {

    public static final String INDEX = "index";
    public static final String LIST = "list";
    public static final String UPDATE = "update";
    public static final String DESTROY = "destroy";
    public static final String SAVE = "save";
    public static final String SHOW = "show";

    public String index() throws Exception;

    public String save() throws Exception;

    public String show() throws Exception;

    public String update() throws Exception;
    
    public String destroy() throws Exception;

    public String list() throws Exception;
}