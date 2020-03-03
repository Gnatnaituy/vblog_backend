package com.hasaker.account.controller;

import com.hasaker.account.service.BlockService;
import com.hasaker.common.consts.Ajax;
import com.hasaker.vo.account.request.RequestBlockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.account.controller
 * @author 余天堂
 * @create 2020/3/2 11:12
 * @description BlockController
 */
@RestController
@RequestMapping(value = "/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @ApiOperation(value = "屏蔽用户")
    @PostMapping(value = "/block")
    public Ajax blockUser(@RequestBody RequestBlockVo blockVo) {
        blockService.blockUser(blockVo);

        return Ajax.success();
    }

    @ApiOperation(value = "取消屏蔽用户")
    @PostMapping(value = "/unblock")
    public Ajax unblockUser(@RequestBody RequestBlockVo unblockVo) {
        blockService.unblockUser(unblockVo);

        return Ajax.success();
    }
}
