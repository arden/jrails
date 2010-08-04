package com.jrails.commons.collections;

import java.util.Collection;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.EqualPredicate;

/**
 * 集合工具类
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
@SuppressWarnings("unchecked")
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {
    public static Object get(Collection collection, Object object) {
        Predicate predicate = new EqualPredicate(object);
        return find(collection, predicate);
    }
}