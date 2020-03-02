package com.hasaker.account.service;

import com.hasaker.account.entity.Block;
import com.hasaker.common.base.BaseService;
import com.hasaker.vo.user.request.RequestBlockVo;

/**
 * @package com.hasaker.account.service
 * @author 余天堂
 * @create 2020/3/2 10:47
 * @description BlockService
 */
public interface BlockService extends BaseService<Block> {

    boolean blockUser(RequestBlockVo blockVo);

    boolean unblockUser(RequestBlockVo unblockVo);
}
