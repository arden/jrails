package com.jrails.modules.webapp.filter;

import com.jrails.modules.regex.OroRegex;
import com.jrails.commons.utils.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-5-27 18:01:59
 */
public class UrlRewriteFilter extends ReplaceFilter {
    /**
     * 重写网页里的链接给其加上我们想要的参数
     * @param responseString
     * @param servletRequest
     * @param servletResponse
     * @return
     */
    protected String doReplace(String responseString, ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String params = "";
        FilterConfig config = this.getFilterConfig();
        Enumeration keys = config.getInitParameterNames();
        for (; keys.hasMoreElements() ;) {
            String key = (String)keys.nextElement();
            String value = config.getInitParameter(key);
            if (StringUtils.isEmpty(value)) {
                value = request.getParameter(key.trim());
            }
            if (!StringUtils.isEmpty(value)) {
                if (!StringUtils.isEmpty(params)) {
                    params += "&amp;" + key + "=" + value;
                } else {
                    params += key + "=" + value;
                }
            }
        }
        if (!StringUtils.isEmpty(params)) {
            return OroRegex.parseWapContent(responseString, params);
        }
        return responseString;
    }

    public String getTargetString() {
        return null;
    }

    public String getReplacementString() {
        return null;
    }
}
