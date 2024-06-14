package com.abin.mallchat.common.user.dao;

import com.abin.mallchat.common.user.domain.entity.UserBackpack;
import com.abin.mallchat.common.user.mapper.UserBackpackMapper;
import com.abin.mallchat.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2024-06-14
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> implements IUserBackpackService {

}
