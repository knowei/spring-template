package com.liwen.project.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PagingDto implements Serializable {
    private static final long serialVersionUID = 9002857638915156229L;
    protected Integer pageNo = 1;
    protected Integer offset;
    protected Integer pageSize = 30;
    /** @deprecated */
    @Deprecated
    protected String orderByType;
    /** @deprecated */
    @Deprecated
    protected String orderBy;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    protected Date startTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    protected Date endTime;
    private List<String> orderByAsc;
    private List<String> orderByDesc;

    public PagingDto() {
    }

    public String getOrderByType() {
        return this.orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }

    @JsonIgnore
    public Integer getOffset() {
        if (this.offset != null && this.offset >= 0) {
            return this.offset;
        } else if (this.pageNo != null && this.pageNo >= 1) {
            return this.pageNo != null && this.pageSize >= 1 ? (this.pageNo - 1) * this.pageSize : 0;
        } else {
            return 0;
        }
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonIgnore
    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @JsonIgnore
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonIgnore
    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void initPage() {
        if (this.pageNo == null || this.pageNo < 1) {
            this.pageNo = 1;
        }

        if (this.pageSize == null || this.pageSize < 1) {
            this.pageSize = 30;
        }

        this.offset = (this.pageNo - 1) * this.pageSize;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize != null && this.pageSize >= 1 ? this.pageSize : 30;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getOrderByAsc() {
        return this.orderByAsc;
    }

    public void setOrderByAsc(List<String> orderByAsc) {
        this.orderByAsc = orderByAsc;
    }

    public List<String> getOrderByDesc() {
        return this.orderByDesc;
    }

    public void setOrderByDesc(List<String> orderByDesc) {
        this.orderByDesc = orderByDesc;
    }
}

