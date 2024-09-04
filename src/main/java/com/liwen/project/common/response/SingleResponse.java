package com.liwen.project.common.response;

/**
 * 单个返回对象
 * @param <T>
 */
public class SingleResponse<T> extends Response {
    private static final long serialVersionUID = -3843550738081963438L;
    protected T data;

    public SingleResponse() {
    }

    public static SingleResponse buildSuccess() {
        SingleResponse response = new SingleResponse();
        response.setCode("0");
        return response;
    }

    public static SingleResponse buildFailure(String msg) {
        SingleResponse response = new SingleResponse();
        response.setCode("1");
        response.setMsg(msg);
        return response;
    }

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse();
        response.setCode("0");
        response.setData(data);
        return response;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

