package com.garwer.oauth2.auth.config;
import com.garwer.oauth2.auth.config.authentication.AuthorizationServerRedisConfig;
import com.garwer.oauth2.auth.error.MssWebResponseExceptionTranslator;
import com.garwer.oauth2.auth.service.impl.outh.JdbcClientDetailsServiceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import javax.sql.DataSource;

/**
 * 授权服务器角色
 * 默认使用数据库存储token信息[类型为空]
 */
@Configuration
@EnableAuthorizationServer //声明为授权服务器
@ConditionalOnMissingBean( value = {AuthorizationServerRedisConfig.class}) //当没有自定义类型时注入 暂时仅有AuthorizationServerRedisConfig
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    protected static final String TOKEN_TYPE = "database";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JdbcClientDetailsServiceBean jdbcClientDetailsService;

    @Autowired
    @Qualifier("dataSource") //防止idea找不到报红
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(
            AuthorizationServerEndpointsConfigurer endpoints) {
        log.info("当前存储token方式为{}", TOKEN_TYPE);
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(jdbcStore())
               // .tokenServices(defaultTokenServices())
               // .exceptionTranslator(webResponseExceptionTranslator())
        ; //自定义异常翻译
    }

    @Bean
    public TokenStore jdbcStore() {
        //token存在jdbc 需按规范预先创建存放token的表oauth_access_token、oauth_refresh_token
        JdbcTokenStore jdbc = new JdbcTokenStore(dataSource);
        return jdbc;
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(jdbcStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(jdbcClientDetailsService);
        tokenServices.setAccessTokenValiditySeconds(60 * 60); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改 刷新token
        return tokenServices;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        //使用内存中的client
//        clients.inMemory()
//                .withClient("demoApp")
//                .secret(bCryptPasswordEncoder.encode("123"));
               // .authorizedGrantTypes("password", "authorization_code");
        clients.withClientDetails(jdbcClientDetailsService);
    }

    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator(){
        return new MssWebResponseExceptionTranslator();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")//对于CheckEndpoint控制器[框架自带的校验]的/oauth/token端点允许所有客户端发送器请求而不会被Spring-security拦截
                .checkTokenAccess("isAuthenticated()")//要访问/oauth/check_token必须设置为permitAll(),此接口一般不对外公布，是springoauth2内部使用，因此这里要设为isAuthenticated()
                .allowFormAuthenticationForClients()//允许客户表单认证,不加的话/oauth/token无法访问
                .passwordEncoder(bCryptPasswordEncoder);//设置oauth_client_details中的密码编码器
    }
}
