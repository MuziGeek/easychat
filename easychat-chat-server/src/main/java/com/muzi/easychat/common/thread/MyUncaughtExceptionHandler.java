package com.muzi.easychat.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-02
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread",e);
    }
}
