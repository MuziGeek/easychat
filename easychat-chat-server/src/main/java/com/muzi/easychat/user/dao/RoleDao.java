package com.muzi.easychat.user.dao;

import com.muzi.easychat.user.domain.entity.Role;
import com.muzi.easychat.user.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author muzi
 * @since 2023-09-10
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
