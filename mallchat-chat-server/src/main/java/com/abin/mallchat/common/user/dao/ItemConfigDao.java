package com.abin.mallchat.common.user.dao;

import com.abin.mallchat.common.user.domain.entity.ItemConfig;
import com.abin.mallchat.common.user.mapper.ItemConfigMapper;
import com.abin.mallchat.common.user.service.IItemConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 功能物品配置表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2024-06-14
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig> implements IItemConfigService {

}
