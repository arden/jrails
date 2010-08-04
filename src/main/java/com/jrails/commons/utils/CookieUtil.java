/* CookieUtil.java
 * --------------------------------------
 * CREATED ON Apr 16, 2006 2:30:41 AM
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Date: Apr 16, 2006 2:30:41 AM
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public class CookieUtil {
    /**
     * 获取Cookie参数值
     *
     * @param cookies
     * @param cookieName
     * @return String cookie值
     */
    public static String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName()))
                    return (cookie.getValue());
            }
        }
        return "";
    }

    /**
     * 获取Cookie参数值
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return CookieUtil.getCookieValue(request.getCookies(), cookieName);
    }
}
