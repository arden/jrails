package com.jrails.modules.webapp.struts.action;

import com.jrails.modules.orm.model.Entity;
import com.jrails.commons.utils.FileUtils;

import java.io.InputStream;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-16 13:23:31
 */
public class DownloadAction<T extends Entity, PK extends java.io.Serializable> extends StrutsAction<T, PK> {
    // 文件的具体URL地址
    protected String fileUrl;
    // 文件名
    protected String fileName;
    // 文件的mime类型
    protected String contentType;

    /**
     * 获得输入流
     * @return
     * @throws java.lang.Exception
     */
    public InputStream getInputStream() throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = FileUtils.readUrl(this.fileUrl);
        } catch (Exception e) {
            if (inputStream != null) {
                inputStream.close();
            }
            e.printStackTrace();
        }
        return inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }    
}