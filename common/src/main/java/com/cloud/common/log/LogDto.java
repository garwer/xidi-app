package com.cloud.common.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 10:33 PM
 * @Version 1.0
 * 用于记录日志
 */

@Builder
@Data
@NoArgsConstructor //生成无参
@AllArgsConstructor //有参
public class LogDto {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 调用模块
     */
    private String module;
    /**
     * 调用状态
     */
    private String status;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 调用信息(成功/异常信息)
     */
    private String msg;

}
