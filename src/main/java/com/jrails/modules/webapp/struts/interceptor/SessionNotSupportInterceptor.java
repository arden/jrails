package com.jrails.modules.webapp.struts.interceptor;

import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.ActionInvocation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-4-17 15:21:13
 */
public class SessionNotSupportInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        // 解决Struts2当浏览器不支持Cookie的时候在Action做redirect跳转的BUG，必须调用下面这行方法
        request.getSession();

        return actionInvocation.invoke();
    }
}
