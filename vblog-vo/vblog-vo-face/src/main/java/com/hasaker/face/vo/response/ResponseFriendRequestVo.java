package com.hasaker.face.vo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @package com.hasaker.face.vo.response
 * @author 余天堂
 * @create 2020/3/27 18:41
 * @description ResponseFriendRequestVo
 */
@Data
@NoArgsConstructor
public class ResponseFriendRequestVo {

    private Long id;

    private Long senderId;

    private Long receiverId;

    private String senderRemark;

    private String senderVisibility;

    private String requestStatus;

    private Long sendTime;

    private Long acceptTime;

    private Long denyTime;

    private Long ignoreTime;
}
