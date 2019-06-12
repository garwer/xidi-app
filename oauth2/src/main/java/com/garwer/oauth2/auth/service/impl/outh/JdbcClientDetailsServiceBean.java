package com.garwer.oauth2.auth.service.impl.outh;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Create By: LJW
 * Date: 2019/6/11
 * Time: 10:19
 * 默认方式
 */

@Service
@ConditionalOnMissingBean(value = {RedisClientDetailsService.class}) //当没有自定义类型时注入 暂时自定义类型仅RedisClientDetailsService
public class JdbcClientDetailsServiceBean extends JdbcClientDetailsService{

    public JdbcClientDetailsServiceBean(DataSource dataSource) {
        super(dataSource);
    }

}
