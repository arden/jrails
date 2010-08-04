package com.jrails.page;

import org.springframework.web.util.HtmlUtils;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-12 13:47:54
 * 基于文章的分页
 */
public class TextPage<T> extends Page<T> {
    // 每页最大文字数
    private int maxPageSize = 5000;
    // 是否可以全文显示
    private boolean fullable = false;

    private boolean fullView = false;

    private String content;

    private String result;

    public TextPage(){}

	public TextPage(int pageSize) {
		this.pageSize = pageSize;
	}

	public TextPage(int pageSize, String content) {
		this.pageSize = pageSize;
        this.setContent(content);
	}

	public void setPageSize(int pageSize) {
        if (pageSize > this.maxPageSize) {
            this.pageSize = this.maxPageSize;
        } else {
		    this.pageSize = pageSize;
        }
	}

    public boolean isFullable() {
        return this.fullable;
    }
    
    public void setContent(String content) {
        this.content = content;
        this.totalCount = this.content.length();
        int totalPages = this.getTotalPages();
        if (this.pageNo > totalPages)  {
            this.pageNo = totalPages;
        }
    }

    public void parseContent() {
        // 计算是否可以显示全文
        if (this.pageNo < this.getTotalPages() && this.getTotalPages() > 2) {
            String tempContent = this.content.substring((this.pageNo) * this.pageSize);
            if (tempContent.length() <= this.maxPageSize) {
                this.fullable = true;
            }
        } else if (this.pageNo == this.getTotalPages() && this.getTotalPages() > 2) {
            this.fullable = true;
        }
        // 如果用户是显示全文并且可以显示全文的时候
        if (this.fullView && this.fullable) {
            this.result = this.content.substring((this.pageNo - 1) * this.pageSize);
            this.pageSize = this.maxPageSize;
        } else {
            if (this.totalCount > this.pageNo * this.pageSize) {
                this.result = this.content.substring((this.pageNo - 1) * this.pageSize, this.pageNo * this.pageSize);
            } else {
                this.result = this.content.substring((this.pageNo - 1) * this.pageSize);
            }
        }
    }

    public void parseContentWithEscape() {
        // 计算是否可以显示全文
        if (this.pageNo < this.getTotalPages() && this.getTotalPages() > 2) {
            String tempContent = this.content.substring((this.pageNo) * this.pageSize);
            if (tempContent.length() <= this.maxPageSize) {
                this.fullable = true;
            }
        } else if (this.pageNo == this.getTotalPages() && this.getTotalPages() > 2) {
            this.fullable = true;
        }
        // 如果用户是显示全文并且可以显示全文的时候
        if (this.fullView && this.fullable) {
            this.result = HtmlUtils.htmlEscape(this.content.substring((this.pageNo - 1) * this.pageSize));
            this.pageSize = this.maxPageSize;
        } else {
            if (this.totalCount > this.pageNo * this.pageSize) {
                this.result = HtmlUtils.htmlEscape(this.content.substring((this.pageNo - 1) * this.pageSize, this.pageNo * this.pageSize));
            } else {
                this.result = HtmlUtils.htmlEscape(this.content.substring((this.pageNo - 1) * this.pageSize));
            }
        }
    }

    /**
     * 获得当前分页的内容
     * @return
     */
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public void setFullView(boolean fullView) {
        this.fullView = fullView;
    }
    
    public static void main(String...args) {
        String content = "曹江华我很牛哦123456789abcdefghijklmnopqrst";
        TextPage<String> page = new TextPage<String>(2, content);
        for (int i = 0; i < 20; i++) {
            page.setPageNo(i);
            String pageContent = page.getResult();
            System.out.print("内容:" + pageContent + "|");
            System.out.print("当前页:" + page.getPageNo() + "|");
            System.out.println("一共多少页:" + page.getTotalPages());
        }
    }
}