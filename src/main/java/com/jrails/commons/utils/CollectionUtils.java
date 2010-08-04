/* --------------------------------------
 * CREATED ON 2007-11-26 14:52:55
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 *
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings("unchecked")
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getFromUniqueCollection(Collection<T> c) {
        if (c == null || c.size() == 0) {
            return null;
        }
        if (isUniqueCollection(c)) {
            return (T) get(c, 0);
        } else {
            throw new RuntimeException("found more than one object in this collection, this collection size is: " + size(c));
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean isUniqueCollection(Collection c) {
        return (size(c) > 1) ? false : true;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> c) {
        if (c == null) {
            return null;
        } else {
            T[] result = (T[]) Array.newInstance(c.getClass().getComponentType(), c.size());
            int i = 0;
            for (T r : c) {
                result[i] = r;
                i++;
            }
            return result;
        }
    }

    /**
     * 随机获取一些记录
     *
     * @param number
     * @param list
     * @return
     */
    public static List getRandomRecords(int number, List list) {
        List resultList = new ArrayList();
        if (list != null && list.size() > 0) {
            int total = list.size();
            if (total <= number)
                return list;
            int index = (int) (Math.random() * (double) (total - number + 1));
            for (int i = 0; i < number; i++)
                resultList.add(list.get(index + i));
        }
        return resultList;
	}
}