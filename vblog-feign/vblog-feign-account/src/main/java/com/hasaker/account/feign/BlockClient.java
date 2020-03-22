package com.hasaker.account.feign;

import com.hasaker.account.vo.request.RequestBlockVo;
import com.hasaker.common.vo.Ajax;
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
@FeignClient(value = "block-client", url = "127.0.0.1:9001")
@RestController
public interface BlockClient {

    @PostMapping("/block/block")
    Ajax block(@RequestBody RequestBlockVo blockVo);

    @PostMapping("/block/unblock")
    Ajax unblock(@RequestBody RequestBlockVo blockVo);
}
