package com.zclcs.cloud.lib.core.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 *
 * @author zclcs
 */
public class BasePage<T> implements IPage<T> {

    /**
     * 分页对象记录列表
     */
    @Getter
    protected List<T> list;

    /**
     * 总条数
     */
    protected Long total;

    /**
     * 每页显示条数
     */
    @Getter
    protected Long pageSize;

    /**
     * 当前页
     */
    @Getter
    protected Long pageNum;

    /**
     * 总页数
     */
    protected Long pages;

    public BasePage() {
        this.list = Collections.emptyList();
        this.total = 0L;
        this.pageSize = 10L;
        this.pageNum = 1L;
    }

    public BasePage(long pageNum, long pageSize) {
        this(pageNum, pageSize, 0L);
    }

    public BasePage(long pageNum, long pageSize, long total) {
        this.list = Collections.emptyList();
        this.total = total;
        this.pageSize = Math.max(pageSize, 1L);
        this.pageNum = pageNum;
    }

    @JsonIgnore
    @Override
    public List<OrderItem> orders() {
        return null;
    }

    @JsonIgnore
    @Override
    public List<T> getRecords() {
        return this.list;
    }

    @JsonIgnore
    @Override
    public BasePage<T> setRecords(List<T> records) {
        this.list = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public BasePage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @JsonIgnore
    @Override
    public long getSize() {
        return this.pageSize;
    }

    @JsonIgnore
    @Override
    public BasePage<T> setSize(long size) {
        this.pageSize = size;
        return this;
    }

    @JsonIgnore
    @Override
    public long getCurrent() {
        return this.pageNum;
    }

    @JsonIgnore
    @Override
    public IPage<T> setCurrent(long current) {
        this.pageNum = current;
        return this;
    }

    @Override
    public long getPages() {
        this.pages = IPage.super.getPages();
        return this.pages;
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
