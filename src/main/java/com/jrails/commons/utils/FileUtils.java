package com.jrails.commons.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件处理类
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public class FileUtils {
    /**
     * 读取网络文件
     *
     * @param urlName
     * @return
     * @throws java.lang.Exception
     */
    public static InputStream readUrl(String urlName) throws Exception {
        int result = 0;
        URL url = new URL(urlName);
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        result = httpConn.getResponseCode();
        if (result != HttpURLConnection.HTTP_OK) {
            return null;
        } else {
            BufferedInputStream bis = new BufferedInputStream(urlConn.getInputStream());
            return bis;
        }
    }

    /**
     * 获得网络文件并写入本地文件
     * @param synurl
     * @param filename
     */
    public static void getUrlFileAndWriteToFile(String synurl, String filename) {
        if (!StringUtils.isEmpty(synurl)) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(synurl);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                int code = response.getStatusLine().getStatusCode();
                if (code == 200) {
                    HttpEntity entity = response.getEntity();
					if (entity != null) {
						InputStream inputStream = entity.getContent();
						if (inputStream != null) {
                            byte[] oBuff = new byte[1024];
                            int iSize;
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            FileOutputStream fos = new FileOutputStream(new File(filename));
                            while (-1 != (iSize = inputStream.read(oBuff))) {
                                baos.write(oBuff, 0, iSize);
                            }
                            baos.writeTo(fos);
                            inputStream.close();
                            baos.close();
                            fos.close();
						}
					}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解压zip文件
     * @param destdir
     * @param zipfile
     */
    public static void unzip(String destdir, String zipfile) {
        try {
            File file = new File(zipfile);
            if (file.exists()) {
                String filename = FileUtils.getFilename(file.getName());
                String thedestdir = destdir + "/" + filename;
                if (StringUtils.isEmpty(destdir)) {
                    thedestdir = FileUtils.getFilename(zipfile);
                    System.out.println("destdir:" + thedestdir);
                }
                InputStream is = new FileInputStream(file);
                ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream("zip", is);
                for (;;) {
                    ZipArchiveEntry entry = (ZipArchiveEntry)in.getNextEntry();
                    if (entry != null) {
                        File destFile = new File(thedestdir);
                        if (!destFile.exists()) {
                            destFile.mkdirs();
                        }
                        OutputStream out = new FileOutputStream(new File(thedestdir, entry.getName()));
                        IOUtils.copy(in, out);
                        out.close();
                    } else {
                        break;
                    }
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得文件名
     * 
     * @param filename
     * @return
     */
    public static String getFilename(String filename) {
        int index = filename.lastIndexOf(".");
        return filename.substring(0, index);
    }

    public static void main(String...args) {
        FileUtils.getUrlFileAndWriteToFile("http://wap.dtmp3.cn/wml/music/down.jsp?mid=35763&s=0", "d:/test.mp3");
    }
}