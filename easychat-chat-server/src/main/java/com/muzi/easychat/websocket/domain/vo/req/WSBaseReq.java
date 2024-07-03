package com.muzi.easychat.websocket.domain.vo.req;

import com.muzi.easychat.websocket.domain.enums.WSReqTypeEnum;
import lombok.Data;

/**
 * Description:
 * Author: muzi
 * Date: 2023-08-27
 */
@Data
public class WSBaseReq {
    /**
     * @see WSReqTypeEnum
     */
    private Integer type;
    private String data;
}
