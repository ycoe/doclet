package com.duoec.test.option;

/**
 * Created by ycoe on 16/1/29.
 */
public class HttpResult<T> {
    /**
     * 正常响应值
     * @demo 200
     */
    private int code = 200;

    /**
     * 信息,一般在错误时包含
     *  @demo 用户未登录
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T>HttpResult<T> successWithData(T o) {
        HttpResult<T> result = new HttpResult<T>();
        result.setData(o);
        return result;
    }
}
