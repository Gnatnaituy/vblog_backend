package com.hasaker.face.controller.user;

import com.hasaker.account.feign.BlockClient;
import com.hasaker.account.vo.request.RequestBlockVo;
import com.hasaker.common.vo.Ajax;
import com.hasaker.face.controller.base.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.face.controller.user
 * @author 余天堂
 * @create 2020/3/26 14:57
 * @description BlockController
 */
@RestController
@RequestMapping(value = "/user/block")
public class BlockController extends BaseController {

    @Autowired
    private BlockClient blockClient;

    @ApiOperation(value = "Block a user")
    @PostMapping(value = "/block")
    public Ajax blockUser(@RequestBody RequestBlockVo blockVo) {
        blockVo.setUserId(getUserId());
        return blockClient.block(blockVo);
    }

    @ApiOperation(value = "Unblock a user")
    @PostMapping(value = "/unblock")
    public Ajax unblockUser(@RequestBody RequestBlockVo unblockVo) {
        unblockVo.setUserId(getUserId());
        return blockClient.unblock(unblockVo);
    }
}
