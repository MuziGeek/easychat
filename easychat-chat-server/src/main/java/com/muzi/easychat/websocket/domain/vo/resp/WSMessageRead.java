package com.muzi.easychat.websocket.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: muzi
 * Date: 2023-03-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSMessageRead {
    @ApiModelProperty("消息")
    private Long msgId;
    @ApiModelProperty("阅读人数（可能为0）")
    private Integer readCount;
}
