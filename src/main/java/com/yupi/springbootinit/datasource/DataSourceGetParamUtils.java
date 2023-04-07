package com.yupi.springbootinit.datasource;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wang
 * @create 2023-2023-22-21:09
 */
public class DataSourceGetParamUtils {
    static HttpServletRequest request;

    public static HttpServletRequest getRequest() {
        return request;
    }

    public static void setRequest(HttpServletRequest request) {
        DataSourceGetParamUtils.request = request;
    }
}
