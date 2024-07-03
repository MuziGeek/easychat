package com.muzi.easychat.user.service;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-01
 */
public interface WXMsgService {
    /**
     * 用户扫码成功
     * @return
     */
    WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage);

    /**
     * 用户授权
     */
    void authorize(WxOAuth2UserInfo userInfo);
}
