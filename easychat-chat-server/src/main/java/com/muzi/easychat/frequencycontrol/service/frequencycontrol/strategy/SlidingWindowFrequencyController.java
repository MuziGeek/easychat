package com.muzi.easychat.frequencycontrol.service.frequencycontrol.strategy;


import com.muzi.easychat.common.constant.FrequencyControlConstant;
import com.muzi.easychat.frequencycontrol.annotation.FrequencyControl;
import com.muzi.easychat.frequencycontrol.domain.dto.SlidingWindowDTO;
import com.muzi.easychat.frequencycontrol.service.frequencycontrol.AbstractFrequencyControlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 抽象类频控服务 -使用redis实现 滑动窗口是一种更加灵活的频率控制策略，它在一个滑动的时间窗口内限制操作的发生次数
 */
@Slf4j
@Service
public class SlidingWindowFrequencyController extends AbstractFrequencyControlService<SlidingWindowDTO> {
    @Override
    protected boolean reachRateLimit(Map<String, SlidingWindowDTO> frequencyControlMap) {
        // 批量获取redis统计的值
        List<String> frequencyKeys = new ArrayList<>(frequencyControlMap.keySet());
        for (int i = 0; i < frequencyKeys.size(); i++) {
            String key = frequencyKeys.get(i);
            SlidingWindowDTO controlDTO = frequencyControlMap.get(key);
            // 获取窗口时间内计数
            Long count = com.abin.mallchat.utils.RedisUtils.ZSetGet(key);
            int frequencyControlCount = controlDTO.getCount();
            if (Objects.nonNull(count) && count >= frequencyControlCount) {
                //频率超过了
                log.warn("frequencyControl limit key:{},count:{}", key, count);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void addFrequencyControlStatisticsCount(Map<String, SlidingWindowDTO> frequencyControlMap) {
        List<String> frequencyKeys = new ArrayList<>(frequencyControlMap.keySet());
        for (int i = 0; i < frequencyKeys.size(); i++) {
            String key = frequencyKeys.get(i);
            SlidingWindowDTO controlDTO = frequencyControlMap.get(key);
            // 窗口最小周期转秒
            long period = controlDTO.getUnit().toMillis(controlDTO.getPeriod());
            long current = System.currentTimeMillis();
            // 窗口大小 单位 秒
            long length = period * controlDTO.getWindowSize();
            long start = current - length;
//            long expireTime = length + period;
            com.abin.mallchat.utils.RedisUtils.ZSetAddAndExpire(key, start, length, current);
        }
    }

    @Override
    protected String getStrategyName() {
        return FrequencyControlConstant.SLIDING_WINDOW;

    }


    /**
     * 将注解参数转换为编程式调用所需要的参数
     *
     * @param key              频率控制Key
     * @param frequencyControl 注解
     * @return 编程式调用所需要的参数-FrequencyControlDTO
     */
    @Override
    protected SlidingWindowDTO buildAbstactFrequencyControlDTO(String key, FrequencyControl frequencyControl){
        SlidingWindowDTO frequencyControlDTO = new SlidingWindowDTO();
        frequencyControlDTO.setWindowSize(frequencyControl.windowSize());
        frequencyControlDTO.setPeriod(frequencyControl.period());
        frequencyControlDTO.setCount(frequencyControl.count());
        frequencyControlDTO.setUnit(frequencyControl.unit());
        frequencyControlDTO.setKey(key);
        return frequencyControlDTO;
    }
}
