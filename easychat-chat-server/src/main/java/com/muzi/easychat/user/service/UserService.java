package com.muzi.easychat.user.service;

import com.muzi.easychat.user.domain.entity.User;
import com.muzi.easychat.user.domain.vo.req.user.BlackReq;
import com.muzi.easychat.user.domain.vo.resp.user.BadgeResp;
import com.muzi.easychat.user.domain.vo.resp.user.UserInfoResp;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author muzi
 * @since 2023-08-27
 */
public interface UserService {

    Long register(User insert);

    UserInfoResp getUserInfo(Long uid);

    void modifyName(Long uid, String name);

    List<BadgeResp> badges(Long uid);

    void wearingBadge(Long uid, Long itemId);

    void black(BlackReq req);
}
