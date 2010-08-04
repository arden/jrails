package com.jrails.commons.utils;

import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-5-22
 * Time: 8:55:23
 * To change this template use File | Settings | File Templates.
 */
public class JavaScriptUtils {

    public static String encodeURIComponent(String s, String ENCODING) {

        // Encode URL

        try {
            s = URLEncoder.encode(s, ENCODING);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Adjust for JavaScript specific annoyances

        s = org.apache.commons.lang.StringUtils.replace(s, "+", "%20");
        s = org.apache.commons.lang.StringUtils.replace(s, "%2B", "+");

        return s;
    }
}
