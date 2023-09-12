package com.abin.mallchat.common.common.event;

import com.abin.mallchat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-09-10
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {
    private User user;

    public UserOnlineEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
