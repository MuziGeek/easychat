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
public enum IdempotentEnum {

    UID(1, "uid"),
    MSG_ID(2, "消息id");
    private final Integer type;
    private final String desc;
}
