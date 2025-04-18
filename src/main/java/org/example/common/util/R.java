package org.example.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 规范返回结果
 * @author: zpy
 * @Date: 2025/4/9
 */
public class R extends HashMap<String, Object> {

    public R() {
        put("code", "success");
        put("msg", "success");
    }

    public static R error(Integer code ,String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(){
        return error(500,"服务器未知异常");
    }

    public static R ok(String msg){
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg,String data){
        R r = new R();
        r.put("msg", msg);
        r.put("data", data);
        return r;
    }

    public static R ok(){
       return new R();
    }

    public static R ok(Map<String,Object> data){
        R r = new R();
        r.putAll(data);
        return r;
    }

    public static R error(String message) {
        R r = new R();
        r.put("code", 500);
        r.put("msg", message);
        return r;
    }

    public R put(String key, Object value){
        super.put(key, value);
        return this;
    }

    public R setData(Object data){
        this.put("data", data);
        return this;
    }
}
