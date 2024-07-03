package com.muzi.easychat.common.utils;

import com.muzi.easychat.common.domain.dto.RequestInfo;

/**
 * Description: 请求上下文
 * Author: muzi
 * Date: 2023-09-09
 */
public class RequestHolder {
    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<RequestInfo>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
