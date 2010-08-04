package com.jrails.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-12 14:24:50
 * 基于List的内存分页
 */
public class ListPage<T> extends TablePage<T> {
    // 要分页的所有记录
    private List<T> results;
    // 是否全部显示
    private boolean fullView = false;

    public ListPage() {
    }

    public ListPage(int pageSize) {
        this.pageSize = pageSize;
    }

    public ListPage(int pageSize, List<T> results) {
        this.pageSize = pageSize;
        this.setResults(results);
    }

    public void setResults(List<T> results) {
        this.results = results;
        this.totalCount = this.results.size();
        int totalPages = this.getTotalPages();
        if (this.pageNo > totalPages) {
            this.pageNo = totalPages;
        }
    }

    /**
     * 获得当前分页的内容
     *
     * @return
     */
    public List<T> getResult() {
        if (this.results != null && this.results.size() > 0) {
            if (!this.fullView) {
                if (this.totalCount > this.pageNo * this.pageSize) {
                    return this.results.subList((this.pageNo - 1) * this.pageSize, this.pageNo * this.pageSize);
                } else {
                    return this.results.subList((this.pageNo - 1) * this.pageSize, this.totalCount);
                }
            } else {
                this.setPageSize(this.results.size());
                return this.results;
            }
        }
        return null;
    }

    public void setFullView(boolean fullView) {
        this.fullView = fullView;
    }

    public boolean getFullView() {
        return fullView;
    }

    public static void main(String... args) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            String s = "A" + i;
            results.add(s);
        }

        ListPage<String> page = new ListPage<String>(2, results);

        for (int j = 0; j < 10; j++) {
            page.setPageNo(j);
            List<String> records = page.getResult();
            System.out.print("内容:" + records + "|");
            System.out.print("当前页:" + page.getPageNo() + "|");
            System.out.println("一共多少页:" + page.getTotalPages());
        }
    }
}