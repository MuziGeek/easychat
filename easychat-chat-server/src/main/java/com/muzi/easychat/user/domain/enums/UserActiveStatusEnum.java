package com.muzi.easychat.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-09
 */
@AllArgsConstructor
@Getter
public enum UserActiveStatusEnum {

    ONLINE(1, "在线"),
    OFFLINE(2, "离线");
    private final Integer status;
    private final String desc;
}
