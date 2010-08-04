/* CharsetConvertor.java
 * --------------------------------------
 * CREATED ON Aug 10, 2007 11:23:42 AM
 *
 * MSN ardenemily@msn.com
 * QQ 83058327（太阳里的雪）
 * MOBILE 13590309275
 * BLOG http://www.ugole.com
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 字符集转换工具类
 *
 * @author <a href="mailto:arden.emily@gmail.com">arden</a>
 */
public class CharsetConvertor {
    public static void main(String[] args) {
        String str = "曹江华";
        try {            
            File f = new File("D:/test.txt");              
            FileOutputStream fio = new FileOutputStream(f);
            String s = gbToUtf8(str);            
            fio.write(s.getBytes("UTF-8"));
            fio.close();
        } catch (Exception e) {            
            e.printStackTrace();
        }
    }

    public static String gbToUtf8(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            if (s.charAt(0) > 0x80) {
                byte[] bytes = s.getBytes("Unicode");
                String binaryStr = "";
                for (int j = 2; j < bytes.length; j += 2) {
                    // the first byte
                    String hexStr = getHexString(bytes[j + 1]);
                    String binStr = getBinaryString(Integer.valueOf(hexStr, 16));
                    binaryStr += binStr;
                    // the second byte
                    hexStr = getHexString(bytes[j]);
                    binStr = getBinaryString(Integer.valueOf(hexStr, 16));
                    binaryStr += binStr;
                }
                // convert unicode to utf-8
                String s1 = "1110" + binaryStr.substring(0, 4);
                String s2 = "10" + binaryStr.substring(4, 10);
                String s3 = "10" + binaryStr.substring(10, 16);
                byte[] bs = new byte[3];
                bs[0] = Integer.valueOf(s1, 2).byteValue();
                bs[1] = Integer.valueOf(s2, 2).byteValue();
                bs[2] = Integer.valueOf(s3, 2).byteValue();
                String ss = new String(bs, "UTF-8");
                sb.append(ss);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    private static String getHexString(byte b) {
        String hexStr = Integer.toHexString(b);
        int m = hexStr.length();
        if (m < 2) {
            hexStr = "0" + hexStr;
        } else {
            hexStr = hexStr.substring(m - 2);
        }
        return hexStr;
    }

    private static String getBinaryString(int i) {
        String binaryStr = Integer.toBinaryString(i);
        int length = binaryStr.length();
        for (int l = 0; l < 8 - length; l++) {
            binaryStr = "0" + binaryStr;
        }
        return binaryStr;
    }
}
