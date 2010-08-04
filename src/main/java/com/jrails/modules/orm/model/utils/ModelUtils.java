package com.jrails.modules.orm.model.utils;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-17 20:41:56
 */
public class ModelUtils {
    /**
     * 根据模型对象生成HQL查询语句
     * @param theClass
     * @return
     */
    public static String toHql(Class theClass) {
        String className = theClass.getSimpleName();
        return "FROM " + className;
    }
}
