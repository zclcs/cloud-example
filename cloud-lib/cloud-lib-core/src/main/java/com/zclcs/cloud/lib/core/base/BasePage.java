package com.zclcs.cloud.lib.core.base;

import com.mybatisflex.core.paginate.Page;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 *
 * @author zclcs
 */
@Getter
public class BasePage<T> {

    /**
     * 分页对象记录列表
     */
    protected List<T> list;

    /**
     * 总条数
     */
    protected Long total;

    /**
     * 每页显示条数
     */
    protected Long pageSize;

    /**
     * 当前页
     */
    protected Long pageNum;

    /**
     * 总页数
     */
    protected Long pages;

    public BasePage(Page<T> page) {
        this.pageNum = page.getPageNumber();
        this.pageSize = page.getPageSize();
        this.total = page.getTotalRow();
        this.pages = page.getTotalPage();
        this.list = page.getRecords();
    }

    public BasePage() {
        this.pageNum = 1L;
        this.pageSize = 10L;
        this.total = 0L;
        this.list = Collections.emptyList();
    }

    public BasePage(long pageNum, long pageSize) {
        this(pageNum, pageSize, 0L);
    }

    public BasePage(long pageNum, long pageSize, long total) {
        this.pageNum = pageNum;
        this.pageSize = Math.max(pageSize, 1L);
        this.total = total;
        this.list = Collections.emptyList();
    }

    public BasePage(Long pageNum, Long pageSize, Long total, Long pages, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.list = list;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }
}
