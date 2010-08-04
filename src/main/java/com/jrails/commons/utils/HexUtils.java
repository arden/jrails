/* HexUtil.java
 * --------------------------------------
 * CREATED ON Jun 20, 2006 6:22:39 PM
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

/**
 * 十六进制转换工具类
 * <p/>
 * Date: Apr 29, 2006 2:49:21 PM
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public class HexUtils {
    /**
     * 将字节转换成16进制
     *
     * @param b byte[]
     * @return String
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
	}
}
