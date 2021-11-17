package com.suny.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: suny
 * @Date: 2021/11/17 下午9:57
 * @Description:
 */
@Data
public class Result implements Serializable {
    /**
     * 200 正常
     * 非200 异常
     */
    private int code;

    /**
     *信息
     */
    private String msg;

    /**
     *数据
     */
    private Object data;

    public static Result succ(int code,String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result succ(Object data){
        return succ(200,"操作成功",data);
    }
    public static Result fail(int code,String msg,Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result fail(String msg,Object data){
        return fail(400,msg,data);
    }
    //方法重载
    public static Result fail(String msg){
        return fail(400,msg,null);
    }
}
