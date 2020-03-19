package com.hasaker.common.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.hasaker.common.consts.RequestConsts;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @package com.hasaker.common.interceptor
 * @author 余天堂
 * @create 2020/2/26 09:25
 * @description FeignRequestInterceptor
 */
@Slf4j
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    /**
     * Feign间传递header
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNotNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            if (ObjectUtil.isNotNull(request)) {
                String token = request.getHeader(RequestConsts.AUTHORIZATION);
                requestTemplate.header(RequestConsts.AUTHORIZATION, token);
                log.info(token);
            }
        }
    }
}
