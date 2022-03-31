package org.iproute.oauth2.config.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * ResourceServerConfig
 *
 * @author zhuzhenjie
 * @since 2021/5/18
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 配置资源id，这里的资源id和授权服务器中的资源id一致
        resources.resourceId("rid")
                // 设置这些资源仅基于令牌认证
                .stateless(true);
    }

    /**
     * 配置 URL 访问权限
     *
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/demo/admin/**").hasRole("admin")
                .antMatchers("/demo/user/**").hasRole("user")
                .anyRequest().authenticated();
    }
}
