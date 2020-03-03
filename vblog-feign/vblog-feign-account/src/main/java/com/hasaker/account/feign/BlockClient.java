package com.hasaker.account.feign;

import com.hasaker.common.consts.Ajax;
import com.hasaker.vo.account.request.RequestBlockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package com.hasaker.account.feign
 * @author 余天堂
 * @create 2020/3/3 20:52
 * @description BlockClient
 */
@FeignClient(value = "vblog-account-server")
@RestController
public interface BlockClient {

    @PostMapping("/block/block")
    Ajax block(@RequestBody RequestBlockVo blockVo);

    @PostMapping("/block/unblock")
    Ajax unblock(@RequestBody RequestBlockVo blockVo);
}
