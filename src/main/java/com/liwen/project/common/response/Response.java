package com.liwen.project.common.response;


import com.liwen.project.common.utils.DateUtils;
import com.liwen.project.common.utils.ThreadLocals;
import com.liwen.project.exception.IBusinessException;

import java.io.Serializable;

/**
 * 返回实体类
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 5657580445315817582L;
    protected String code = "0";
    protected String msg;
    protected String errorMsg;
    protected String timestamp;
    protected String traceId;

    public Response() {
        this.timestamp = DateUtils.getCurrentTimeString();

        try {
            this.traceId = (String) ThreadLocals.get("traceId");
        } catch (Throwable var2) {
        }

    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(String code) {
        this.code = code;
    }

    public static Response buildFailure(String msg) {
        Response response = new Response();
        response.setCode("1");
        response.setMsg(msg);
        return response;
    }

    public static Response buildSuccess(String msg) {
        Response response = new Response();
        response.setCode("0");
        response.setMsg(msg);
        return response;
    }

    public static SingleResponse buildFailure(IBusinessException iBusinessException) {
        SingleResponse response = new SingleResponse();
        response.setCode(iBusinessException.getCode());
        response.setMsg(iBusinessException.getMsg());
        return response;
    }

    public boolean responseIsSuccess() {
        return "0".equals(this.code);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
