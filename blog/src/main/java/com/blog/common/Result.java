package com.blog.common;

import lombok.Data;

@Data
public class Result {

    private String msg;
    private int code;
    private Object data;

    public static Result succ(String msg, int code, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result succ(Object data) {
        return succ("执行成功",200,data);
    }

    public static Result fail(String msg) {
        return fail(msg,400,null);
    }

    public static Result fail(String msg,int code) {
        return fail(msg,code,null);
    }

    public static Result fail(String msg, int code, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

}
