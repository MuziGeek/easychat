package com.muzi.easychat.user.service.impl;

import com.muzi.easychat.user.domain.enums.RoleEnum;
import com.muzi.easychat.user.service.IRoleService;
import com.muzi.easychat.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-10
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private UserCache userCache;

    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        Set<Long> roleSet = userCache.getRoleSet(uid);
        return isAdmin(roleSet) || roleSet.contains((roleEnum.getId()));
    }

    private boolean isAdmin(Set<Long> roleSet) {
        return roleSet.contains(RoleEnum.ADMIN.getId());
    }
}
