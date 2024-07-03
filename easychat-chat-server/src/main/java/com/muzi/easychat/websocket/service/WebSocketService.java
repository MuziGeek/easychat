package com.muzi.easychat.websocket.service;

import com.muzi.easychat.websocket.domain.vo.resp.WSBaseResp;
import io.netty.channel.Channel;

/**
 * Description:
 * Author: muzi
 * Date: 2023-09-01
 */
public interface WebSocketService {
    void connect(Channel channel);

    void handleLoginReq(Channel channel);

    void remove(Channel channel);

    void scanLoginSuccess(Integer code, Long uid);

    void waitAuthorize(Integer code);

    void authorize(Channel channel, String token);

    void sendMsgToAll(WSBaseResp<?> msg);
}
