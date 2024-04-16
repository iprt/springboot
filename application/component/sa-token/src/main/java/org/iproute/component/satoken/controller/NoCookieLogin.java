package org.iproute.component.satoken.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NoCookieLogin
 *
 * @author devops@kubectl.net
 * @since 2023/8/15
 */
@RequestMapping("/NoCookie")
@RestController
public class NoCookieLogin {

    @PostMapping("doLogin")
    public SaResult doLogin() {
        StpUtil.login(10001L);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 第3步，返回给前端
        return SaResult.data(tokenInfo);
    }

    @SaCheckLogin
    @PostMapping("/doLogout")
    public SaResult doLogout() {
        StpUtil.logout(10001L);
        return SaResult.ok("登出");
    }

}
