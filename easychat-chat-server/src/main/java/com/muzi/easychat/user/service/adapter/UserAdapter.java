package com.muzi.easychat.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.muzi.easychat.common.domain.enums.YesOrNoEnum;
import com.muzi.easychat.user.domain.entity.ItemConfig;
import com.muzi.easychat.user.domain.entity.User;
import com.muzi.easychat.user.domain.entity.UserBackpack;
import com.muzi.easychat.user.domain.vo.resp.user.BadgeResp;
import com.muzi.easychat.user.domain.vo.resp.user.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-01
 */
public class UserAdapter {
    public static User buildUserSave(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildAuthorizeUser(Long uid, WxOAuth2UserInfo userInfo) {
        User user = new User();
        user.setId(uid);
        user.setName(userInfo.getNickname());
        user.setAvatar(userInfo.getHeadImgUrl());
        return user;
    }

    public static UserInfoResp buildUserInfo(User user, Integer modifyNameCount) {
        UserInfoResp vo = new UserInfoResp();
        BeanUtil.copyProperties(user, vo);
        vo.setModifyNameChance(modifyNameCount);
        return vo;
    }

    public static List<BadgeResp> buildBadgeResp(List<ItemConfig> itemConfigs, List<UserBackpack> backpacks, User user) {
        Set<Long> obtainItemSet = backpacks.stream().map(UserBackpack::getItemId).collect(Collectors.toSet());
        return itemConfigs.stream().map(a -> {
            BadgeResp resp = new BadgeResp();
            BeanUtil.copyProperties(a, resp);
            resp.setObtain(obtainItemSet.contains(a.getId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
            resp.setWearing(Objects.equals(a.getId(), user.getItemId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
            return resp;
        }).sorted(Comparator.comparing(BadgeResp::getWearing, Comparator.reverseOrder())
                .thenComparing(BadgeResp::getObtain, Comparator.reverseOrder()))
                .collect(Collectors.toList());

    }
}
