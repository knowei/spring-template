package com.liwen.project.common.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PageResponse<T> extends Response {
    private static final long serialVersionUID = -8450083830568857978L;
    protected Integer pageNo = 1;
    protected Integer pageSize = 1;
    protected Integer totalCount = 0;
    protected Collection<T> data;

    public PageResponse() {
    }

    public PageResponse(Integer pageNo, Integer pageSize) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public static PageResponse buildSuccess() {
        PageResponse response = new PageResponse();
        response.setCode("0");
        return response;
    }

    public static PageResponse buildFailure(String msg) {
        PageResponse response = new PageResponse();
        response.setCode("1");
        response.setMsg(msg);
        return response;
    }

    public static PageResponse buildFailure(String code, String msg) {
        PageResponse response = new PageResponse();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> PageResponse<T> of(Integer pageNo, Integer pageSize) {
        PageResponse<T> response = new PageResponse();
        response.setCode("0");
        response.setData(Collections.emptyList());
        response.setTotalCount(0);
        response.setPageSize(pageSize);
        response.setPageNo(pageNo);
        return response;
    }

    public static <T> PageResponse<T> of(Integer pageNo, Integer pageSize, Integer totalCount, Collection<T> data) {
        PageResponse<T> response = new PageResponse();
        response.setCode("0");
        response.setData(data);
        response.setTotalCount(totalCount);
        response.setPageSize(pageSize);
        response.setPageNo(pageNo);
        return response;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return this.pageSize != null && this.pageSize >= 1 ? this.pageSize : 1;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize >= 1) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = 30;
        }

    }

    public int getPageNo() {
        return this.pageNo != null && this.pageNo >= 1 ? this.pageNo : 1;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo != null && pageNo >= 1) {
            this.pageNo = pageNo;
        } else {
            this.pageNo = 1;
        }

    }

    public List<T> getData() {
        return (List)(null == this.data ? Collections.emptyList() : new ArrayList(this.data));
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public Integer getTotalPages() {
        if (this.totalCount != null && this.totalCount >= 1) {
            if (this.pageSize == null || this.pageSize < 1) {
                this.pageSize = 30;
            }

            return this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
        } else {
            return 0;
        }
    }

    public boolean responseIsEmpty() {
        return this.data == null || this.data.isEmpty();
    }

    public boolean responseIsNotEmpty() {
        return !this.responseIsEmpty();
    }

    public PageResponse<T> ofThis(Integer pageNo, Integer pageSize) {
        this.setCode("0");
        this.setData(Collections.emptyList());
        this.setTotalCount(0);
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
        return this;
    }

    public PageResponse<T> ofThis(Integer pageNo, Integer pageSize, Integer totalCount, Collection<T> data) {
        this.setCode("0");
        this.setData(data);
        this.setTotalCount(totalCount);
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
        return this;
    }
}
