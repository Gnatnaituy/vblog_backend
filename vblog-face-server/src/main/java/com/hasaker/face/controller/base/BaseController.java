package com.hasaker.face.controller.base;

import cn.hutool.core.util.StrUtil;
import com.hasaker.common.consts.Consts;
import com.hasaker.common.consts.RequestConsts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @package com.hasaker.face.controller.base
 * @author 余天堂
 * @create 2020/3/1 09:00
 * @description BaseController
 */
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    public String getUsername() {
        String authorization = request.getHeader(RequestConsts.AUTHORIZATION);
        String jwtToken = StrUtil.subAfter(authorization, "bearer ", false);

        Claims claims = Jwts.parser()
                .setSigningKey(Consts.JWT_ASSIGN_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.getAudience();
    }

    public Object getTokenInfo() {
        String authorization = request.getHeader(RequestConsts.AUTHORIZATION);
        String jwtToken = StrUtil.subAfter(authorization, "bearer ", false);

        Claims claims = Jwts.parser()
                .setSigningKey(Consts.JWT_ASSIGN_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims;
    }
}
