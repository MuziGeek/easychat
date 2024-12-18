package com.muzi.easychat.frequencycontrol.aspect;


import cn.hutool.core.util.StrUtil;

import com.muzi.easychat.common.utils.RequestHolder;
import com.muzi.easychat.common.utils.SpElUtils;
import com.muzi.easychat.common.constant.FrequencyControlConstant;
import com.muzi.easychat.frequencycontrol.annotation.FrequencyControl;
import com.muzi.easychat.frequencycontrol.service.frequencycontrol.FrequencyControlUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 频控实现
 */
@Slf4j
@Aspect
@Component
public class FrequencyControlAspect {
    @Around("@annotation(com.muzi.easychat.frequencycontrol.annotation.FrequencyControl)||@annotation(com.muzi.easychat.frequencycontrol.annotation.FrequencyControlContainer)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        FrequencyControl[] annotationsByType = method.getAnnotationsByType(FrequencyControl.class);
        Map<String, FrequencyControl> keyMap = new HashMap<>();
        String strategy = FrequencyControlConstant.TOTAL_COUNT_WITH_IN_FIX_TIME;
        for (int i = 0; i < annotationsByType.length; i++) {
            // 获取频控注解
            FrequencyControl frequencyControl = annotationsByType[i];
            String prefix = StrUtil.isBlank(frequencyControl.prefixKey()) ? /* 默认方法限定名 + 注解排名（可能多个）*/method.toGenericString() + ":index:" + i : frequencyControl.prefixKey();
            String key = "";
            switch (frequencyControl.target()) {
                case EL:
                    key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), frequencyControl.spEl());
                    break;
                case IP:
                    key = RequestHolder.get().getIp();
                    break;
                case UID:
                    key = RequestHolder.get().getUid().toString();
            }
            keyMap.put(prefix + ":" + key, frequencyControl);
            strategy = frequencyControl.strategy();
        }
        return FrequencyControlUtil.executeWithFrequencyControlList(strategy, keyMap, joinPoint::proceed);

    }

}
