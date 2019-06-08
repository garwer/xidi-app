package com.garwer.usercenter.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor //生成无参
@AllArgsConstructor //有参
public class AppUser implements Serializable {
	private Long userId;
	private String username;
	private String password;
	private Integer sex;
	private String nickName;
	private String imgUrl;
	private String phone;
	private Boolean enabled;
	private Date createTime;
	private Date updateTime;
}
