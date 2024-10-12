package com.muzi.easychat.frequencycontrol.service.frequencycontrol;


import com.muzi.easychat.frequencycontrol.annotation.FrequencyControl;
import com.muzi.easychat.frequencycontrol.domain.dto.FrequencyControlDTO;

import java.util.Map;

/**
 * 限流工具类 提供编程式的限流调用方法
 */
public class FrequencyControlUtil {

    /**
     * 单限流策略的调用方法-编程式调用
     *
     * @param strategyName     策略名称
     * @param keyMap 频控注解集合
     * @param supplier         服务提供着
     * @return 业务方法执行结果
     * @throws Throwable
     */
    public static <T, K extends FrequencyControlDTO> T executeWithFrequencyControl(String strategyName, Map<String, FrequencyControl> keyMap, AbstractFrequencyControlService.SupplierThrowWithoutParam<T> supplier) throws Throwable {
        AbstractFrequencyControlService<K> frequencyController = FrequencyControlStrategyFactory.getFrequencyControllerByName(strategyName);
        return frequencyController.executeWithFrequencyControl(keyMap, supplier);
    }

    public static <K extends FrequencyControlDTO> void executeWithFrequencyControl(String strategyName, Map<String, FrequencyControl > keyMap, AbstractFrequencyControlService.Executor executor) throws Throwable {
        AbstractFrequencyControlService<K> frequencyController = FrequencyControlStrategyFactory.getFrequencyControllerByName(strategyName);
        frequencyController.executeWithFrequencyControl(keyMap, () -> {
            executor.execute();
            return null;
        });
    }


    /**
     * 多限流策略的编程式调用方法调用方法
     *
     * @param strategyName         策略名称
     * @param  keyMap  频控注解集合
     * @param supplier             函数式入参-代表每个频控方法执行的不同的业务逻辑
     * @return 业务方法执行的返回值
     * @throws Throwable 被限流或者限流策略定义错误
     */
    public static <T, K extends FrequencyControlDTO> T executeWithFrequencyControlList(String strategyName, Map<String, FrequencyControl > keyMap, AbstractFrequencyControlService.SupplierThrowWithoutParam<T> supplier) throws Throwable {
        AbstractFrequencyControlService<K> frequencyController = FrequencyControlStrategyFactory.getFrequencyControllerByName(strategyName);
        return frequencyController.executeWithFrequencyControlList( keyMap, supplier);
    }

    /**
     * 构造器私有
     */
    private FrequencyControlUtil() {

    }

}
