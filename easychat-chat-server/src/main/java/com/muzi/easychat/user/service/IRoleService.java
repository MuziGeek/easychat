package com.muzi.easychat.user.service;

import com.muzi.easychat.user.domain.enums.RoleEnum;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author muzi
 * @since 2023-09-10
 */
public interface IRoleService {

    /**
     * 是否拥有某个权限 临时写法
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);
}
