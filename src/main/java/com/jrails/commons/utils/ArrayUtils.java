/* 项目名：eabar
 * 包  名：com.easou.commons.util
 * 文件名：ArraysUtil.java
 * --------------------------------------
 * CREATED ON Mar 24, 2006 11:05:33 AM
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组处理类
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public class ArrayUtils {

    private static ArrayUtils arrayUtil = null;

    public static ArrayUtils getInstance() {
        if (arrayUtil == null) {
            arrayUtil = new ArrayUtils();
        }        
        return arrayUtil;
    }

    /**
     * 根据一行显示多少列计算出有多少行
     *
     * @param records
     * @param cols
     * @return
     */
    @SuppressWarnings("unchecked")
    public static int getRows(List records, int cols) {
        int size = records.size();
        int rows = records.size() / cols;
        if ((rows * cols) < size || rows == 0) {
            rows = rows + 1;
        }
        return rows;
    }

    /**
     * 将指定的list类型转换成指定的行、列二维数组
     *
     * @param records
     * @param rows
     * @param cols
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[][] listToArrays(List records, int rows, int cols) {
        if (records == null) {
            return null;
        }
        Object[][] objects = new Object[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = (i * cols) + j;
                if (index == records.size()) {
                    break;
                }
                objects[i][j] = records.get(index);
            }
        }
        return objects;
    }

    /**
     * 将指定的list类型转换成指定的行、列二维数组
     *
     * @param records
     * @param records
     * @param cols
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[][] listToArrays(List records, int cols) {
        if (records == null) {
            return null;
        }
        int rows = getRows(records, cols);
        Object[][] objects = new Object[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = (i * cols) + j;
                if (index == records.size()) {
                    break;
                }
                objects[i][j] = records.get(index);
            }
        }
        return objects;
    }

    @SuppressWarnings("unchecked")
    public static void main(String... args) {
        List<String> records = new ArrayList();
        records.add("1");
        records.add("2");
        records.add("3");
        records.add("4");
        records.add("5");
        records.add("6");
        records.add("7");
        records.add("8");
        records.add("9");

        int cols = 5;
        int rows = ArrayUtils.getRows(records, cols);

        Object[][] objects = ArrayUtils.listToArrays(records, rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String s = (String) objects[i][j];
                if (s == null) {
                    break;
                }
                System.out.println((String) objects[i][j]);
            }
        }
    }
}
