package com.muzi.easychat.user.service.impl;

import com.muzi.easychat.common.annotation.RedissonLock;
import com.muzi.easychat.common.domain.enums.YesOrNoEnum;
import com.muzi.easychat.common.service.LockService;
import com.muzi.easychat.user.dao.UserBackpackDao;
import com.muzi.easychat.user.domain.entity.UserBackpack;
import com.muzi.easychat.user.domain.enums.IdempotentEnum;
import com.muzi.easychat.user.service.IUserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-09
 */
@Service
public class UserBackpackServiceImpl implements IUserBackpackService {
    @Autowired
    private LockService lockService;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    @Lazy
    private UserBackpackServiceImpl userBackpackService;

    @Override
    public void  acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        userBackpackService.doAcquireItem(uid, itemId, idempotent);
    }

    @RedissonLock(key = "#idempotent", waitTime = 5000)
    public void doAcquireItem(Long uid, Long itemId, String idempotent) {
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) {
            return;
        }
        //发放物品
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesOrNoEnum.NO.getStatus())
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
    }

    private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }
}
