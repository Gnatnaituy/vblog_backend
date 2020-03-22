package com.hasaker.common.config;

import com.alibaba.fastjson.JSONObject;
import com.hasaker.common.exception.base.CommonException;
import com.hasaker.common.exception.enums.CommonExceptionEnums;
import com.hasaker.common.vo.Ajax;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @package com.hasaker.common.config
 * @author 余天堂
 * @create 2020/3/19 21:14
 * @description FeignConfig
 */
@Slf4j
public class FeignExceptionConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder();
    }

    public class FeignClientErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String s, Response response) {
            try {
                String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                log.error(body);
                Ajax ajax = JSONObject.parseObject(body, Ajax.class);

                return new CommonException(ajax.getCode(), ajax.getMessage());
            } catch (IOException e) {
                return CommonExceptionEnums.INTERNAL_SERVER_ERROR.newException();
            }
        }
    }
}
