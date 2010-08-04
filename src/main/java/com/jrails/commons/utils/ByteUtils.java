/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jrails.commons.utils;

/**
 * 字节操作工具类
 * @author wayan
 */
public class ByteUtils {

    /**
     * 将int转为低字节在前，高字节在后的byte数组
     * 
     * @param n
     * @return
     */
    public static byte[] intToByte(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 将short转为低字节在前，高字节在后的byte数组(网络字节)
     * 
     * @param n
     * @return
     */
    public static byte[] shortToByte(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 将long转为低字节在前，高字节在后的byte数组
     * 
     * @param n
     * @return
     */
    public static byte[] longToByte(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        b[4] = (byte) (n >> 32 & 0xff);
        b[5] = (byte) (n >> 40 & 0xff);
        b[6] = (byte) (n >> 48 & 0xff);
        b[7] = (byte) (n >> 56 & 0xff);
        return b;
    }

    /**
     * 解包Int算法
     * 
     * @param b
     * @return
     */
    public static int byteToInt(byte[] b) {
        int iOutcome = 0;
        byte bLoop;
        for (int i = 0; i < 4; i++) {
            bLoop = b[i];
            iOutcome += (bLoop & 0xff) << (8 * i);
        }
        return iOutcome;
    }

    /**
     * 解包short算法
     * 
     * @param b
     * @return
     */
    public static short byteToShort(byte[] b) {
        short iOutcome = 0;
        byte bLoop;
        for (int i = 0; i < 2; i++) {
            bLoop = b[i];
            iOutcome += (bLoop & 0xff) << (8 * i);
        }
        return iOutcome;
    }

    /**
     * 将字节数组转换成字符
     * @param b
     * @param encode
     * @return
     */
    public static String byteToString(byte[] b, String encode) {
        // 转化为Unicode编码格式
        String retStr = "";
        try {
            retStr = new String(b, encode);

        } catch (Exception e) {
        }
        return retStr.trim();
    }
    
    /**
     * 将字节数组转换成字符
     * @param b
     * @return
     */
    public static String byteToString(byte[] b) {
        // 转化为Unicode编码格式
        String retStr = "";
        try {
            retStr = new String(b);

        } catch (Exception e) {
        }
        return retStr.trim();
    }    

    /**
     * 字符转换成字节数组
     * @param str
     * @param encode
     * @return
     */
    public static byte[] stringToByte(String str, String encode) {
        byte[] retBytes = null;
        try {
            retBytes = str.getBytes(encode);
        } catch (Exception ex) {
        }
        return retBytes;
    }

        /**
     * 字符转换成字节数组
     * @param str
     * @return
     */
    public static byte[] stringToByte(String str) {
        byte[] retBytes = null;
        try {
            retBytes = str.getBytes();
        } catch (Exception ex) {
        }
        return retBytes;
    }

    /**
     * 拷贝一个字节维数组到另外一个字节数组
     * @param from  源
     * @param to  目标
     * @param fromStart  目标的起始位置
     * @param toStart  源的起始位置
     */    
    public static void bytesCopy(byte from[], byte to[], int fromStart, int fromEnd, int toStart) {
        int count = 0;
        for (int temp = fromStart; temp < fromEnd; temp++) {
            to[toStart + count] = from[temp];
            count++;
        }
    }
    
    /**
     * 拷贝一个字节维数组到另外一个字节数组
     * @param from  源
     * @param to  目标
     * @param toStart  源的起始位置
     */    
    public static void bytesCopy(byte from[], byte to[], int toStart) {
        int count = 0;
        for (int temp = 0; temp < from.length; temp++) {
            to[toStart + count] = from[temp];
            count++;
        }
    }    

    /**
     * 拷贝一个字节维数组到另外一个字节数组
     * @param from  源
     * @param to  目标
     * @param fromStart  目标的起始位置
     * @param toStart  源的起始位置
     */    
    public static void bytesCopy(byte from[], byte to[], int fromStart, int toStart) {
        int count = 0;
        for (int temp = fromStart; temp < from.length; temp++) {
            to[toStart + count] = from[temp];
            count++;
        }
    }
    
    public static void main(String... args) {
        
    }
}