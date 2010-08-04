/* --------------------------------------
 * CREATED ON 2007-11-23 15:23:10
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

/**
 * 模型基本对象
 * 
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
public abstract class BaseObject implements Serializable {
  @Override
  public abstract String toString();
  @Override
  public abstract boolean equals(Object o);
  @Override
  public abstract int hashCode();
}