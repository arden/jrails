/* --------------------------------------
 * CREATED ON 2007-11-26 15:32:44
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.caojianghua.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

/**
 * 常量
 *
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
public enum CoreConstants {
    DEFAULT_BATCH_SIZE(30),
    DEFAULT_PAGE_SIZE(20);
    private int value;

    CoreConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}