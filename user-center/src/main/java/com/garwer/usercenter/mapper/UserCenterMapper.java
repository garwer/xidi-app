package com.garwer.usercenter.mapper;
import com.garwer.common.user.AppUser;
import org.springframework.stereotype.Repository;

/**
 * @Author: Garwer
 * @Date: 2019/5/22 11:41 PM
 * @Version 1.0
 * 加入Repository注解防止idea报红
 */

@Repository
public interface UserCenterMapper {
    AppUser findByUsername(String userName);

    AppUser findByUserId(Long userId);

    Integer initTable();

    void update(AppUser appUser);
}
