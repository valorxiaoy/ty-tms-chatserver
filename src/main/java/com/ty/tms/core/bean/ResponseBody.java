package com.ty.tms.core.bean;

import com.alibaba.fastjson.JSONObject;

public class ResponseBody {

    private Integer code;

    private String message;

    private Object result = null;;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ResponseBody(Integer code, String message, Object result) {
        this.result = result;
        this.code = code;
        this.message = message;
    }

    public String getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        jsonObject.put("result", result);
        return jsonObject.toJSONString();
    }
}
