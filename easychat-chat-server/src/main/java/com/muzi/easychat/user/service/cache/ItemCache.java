package com.muzi.easychat.user.service.cache;

import com.muzi.easychat.user.dao.ItemConfigDao;
import com.muzi.easychat.user.domain.entity.ItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-09
 */
@Component
public class ItemCache {

    @Autowired
    private ItemConfigDao itemConfigDao;

    @Cacheable(cacheNames = "item",key = "'itemsByType:'+#itemType")
    public List<ItemConfig> getByType(Integer itemType) {
        return itemConfigDao.getByType(itemType);
    }

    @CacheEvict(cacheNames = "item",key = "'itemsByType:'+#itemType")
    public void evictByType(Integer itemType) {
    }
}
