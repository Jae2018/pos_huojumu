package com.huojumu.model;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description:
 */
public class BaseBean<T> {

    private String code;
    private T data;
    private String msg;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @author : Jie
     * Date: 2018/9/27
     * Description: 参数：eqpType
     */
    public static class EqpNo {

    }
}
