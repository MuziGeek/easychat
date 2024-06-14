package com.abin.mallchat.common.user.service;

import com.abin.mallchat.common.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2023-08-27
 */
public interface UserService {

    Long register(User insert);
}
