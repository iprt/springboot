package org.iproute.oauth2.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证配置
 *
 * @author zhuzhenjie
 * @since 2021/5/18
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 该对象用来支持password模式
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * token存储在内存中
     */
    @Autowired(required = false)
    TokenStore inMemoryTokenStore;

    /**
     * 该对象将为刷新token提供支持
     */
    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 指定密码的加密方式
     *
     * @return PasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        // 使用BCrypt强哈希函数加密方案（密钥迭代次数默认为10）
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置 password 授权模式
     *
     * @param clients ClientDetailsServiceConfigurer
     * @throws Exception Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory()
                .withClient("client-id-demo")
                // 授权模式为password和refresh_token两种
                .authorizedGrantTypes("password", "refresh_token")
                //  配置access_token的过期时间
                .accessTokenValiditySeconds(1800)
                // 配置资源id
                .resourceIds("rid")
                .scopes("all")
                // client-secret-demo 加密后的密码
                .secret("$2a$10$iy4gpSBgqeUO8mOEjmgAXu/f9LLASw09KOxot26C./B3ZX.pe3bvi");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 配置令牌的存储（这里存放在内存中）
        endpoints.tokenStore(inMemoryTokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 表示支持 client_id 和 client_secret 做登录认证
        security.allowFormAuthenticationForClients();
    }
}
