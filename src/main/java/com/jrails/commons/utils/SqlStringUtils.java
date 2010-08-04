package com.jrails.commons.utils;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-14 9:42:00
 */
public class SqlStringUtils {
    /**
     * 获得统计查询的语句
     *
     * @param sql
     * @return
     */
    public static String getCountString(String sql) {
        int index = (" " + sql).toLowerCase().indexOf(" from ");
        String prefix = sql.substring(0, index);
        String select = "select count(*) ";
        if (!StringUtils.isEmpty(prefix)) {
            int index1 = prefix.toLowerCase().indexOf(" distinct");
            int index2 = prefix.indexOf(",");
            if (index1 > 0) {
                String distinct = "";
                if (index2 > 0 && index2 > index1) {
                    distinct = prefix.substring(index1, index2);
                } else {
                    distinct = prefix.substring(index1);
                }
                select = "select count(" + distinct + ") ";
            }
        }
        String countSql = select + (" " + sql).substring(index, (" " + sql).length());
        return countSql;
    }

    public static void main(String...args) {
        String sql = "select distinct  (a),b from Soft a, SoftFinder b WHERE a.id=b.softId and (b.isAllSeries=1 or b.isCommon=1) order by a.downloadCount desc";
        sql = sql.trim().toLowerCase();
        int index = (" " + sql).indexOf(" from ");
        String prefix = sql.substring(0, index);
        String select = "select count(*) ";
        if (!StringUtils.isEmpty(prefix)) {
            int index1 = prefix.indexOf(" distinct");
            int index2 = prefix.indexOf(",");
            if (index1 > 0) {
                String distinct = "";
                if (index2 > 0 && index2 > index1) {
                    distinct = prefix.substring(index1, index2);
                } else {
                    distinct = prefix.substring(index1);
                }
                System.out.println(distinct);
                select = "select count(" + distinct + ") ";
            }
        }
        String countSql = select + (" " + sql).substring(index, (" " + sql).length());
        System.out.println(countSql);
    }
}