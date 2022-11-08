package com.cunjun.demo.business;

import com.cunjun.demo.constant.ResultConstants;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CunjunWang on 2021/1/26.
 */
public class ResultData extends HashMap<String, Object> {

    public ResultData() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    public static ResultData error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常, 请联系开发者");
    }

    public static ResultData error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static ResultData error(int code, String msg) {
        ResultData r = new ResultData();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultData ok(String msg) {
        ResultData r = new ResultData();
        r.put("msg", msg);
        return r;
    }

    public static ResultData ok(Map<String, Object> map) {
        ResultData r = new ResultData();
        r.putAll(map);
        return r;
    }

    public static ResultData ok() {
        return new ResultData();
    }

    @Override
    public ResultData put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public ResultData objectResult(Object value) {
        super.put(ResultConstants.OBJECT_RESULT_KEY, value);
        return this;
    }

    public ResultData booleanResult(Boolean value) {
        super.put(ResultConstants.BOOLEAN_RESULT_KEY, value);
        return this;
    }

    public ResultData listResult(List<?> value) {
        super.put(ResultConstants.LIST_RESULT_KEY, value);
        return this;
    }

    public ResultData integerResult(int value) {
        super.put(ResultConstants.TOTAL_KEY, value);
        return this;
    }

    public ResultData mapResult(Map<String, ?> result) {
        super.put("result", result);
        return this;
    }

}
