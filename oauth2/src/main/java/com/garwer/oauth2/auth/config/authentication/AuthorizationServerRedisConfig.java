package com.garwer.oauth2.auth.config.authentication;

import com.garwer.oauth2.auth.error.MssWebResponseExceptionTranslator;
import com.garwer.oauth2.auth.service.impl.outh.RedisAuthorizationCodeServices;
import com.garwer.oauth2.auth.service.impl.outh.RedisClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

@Component
@EnableAuthorizationServer
@ConditionalOnProperty(name = "access_token_type", havingValue = AuthorizationServerRedisConfig.TOKEN_TYPE) //当为redis时生效
@Slf4j
@Order(1)
public class AuthorizationServerRedisConfig extends AuthorizationServerConfigurerAdapter {
    public static final String TOKEN_TYPE = "redis";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

    @Autowired
    private RedisAuthorizationCodeServices redisAuthorizationCodeServices;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${access_token_type}")
    private String accessTokenType;

    @Override
    public void configure(
            AuthorizationServerEndpointsConfigurer endpoints) {
        log.info("当前存储token方式为{}", accessTokenType);
        endpoints.authenticationManager(authenticationManager).tokenStore(redisStore()).authorizationCodeServices(redisAuthorizationCodeServices);
    }

    @Bean
    public RedisTokenStore redisStore() {
        RedisTokenStore redis = new RedisTokenStore(connectionFactory);
        //redis.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());
        return redis;
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(redisClientDetailsService);
        tokenServices.setAccessTokenValiditySeconds(60 * 60); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改 刷新token
        return tokenServices;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        //使用内存中的client
//        		clients.inMemory().withClient("system").secret(bCryptPasswordEncoder.encode("system"))
//				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("app")
//				.accessTokenValiditySeconds(3600);
        clients.withClientDetails(redisClientDetailsService);
        redisClientDetailsService.loadAllClientToCache();
    }

    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator(){
        return new MssWebResponseExceptionTranslator();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(bCryptPasswordEncoder);
    }
}