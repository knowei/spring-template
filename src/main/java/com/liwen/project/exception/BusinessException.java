package com.liwen.project.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessException extends RuntimeException implements IBusinessException {
    public String code;
    public String msg;
    public Object[] args;
    Logger logger = LoggerFactory.getLogger(BusinessException.class);

    public BusinessException() {
    }

    public BusinessException(IBusinessException businessException) {
        this.msg = businessException.getMsg();
        this.code = businessException.getCode();
    }

    public BusinessException(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(Exception e) {
        this.logger.warn(e.getMessage(), e);
        this.code = "1";
    }

    public static BusinessException fail(String msg) {
        return new BusinessException("1", msg);
    }

    public static BusinessException fail(IBusinessException iBusinessException) {
        return new BusinessException(iBusinessException.getCode(), iBusinessException.getMsg());
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
    }
}

