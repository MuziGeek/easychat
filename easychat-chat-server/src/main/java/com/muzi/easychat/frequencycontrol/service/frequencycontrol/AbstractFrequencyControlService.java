package com.muzi.easychat.frequencycontrol.service.frequencycontrol;


import com.muzi.easychat.frequencycontrol.annotation.FrequencyControl;
import com.muzi.easychat.frequencycontrol.domain.dto.FrequencyControlDTO;
import com.muzi.easychat.frequencycontrol.exception.CommonErrorEnum;
import com.muzi.easychat.frequencycontrol.exception.FrequencyControlException;
import com.muzi.easychat.frequencycontrol.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象类频控服务 其他类如果要实现限流服务 直接注入使用通用限流类 后期会通过继承此类实现令牌桶等算法
 *
 * @param <K>
 */
@Slf4j
public abstract class AbstractFrequencyControlService<K extends FrequencyControlDTO> {
    private Class<K> KClass;




    @PostConstruct
    protected void registerMyselfToFactory() {
        FrequencyControlStrategyFactory.registerFrequencyController(getStrategyName(), this);
            ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
            this.KClass = (Class<K>) genericSuperclass.getActualTypeArguments()[1];
    }

    /**
     * @param frequencyControlMap 定义的注解频控 Map中的Key-对应redis的单个频控的Key Map中的Value-对应redis的单个频控的Key限制的Value
     * @param supplier            函数式入参-代表每个频控方法执行的不同的业务逻辑
     * @return 业务方法执行的返回值
     * @throws Throwable
     */
    private <T> T executeWithFrequencyControlMap(Map<String, K> frequencyControlMap, SupplierThrowWithoutParam<T> supplier) throws Throwable {
        if (reachRateLimit(frequencyControlMap)) {
            throw new FrequencyControlException(CommonErrorEnum.FREQUENCY_LIMIT);
        }
        try {
            return supplier.get();
        } finally {
            //不管成功还是失败，都增加次数
            addFrequencyControlStatisticsCount(frequencyControlMap);
        }
    }


    /**
     * 多限流策略的编程式调用方法 无参的调用方法
     *
     * @param keyMap 频控注解集合
     * @param supplier             函数式入参-代表每个频控方法执行的不同的业务逻辑
     * @return 业务方法执行的返回值
     * @throws Throwable 被限流或者限流策略定义错误
     */
    @SuppressWarnings("unchecked")
    public <T> T executeWithFrequencyControlList(Map<String, FrequencyControl> keyMap, SupplierThrowWithoutParam<T> supplier) throws Throwable {
        List<K> frequencyControlList = buildFrequencyControlDTO(keyMap);
        boolean existsFrequencyControlHasNullKey = frequencyControlList.stream().anyMatch(frequencyControl -> ObjectUtils.isEmpty(frequencyControl.getKey()));
        AssertUtil.isFalse(existsFrequencyControlHasNullKey, "限流策略的Key字段不允许出现空值");
        Map<String, K> frequencyControlDTOMap = frequencyControlList.stream().collect(Collectors.groupingBy(K::getKey, Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0))));
        return executeWithFrequencyControlMap(frequencyControlDTOMap, supplier);
    }

    /**
     * 单限流策略的调用方法-编程式调用
     *
     * @param keyMap 频控注解集合
     * @param supplier         服务提供着
     * @return 业务方法执行结果
     * @throws Throwable
     */
    public <T> T executeWithFrequencyControl(Map<String, FrequencyControl> keyMap, SupplierThrowWithoutParam<T> supplier) throws Throwable {
        return executeWithFrequencyControlList(keyMap, supplier);
    }

    public List<K> buildFrequencyControlDTO(Map<String, FrequencyControl> keyMap) {
        List<K> frequencyControlList = keyMap.entrySet().stream().map(entrySet -> buildAbstactFrequencyControlDTO(entrySet.getKey(), entrySet.getValue())).collect(Collectors.toList());
        boolean existsFrequencyControlHasNullKey = frequencyControlList.stream().anyMatch(frequencyControl -> ObjectUtils.isEmpty(frequencyControl.getKey()));
        AssertUtil.isFalse(existsFrequencyControlHasNullKey, "限流策略的Key字段不允许出现空值");
        return frequencyControlList;
    }


    @FunctionalInterface
    public interface SupplierThrowWithoutParam<T> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Throwable;
    }

    @FunctionalInterface
    public interface Executor {

        /**
         * Gets a result.
         *
         * @return a result
         */
        void execute() throws Throwable;
    }

    /**
     * 是否达到限流阈值 子类实现 每个子类都可以自定义自己的限流逻辑判断
     *
     * @param frequencyControlMap 定义的注解频控 Map中的Key-对应redis的单个频控的Key Map中的Value-对应redis的单个频控的Key限制的Value
     * @return true-方法被限流 false-方法没有被限流
     */
    protected abstract boolean reachRateLimit(Map<String, K> frequencyControlMap);

    /**
     * 增加限流统计次数 子类实现 每个子类都可以自定义自己的限流统计信息增加的逻辑
     *
     * @param frequencyControlMap 定义的注解频控 Map中的Key-对应redis的单个频控的Key Map中的Value-对应redis的单个频控的Key限制的Value
     */
    protected abstract void addFrequencyControlStatisticsCount(Map<String, K> frequencyControlMap);

    /**
     * 获取策略名称
     *
     * @return 策略名称
     */
    protected abstract String getStrategyName();

    protected abstract K buildAbstactFrequencyControlDTO(String key, FrequencyControl frequencyControl);

}
