package com.muzi.easychat.frequencycontrol.service.frequencycontrol.strategy;



import com.muzi.easychat.frequencycontrol.FrequencyControlConstant;
import com.muzi.easychat.frequencycontrol.annotation.FrequencyControl;
import com.muzi.easychat.frequencycontrol.domain.dto.FixedWindowDTO;
import com.muzi.easychat.frequencycontrol.service.frequencycontrol.AbstractFrequencyControlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 抽象类频控服务 -使用redis实现 固定时间内不超过固定次数的限流类
 */
@Slf4j
@Service
public class TotalCountWithInFixTimeFrequencyController extends AbstractFrequencyControlService<FixedWindowDTO> {


    /**
     * 是否达到限流阈值 子类实现 每个子类都可以自定义自己的限流逻辑判断
     *
     * @param frequencyControlMap 定义的注解频控 Map中的Key-对应redis的单个频控的Key Map中的Value-对应redis的单个频控的Key限制的Value
     * @return true-方法被限流 false-方法没有被限流
     */
    @Override
    protected boolean reachRateLimit(Map<String, FixedWindowDTO> frequencyControlMap) {
        //批量获取redis统计的值
        List<String> frequencyKeys = new ArrayList<>(frequencyControlMap.keySet());
        List<Integer> countList = com.abin.mallchat.utils.RedisUtils.mget(frequencyKeys, Integer.class);
        for (int i = 0; i < frequencyKeys.size(); i++) {
            String key = frequencyKeys.get(i);
            Integer count = countList.get(i);
            int frequencyControlCount = frequencyControlMap.get(key).getCount();
            if (Objects.nonNull(count) && count >= frequencyControlCount) {
                //频率超过了
                log.warn("frequencyControl limit key:{},count:{}", key, count);
                return true;
            }
        }
        return false;
    }

    /**
     * 增加限流统计次数 子类实现 每个子类都可以自定义自己的限流统计信息增加的逻辑
     *
     * @param frequencyControlMap 定义的注解频控 Map中的Key-对应redis的单个频控的Key Map中的Value-对应redis的单个频控的Key限制的Value
     */
    @Override
    protected void addFrequencyControlStatisticsCount(Map<String, FixedWindowDTO> frequencyControlMap) {
        frequencyControlMap.forEach((k, v) -> com.abin.mallchat.utils.RedisUtils.inc(k, v.getTime(), v.getUnit()));
    }

    @Override
    protected String getStrategyName() {
        return FrequencyControlConstant.TOTAL_COUNT_WITH_IN_FIX_TIME;
    }

    @Override
    protected FixedWindowDTO buildAbstactFrequencyControlDTO(String key, FrequencyControl frequencyControl) {
        FixedWindowDTO fixedWindowDTO = new FixedWindowDTO();
        fixedWindowDTO.setCount(frequencyControl.count());
        fixedWindowDTO.setTime(frequencyControl.time());
        fixedWindowDTO.setUnit(frequencyControl.unit());
        fixedWindowDTO.setKey(key);
        return fixedWindowDTO;
    }
}
