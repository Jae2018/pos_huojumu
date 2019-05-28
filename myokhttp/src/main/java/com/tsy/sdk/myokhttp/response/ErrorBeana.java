package com.tsy.sdk.myokhttp.response;

public class ErrorBeana {
    /**
     * code : 31301
     * msg : 存在未交班订单,不能进行日结
     * data :
     */

    private String code;
    private String msg;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
