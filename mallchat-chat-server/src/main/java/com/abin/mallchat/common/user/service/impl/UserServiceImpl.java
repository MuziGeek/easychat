package com.abin.mallchat.common.user.service.impl;

import com.abin.mallchat.common.user.dao.UserDao;
import com.abin.mallchat.common.user.domain.entity.User;
import com.abin.mallchat.common.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-09-01
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        //todo 用户注册的事件
        return insert.getId();
    }
}
